package com.jeseg.admin_system.parameters.domain.usecases;

import com.jeseg.admin_system.parameters.domain.dto.*;
import com.jeseg.admin_system.parameters.domain.interfaces.ParameterInterface;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ParametersUseCase {

    private ParameterInterface parameterInterface;

    public ParametersResponse parametersCronograma(){


        List<Parametros> parametros = parameterInterface.listarParametros();
        List<Ubicacion> ubicacions = parameterInterface.listarUbicaciones();
        List<Responsable> responsables = parameterInterface.listarResponsables();

        List<String> actividades = parametros.get(1).getResultado();
        List<String> lugares = parametros.get(0).getResultado();
        List<UbicacionResponse> territorios = ubicacions.stream().map(this::convertirAUbicacionResponse).toList();
        List<String> equipos =  ubicacions.stream().map(this::crearNombreEquipo).toList();


        return ParametersResponse.builder()
                .actividades(actividades)
                .lugares(lugares)
                .territorios(territorios)
                .equipos(equipos)
                .responsables(responsables)
                .build();
    }


    String crearNombreEquipo(Ubicacion t) {
        String territorio = t.getTerritorio();
        String[] partes = territorio.split("T");
        String equipo = partes[1].trim();
        return "EBS " + equipo.toUpperCase();
    }

    UbicacionResponse convertirAUbicacionResponse(Ubicacion ubicacion) {
        return  UbicacionResponse.builder()
                        .territorio(ubicacion.getTerritorio())
                        .estado(ubicacion.getEstado())
                        .transporte(ubicacion.getTransporte())
                        .build();
    }
}
