package com.jeseg.admin_system.parameters.infrastructure.adapter;

import com.jeseg.admin_system.parameters.domain.dto.Parametros;
import com.jeseg.admin_system.parameters.domain.dto.Responsable;
import com.jeseg.admin_system.parameters.domain.dto.Ubicacion;
import com.jeseg.admin_system.parameters.domain.interfaces.ParameterInterface;
import com.jeseg.admin_system.parameters.infrastructure.repository.ParameterRepository;
import com.jeseg.admin_system.parameters.infrastructure.repository.ResponsableRepository;
import com.jeseg.admin_system.parameters.infrastructure.repository.UbicacionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ParameterAdapter implements ParameterInterface {

    private final ParameterRepository parameterRepository;
    private final ResponsableRepository responsableRepository;
    private final UbicacionRepository ubicacionRepository;

    @Override
    public List<Parametros> listarParametros() {
        return parameterRepository.findByTipoIn(List.of("U", "A"))
                .stream()
                .map(entity -> Parametros.builder()
                        .resultado(List.of(entity.getResultado().split(",")))
                        .build())
                .toList();
    }

    @Override
    public List<Responsable> listarResponsables() {
        return responsableRepository.findResponsables()
                .stream()
                .map(entity -> Responsable.builder()
                        .id(entity.getId())
                        .nombre(entity.getNombres())
                        .build())
                .toList();
    }

    @Override
    public List<Ubicacion> listarUbicaciones() {
        return ubicacionRepository.findDistinctTerritorios().stream()
                .map(territorio -> Ubicacion.builder()
                        .territorio(territorio.getTerritorio())
                        .estado(territorio.getEstado())
                        .transporte(territorio.getTransporte())
                        .build())
                .toList();
    }
}
