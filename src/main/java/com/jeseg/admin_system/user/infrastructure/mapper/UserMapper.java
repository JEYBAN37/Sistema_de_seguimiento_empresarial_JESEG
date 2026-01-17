package com.jeseg.admin_system.user.infrastructure.mapper;

import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.dto.UserCsvRow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "nombre", source = "nombre_contratista")
    @Mapping(target = "cedula", source = "numero_identificacion")
    @Mapping(target = "contrato", source = "numero_contrato")
    @Mapping(target = "fechaInicioContrato", source = "fecha_inicio_contrato")
    @Mapping(target = "fechaTerminoContrato", source = "fecha_termino_contrato")
    @Mapping(target = "numeroSupervisor", source = "numero_supervisor")
    @Mapping(target = "nombreSupervisor", source = "nombre_supervisor")
    @Mapping(target = "valorContratado", source = "valor_contratado")
    @Mapping(target = "idRecurso", source = "id_recurso")
    @Mapping(target = "role", source = "rol")
    @Mapping(target = "company", source = "compania")
    UserCreateRequest toCreateRequest(UserCsvRow csvRow);
}
