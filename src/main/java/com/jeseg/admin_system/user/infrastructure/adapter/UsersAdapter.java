package com.jeseg.admin_system.user.infrastructure.adapter;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyResponse;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.hierarchyNode.infrastructure.repository.HierarchyNodeRepository;
import com.jeseg.admin_system.role.domain.dto.RoleResponse;
import com.jeseg.admin_system.role.infrastructure.repository.RoleRepository;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.dto.UserCsvRow;
import com.jeseg.admin_system.user.domain.dto.UserResponse;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;

import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import com.jeseg.admin_system.user.infrastructure.mapper.UserMapper;
import com.jeseg.admin_system.user.infrastructure.repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import static com.jeseg.admin_system.application.UploadsGeneric.uploadedFiles;
import static java.util.stream.Collectors.toList;

@Repository
@AllArgsConstructor
public class UsersAdapter implements UserInterface {

    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final HierarchyNodeRepository hierarchyNodeRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private static final List<String> EXPECTED_HEADERS = List.of(
            "numero_contrato",
            "indicador",
            "fecha_inicio_contrato",
            "fecha_termino_contrato",
            "objeto",
            "contratista_tipo_identificacion",
            "numero_identificacion",
            "nombre_contratista",
            "numero_supervisor",
            "tipo_doc_supervisor",
            "nombre_supervisor",
            "pagado",
            "valor_contratado",
            "id_recurso",
            "saldo",
            "estado",
            "rol",
            "cargo",
            "telefono"

    );


    @Override
    public List<UserResponse>  getUsersAdminPage(Long companyId) {
        return userRepository.findByCompanyId(companyId).
                stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .cedula(user.getCedula())
                        .username(user.getUsername())
                        .nombreCompleto(user.getNombreCompleto())
                        .contrato(user.getContrato())
                        .telefono(user.getTelefono())
                        .fechaTerminoContrato(user.getFechaTerminoContrato())
                        .fechaInicioContrato(user.getFechaInicioContrato())
                        .role(RoleResponse.builder()
                                .id(user.getRole().getId())
                                .name(user.getRole().getName())
                                .build())
                        .objeto(user.getObjeto())
                        .pagado(user.getPagado())
                        .contrato(user.getContrato())
                        .idRecurso(user.getIdRecurso())
                        .hierarchyNode(HierarchyResponse.builder()
                                .id(user.getHierarchyNode().getId())
                                .nombre(user.getHierarchyNode().getName())
                        .build()).build()
                ).collect(toList());


    }

    @Override
    public List<List<UserCreateRequest>> validUserCsv(MultipartFile file) {

        String mimetype = file.getContentType();
        String dir = file.getOriginalFilename();

        if (mimetype == null || !mimetype.equals("text/csv")) {
            throw BusinessException.Type.ERROR_FORMATO_CSV_INVALIDO.build();
        }


        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {

            String[] headers = csvReader.readNext();
            if (headers == null || headers.length == 0) {
                throw BusinessException.Type.ERROR_CSV_SIN_ENCABEZADOS.build();
            }

            headers[0] = headers[0].replace("\uFEFF", "").trim();


            List<String> actualHeaders = Arrays.stream(headers)
                    .map(String::trim)
                    .toList();


            if (!EXPECTED_HEADERS.equals(actualHeaders)) {
                throw BusinessException.Type.ERROR_ENCABEZADOS_CSV_INVALIDOS.build();
            }

            String url = uploadedFiles(file,"uploads/csv/");

            HeaderColumnNameMappingStrategy<UserCsvRow> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(UserCsvRow.class);

            InputStream is = Files.newInputStream(Paths.get(url));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            reader.mark(1);
            if (reader.read() != 0xFEFF) {
                reader.reset();
            }

            CsvToBean<UserCsvRow> csvToBean = new CsvToBeanBuilder<UserCsvRow>(reader)
                    .withMappingStrategy(strategy)
                    .withType(UserCsvRow.class)
                    .withSkipLines(0)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();


            return identifyTypeOfOperation(csvToBean.parse());

        } catch (IOException | CsvValidationException e) {
            throw BusinessException.Type.ERROR_BASE.build(e);
        }

    }


    private List<List<UserCreateRequest>> identifyTypeOfOperation(List<UserCsvRow> usersCsv) {

        List<UserCreateRequest> usersToInvalid =  usersCsv.stream()
                .filter(user ->
                        !user.getIndicador().equalsIgnoreCase("I") ||
                                !user.getIndicador().equalsIgnoreCase("A") ||
                                !user.getIndicador().equalsIgnoreCase("E"))
                .map(userMapper::toCreateRequest)
                .toList();

        List<UserCreateRequest> usersToCreate =  usersCsv.stream()
                .filter(user -> user.getIndicador().equalsIgnoreCase("I"))
                .map(userMapper::toCreateRequest)
                .toList();

        List<UserCreateRequest> usersToUpdate =  usersCsv.stream()
                .filter(user -> user.getIndicador().equalsIgnoreCase("A"))
                .map(userMapper::toCreateRequest)
                .toList();

        List<UserCreateRequest> usersToDelete =  usersCsv.stream()
                .filter(user -> user.getIndicador().equalsIgnoreCase("E"))
                .map(userMapper::toCreateRequest)
                .toList();

        return List.of(usersToInvalid,usersToCreate, usersToUpdate, usersToDelete);
    }



    @Override
    @Transactional
    public void createUsers(List<UserCreateRequest> users) {
        Set<Long> occupiedIds = new HashSet<>();
        Long companyId = users.get(0).getCompany();

        List<UserJepegEntity> entities = users.stream()
                .map(user -> {
                    // 1. Buscamos todos los nodos que empiecen con el nombre del cargo (ej. "Auxiliar")
                    List<HierarchyNodeEntity> potentialNodes = hierarchyNodeRepository
                            .findAvailableNodesByPrefix(user.getCargo().trim(), companyId);

                    // 2. Filtramos: que no tengan usuario asignado Y que no hayamos usado el ID en este loop
                    HierarchyNodeEntity node = null;
                    try {
                        node = potentialNodes.stream()
                                .filter(n -> !occupiedIds.contains(n.getId()))
                                .findFirst()
                                .orElseThrow(() -> BusinessException.Type.ERROR_BASE.build(
                                        "No hay vacantes libres para el cargo '" + user.getCargo() +
                                                "'. Verifique que existan suficientes posiciones (ej. #1, #2) creadas. " +
                                                "Falla en el registro de: " + user.getNombre()
                                ));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    // 3. Bloqueamos este ID para la siguiente iteración
                    occupiedIds.add(node.getId());

                    return UserJepegEntity.builder()
                            .cedula(user.getCedula())
                            .nombreCompleto(user.getNombre())
                            .hierarchyNode(node)
                            .telefono(user.getTelefono())
                            .contrato(user.getContrato())
                            .role(roleRepository.getReferenceById(user.getRole()))
                            .company(companyRepository.getReferenceById(companyId))
                            .password(encodePassword("Cc" + user.getCedula()))
                            .username(user.getCedula())
                            .fechaInicioContrato(LocalDate.parse(user.getFechaInicioContrato()))
                            .fechaTerminoContrato(LocalDate.parse(user.getFechaTerminoContrato()))
                            .objeto(user.getObjeto())
                            .active(Boolean.valueOf(user.getEstado()))
                            .pagado(new BigDecimal(user.getPagado()))
                            .idRecurso(Long.valueOf(user.getIdRecurso()))
                            .valorContratado(new BigDecimal(user.getValorContratado()))
                            .numeroSupervisor(user.getNumeroSupervisor())
                            .nombreSupervisor(user.getNombreSupervisor())
                            .build();
                })
                .toList();

        userRepository.saveAll(entities);
    }


    @Override
    @Transactional
    public void updateUsers(List<UserCreateRequest> users) {
        Long companyId = users.get(0).getCompany();

        // 1. PRECARGA: Solo 2 consultas a la BD
        List<UserJepegEntity> existingUsers = userRepository.findByContratoInAndCompanyId(
                users.stream().map(UserCreateRequest::getContrato).toList(),
                companyId
        ).orElse(List.of());

        List<HierarchyNodeEntity> availablePool = new ArrayList<>(
                hierarchyNodeRepository.findAllAvailableNodes(companyId)
        );

        // 2. PROCESAMIENTO CON CONTROL DE ERRORES
        // Usamos un for tradicional o IntStream para tener el índice de la fila
        for (int i = 0; i < users.size(); i++) {
            UserCreateRequest userRequest = users.get(i);
            int filaActual = i + 2; // +2 porque el CSV empieza en 1 y tiene encabezado

            try {
                UserJepegEntity userEntity = existingUsers.stream()
                        .filter(u -> Objects.equals(u.getContrato(), userRequest.getContrato()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Usuario con contrato " + userRequest.getContrato() + " no existe."));

                String cargoActualLimpio = userEntity.getHierarchyNode().getName().replaceAll("\\s#\\d+", "").trim();
                String nuevoCargoCsv = userRequest.getCargo().trim();

                if (!cargoActualLimpio.equalsIgnoreCase(nuevoCargoCsv)) {

                    HierarchyNodeEntity newNode = availablePool.stream()
                            .filter(n -> n.getName().startsWith(nuevoCargoCsv))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Sin vacantes libres para '" + nuevoCargoCsv + "'"));

                    availablePool.remove(newNode);
                    userEntity.setHierarchyNode(newNode);
                }

                mapRequestToEntity(userRequest, userEntity);

            } catch (Exception e) {
                throw new RuntimeException("Error en la fila " + filaActual + " (Contrato: " + userRequest.getContrato() + "): " + e.getMessage());
            }
        }

        // Si llegamos aquí sin errores, guardamos todo de un solo golpe
        userRepository.saveAll(existingUsers);
    }


    private void mapRequestToEntity(UserCreateRequest req, UserJepegEntity entity) {
        entity.setNombreCompleto(req.getNombre());
        entity.setTelefono(req.getTelefono());
        entity.setRole(roleRepository.getReferenceById(req.getRole()));
        entity.setFechaInicioContrato(LocalDate.parse(req.getFechaInicioContrato()));
        entity.setFechaTerminoContrato(LocalDate.parse(req.getFechaTerminoContrato()));
        entity.setObjeto(req.getObjeto());
        entity.setPagado(new BigDecimal(req.getPagado()));
        entity.setIdRecurso(Long.valueOf(req.getIdRecurso()));
        entity.setValorContratado(new BigDecimal(req.getValorContratado()));
        entity.setActive(Boolean.valueOf(req.getEstado()));
        entity.setNumeroSupervisor(req.getNumeroSupervisor());
        entity.setNombreSupervisor(req.getNombreSupervisor());
    }

    @Override
    @Transactional
    public void deleteUsers(List<String> userContrats, Long idCompany) {
        List<UserJepegEntity> users = userRepository.findByContratoInAndCompanyId(userContrats, idCompany)
                .orElseThrow(BusinessException.Type.ERROR_USUARIO_NO_ENCONTRADO::build);
        userRepository.deleteAll(users);
    }


    private String encodePassword(String password)
    {
        return password;
    }

}
