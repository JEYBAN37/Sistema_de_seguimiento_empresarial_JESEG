package com.jeseg.admin_system.parameters.infrastructure.service;

import com.jeseg.admin_system.parameters.domain.dto.ParametersResponse;
import com.jeseg.admin_system.parameters.domain.usecases.ParametersUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ParameterService {
    private final ParametersUseCase parametersUseCase;
    public ParametersResponse getParametersCronograma() {
        return parametersUseCase.parametersCronograma();
    }
}
