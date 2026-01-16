package com.jeseg.admin_system.user.domain.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCsvRow {
    @CsvBindByPosition(position = 0)
    private String numero_contrato;//
    private String indicador;
    private String fecha_inicio_contrato;
    private String fecha_termino_contrato;
    private String objeto;
    private String contratista_tipo_identificacion;
    private String numero_identificacion;
    private String nombre_contratista;
    private String numero_supervisor;
    private String tipo_doc_supervisor;
    private String nombre_supervisor;
    private String pagado;
    private String valor_contratado;
    private String id_recurso;
    private String saldo;
    private String estado;
    private Long rol;
    private String cargo;
    private String telefono;
    private Long compania;
}
