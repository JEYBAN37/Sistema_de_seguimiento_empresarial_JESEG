package com.jeseg.admin_system.parameters.infrastruture.adapter;

import com.jeseg.admin_system.parameters.domain.interfaces.ParameterInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ParameterAdapter implements ParameterInterface {
    @Override
    public List<String> listadoParametros(Long id) {
        return List.of("Parametro1", "Parametro2", "Parametro3");
    }
}
