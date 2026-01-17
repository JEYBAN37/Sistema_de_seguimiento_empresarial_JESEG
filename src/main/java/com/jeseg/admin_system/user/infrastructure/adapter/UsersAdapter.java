package com.jeseg.admin_system.user.infrastructure.adapter;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyResponse;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.hierarchyNode.infrastructure.repository.HierarchyNodeRepository;
import com.jeseg.admin_system.role.domain.dto.RoleResponse;
import com.jeseg.admin_system.role.infrastructure.entity.RoleEntity;
import com.jeseg.admin_system.role.infrastructure.repository.RoleRepository;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.dto.UserCsvRow;
import com.jeseg.admin_system.user.domain.dto.UserResponse;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;

import com.jeseg.admin_system.user.domain.model.User;
import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import com.jeseg.admin_system.user.infrastructure.mapper.UserMapper;
import com.jeseg.admin_system.user.infrastructure.repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.jeseg.admin_system.application.UploadsGeneric.parseFecha;
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
            "telefono",
            "compania"
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
                        .objeto(user.getObjeto().substring(0, Math.min(user.getObjeto().length(), 150)))
                        .pagado(String.valueOf(user.getPagado()))
                        .valorContratado(String.valueOf(user.getValorContratado()))
                        .saldo(String.valueOf(user.getSaldo()))
                        .contrato(user.getContrato())
                        .idRecurso(user.getIdRecurso())
                        .active(user.getActive())
                        .numeroSupervisor(user.getNumeroSupervisor())
                        .nombreSupervisor(user.getNombreSupervisor())
                        .hierarchyNode(HierarchyResponse.builder()
                                .id(user.getHierarchyNode().getId())
                                .nombre(user.getHierarchyNode().getName())
                        .build()).build()
                ).collect(toList());


    }

    @Override
    public List<List<UserCreateRequest>> validUserCsv(MultipartFile file) {
        String mimetype = file.getContentType();

        if (mimetype == null || (!mimetype.equals("text/csv") && !mimetype.equals("application/vnd.ms-excel"))) {
            throw BusinessException.Type.ERROR_FORMATO_CSV_INVALIDO.build();
        }

        try {
            // 1. Detectar el separador leyendo la primera línea manualmente
            byte[] fileContent = file.getBytes();
            String firstLine = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileContent))).readLine();

            if (firstLine == null) throw BusinessException.Type.ERROR_CSV_SIN_ENCABEZADOS.build();

            // Si la línea tiene ';' usamos ese, de lo contrario ','
            char separator = firstLine.contains(";") ? ';' : ',';

            // 2. Validar Encabezados con el separador detectado
            try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(new ByteArrayInputStream(fileContent)))
                    .withCSVParser(new RFC4180ParserBuilder().withSeparator(separator).build())
                    .build()) {

                String[] headers = csvReader.readNext();
                if (headers == null || headers.length == 0) {
                    throw BusinessException.Type.ERROR_CSV_SIN_ENCABEZADOS.build();
                }

                // Limpieza de BOM y espacios
                headers[0] = headers[0].replace("\uFEFF", "").trim();
                List<String> actualHeaders = Arrays.stream(headers).map(String::trim).toList();

                if (!EXPECTED_HEADERS.equals(actualHeaders)) {
                    throw BusinessException.Type.ERROR_ENCABEZADOS_CSV_INVALIDOS.build();
                }
            }

            // 3. Guardar archivo y procesar contenido
            String url = uploadedFiles(file, "uploads/csv/");
            HeaderColumnNameMappingStrategy<UserCsvRow> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(UserCsvRow.class);

            try (BufferedReader reader = Files.newBufferedReader(Paths.get(url), StandardCharsets.UTF_8)) {
                // Manejo de BOM en la lectura final
                reader.mark(1);
                if (reader.read() != 0xFEFF) reader.reset();

                CsvToBean<UserCsvRow> csvToBean = new CsvToBeanBuilder<UserCsvRow>(reader)
                        .withMappingStrategy(strategy)
                        .withType(UserCsvRow.class)
                        .withSeparator(separator) // <--- CRÍTICO: Usar el mismo separador detectado
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                List<UserCsvRow> post = csvToBean.parse();
                return identifyTypeOfOperation(post);
            }

        } catch (Exception e) {
            throw BusinessException.Type.ERROR_BASE.build(e);
        }
    }


    private List<List<UserCreateRequest>> identifyTypeOfOperation(List<UserCsvRow> usersCsv) {
        // Definimos los indicadores válidos
        Set<String> validIndicators = Set.of("I", "A", "E");

        // 1. Filtrar los inválidos (Los que NO están en la lista de permitidos)
        List<UserCreateRequest> usersToInvalid = usersCsv.stream()
                .filter(user -> user.getIndicador() == null ||
                        !validIndicators.contains(user.getIndicador().toUpperCase()))
                .map(userMapper::toCreateRequest)
                .toList();

        // 2. Filtrar Creación (Insertar)
        List<UserCreateRequest> usersToCreate = usersCsv.stream()
                .filter(user -> "I".equalsIgnoreCase(user.getIndicador()))
                .map(userMapper::toCreateRequest)
                .toList();

        // 3. Filtrar Actualización (Actualizar)
        List<UserCreateRequest> usersToUpdate = usersCsv.stream()
                .filter(user -> "A".equalsIgnoreCase(user.getIndicador()))
                .map(userMapper::toCreateRequest)
                .toList();

        // 4. Filtrar Eliminación (Eliminar/Estado inactivo)
        List<UserCreateRequest> usersToDelete = usersCsv.stream()
                .filter(user -> "E".equalsIgnoreCase(user.getIndicador()))
                .map(userMapper::toCreateRequest)
                .toList();

        return List.of(usersToInvalid, usersToCreate, usersToUpdate, usersToDelete);
    }

    @Override
    @Transactional
    public void createUsers(List<UserCreateRequest> usersRequest){
        Set<Long> occupiedIds = new HashSet<>();
        Long companyId = usersRequest.get(0).getCompany();

        List<UserJepegEntity> entities = new ArrayList<>();

        for (UserCreateRequest userDto : usersRequest) {
            // 1. Buscar Nodo
            List<HierarchyNodeEntity> potentialNodes = hierarchyNodeRepository
                    .findAvailableNodesByPrefix(userDto.getCargo().trim(), companyId);

            HierarchyNodeEntity node;
            try {

                node = potentialNodes.stream()
                        .filter(n -> !occupiedIds.contains(n.getId()))
                        .findFirst()
                        .orElseThrow(() -> BusinessException.Type.ERROR_BASE.build(
                                "No hay vacantes libres para el cargo '" + userDto.getCargo() +
                                        "'. Verifique que existan suficientes posiciones (ej. #1, #2) creadas. " +
                                        "Falla en el registro de: " + userDto.getNombre()
                        ));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            occupiedIds.add(node.getId());

            // 2. Construir Usuario
            UserJepegEntity userEntity = UserJepegEntity.builder()
                    .cedula(userDto.getCedula())
                    .nombreCompleto(userDto.getNombre())
                    .hierarchyNode(node)
                    .telefono(userDto.getTelefono())
                    .contrato(userDto.getContrato())
                    .role(roleRepository.findById(userDto.getRole()).orElseThrow(BusinessException.Type.ERROR_ROL_NO_ENCONTRADO::build))
                    .company(companyRepository.getReferenceById(companyId))
                    .password(encodePassword("Cc" + userDto.getCedula()))
                    .username(userDto.getCedula())
                    .fechaInicioContrato(parseFecha(userDto.getFechaInicioContrato()))
                    .fechaTerminoContrato(parseFecha(userDto.getFechaInicioContrato()))
                    .objeto(userDto.getObjeto().substring(0, Math.min(userDto.getObjeto().length(), 150)))
                    .active(Objects.equals(userDto.getEstado(), "1"))
                    .pagado(new BigDecimal(userDto.getPagado()))
                    .idRecurso(userDto.getIdRecurso())
                    .valorContratado(new BigDecimal(userDto.getValorContratado()))
                    .numeroSupervisor(userDto.getNumeroSupervisor())
                    .nombreSupervisor(userDto.getNombreSupervisor())
                    .build();

            // OPCIONAL: Si necesitas que el objeto 'node' vea al usuario inmediatamente
            // node.getUsers().add(userEntity);

            entities.add(userEntity);
        }

        // 3. Guardar todo
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
        entity.setFechaInicioContrato(parseFecha(req.getFechaInicioContrato()));
        entity.setFechaTerminoContrato(parseFecha(req.getFechaInicioContrato()));
        entity.setObjeto(req.getObjeto().substring(0, Math.min(req.getObjeto().length(), 150)));
        entity.setPagado(new BigDecimal(req.getPagado()));
        entity.setIdRecurso(req.getIdRecurso());
        entity.setValorContratado(new BigDecimal(req.getValorContratado()));
        entity.setActive(Objects.equals(req.getEstado(), "1"));
        entity.setNumeroSupervisor(req.getNumeroSupervisor());
        entity.setNombreSupervisor(req.getNombreSupervisor());
        entity.setContrato(req.getContrato());
        entity.setHierarchyNode(entity.getHierarchyNode());
    }

    @Override
    @Transactional
    public void deleteUsers(List<UserCreateRequest> userContrats) {

        Long company = userContrats.get(0).getCompany();
        if (company != null){

            List<String> usersId = userContrats.stream().map(UserCreateRequest::getContrato).toList();


            List<UserJepegEntity> users = userRepository.findByContratoInAndCompanyId(usersId, company)
                    .orElseThrow(BusinessException.Type.ERROR_USUARIO_NO_ENCONTRADO::build);
            userRepository.deleteAll(users);
        }
    }


    private String encodePassword(String password)
    {
        return password;
    }

}
