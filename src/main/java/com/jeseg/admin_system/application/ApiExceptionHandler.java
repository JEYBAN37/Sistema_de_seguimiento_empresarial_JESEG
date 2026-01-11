package com.jeseg.admin_system.application;

import com.jeseg.admin_system.application.error.dto.ErrorDetail;
import com.jeseg.admin_system.application.error.dto.ErrorResponse;
import com.jeseg.admin_system.application.ex.ApplicationException;
import com.jeseg.admin_system.application.ex.TechnicalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;


@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException exception) {
        ErrorDetail errorDetail = toErrorDetail(TechnicalException.Type.ERROR_EXCEPCION_RESPUESTA_ESTADO.build());
        errorDetail = errorDetail.toBuilder()
                .detail(exception.getMessage())
                .build();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(Collections.singletonList(errorDetail))
                .build();

        return new ResponseEntity<>(errorResponse, exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorDetail errorDetail = toErrorDetail(TechnicalException.Type.ERROR_EXCEPCION_GENERICA.build());
        errorDetail = errorDetail.toBuilder()
                .detail(ex.getMessage())
                .build();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(Collections.singletonList(errorDetail))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<ErrorResponse> handleBusinessExceptions(ApplicationException exception) {
        ErrorDetail errorDetail = toErrorDetail(exception);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(Collections.singletonList(errorDetail))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    private ErrorDetail toErrorDetail(ApplicationException exception) {
        // Si la excepción tiene una causa (el error real), la usamos para el detalle
        String technicalDetail = (exception.getCause() != null)
                ? exception.getCause().getMessage()
                : exception.getDetail();

        return ErrorDetail.builder()
                .id(exception.getId())
                .type(exception.getErrorType().name())
                .message(exception.getMessage()) // "Error al guardar"
                .detail(technicalDetail)         // "Schema 'COMPANIES' not found"
                .build();
    }
}
