package com.jeseg.admin_system.parameters.domain.usecases;

import com.jeseg.admin_system.parameters.domain.interfaces.ParameterInterface;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ParametersUseCase {

    private ParameterInterface parameterInterface;

    public List<String> parameterById(Long id){
        return parameterInterface.listadoParametros(id);
    }
}
