package com.jeseg.admin_system.user.infrastructure.adapter;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;
import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyResponse;
import com.jeseg.admin_system.hierarchyNode.infrastructure.repository.HierarchyNodeRepository;
import com.jeseg.admin_system.role.domain.dto.RoleResponse;
import com.jeseg.admin_system.role.infrastructure.repository.RoleRepository;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.dto.UserCsvRow;
import com.jeseg.admin_system.user.domain.dto.UserResponse;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;

import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.jeseg.admin_system.application.UploadsGeneric.uploadedFiles;
import static java.util.stream.Collectors.toList;

@Repository
@AllArgsConstructor
public class UsersAdapter implements UserInterface {

    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final HierarchyNodeRepository hierarchyNodeRepository;
    private final UserRepository userRepository;

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
    @Transactional
    public void saveUsers(List<UserCreateRequest> users) {

        List<UserJepegEntity> entities = users.stream()
                .map(user ->
                        UserJepegEntity.builder()
                        .cedula(user.getCedula())
                        .password(encodePassword(user.getPassword()))
                        .username(user.getUsername())
                        .nombreCompleto(user.getNombre())
                        .contrato(user.getContrato())
                        .telefono(user.getTelefono())
                        .role(roleRepository.getReferenceById(user.getRole()))
                        .company(companyRepository.getReferenceById(user.getCompany()))
                        .build())
                .toList();

        userRepository.saveAll(entities);
    }

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
    public List<UserCsvRow> validUserCsv(MultipartFile file) {

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


            return csvToBean.parse();

        } catch (IOException | CsvValidationException e) {
            throw BusinessException.Type.ERROR_BASE.build(e);
        }

    }


    private String encodePassword(String password)
    {
        return password;
    }

}
