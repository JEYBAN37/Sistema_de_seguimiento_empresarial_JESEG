package com.jeseg.admin_system.application.ex;


import com.jeseg.admin_system.application.ex.codigos.CodigosErrorNegocio;
import com.jeseg.admin_system.application.ex.codigos.TipoError;

import java.io.Serial;
import java.util.function.Supplier;

public class BusinessException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public enum Type {
        ERROR_BASE(new Error(CodigosErrorNegocio.NEGOCIO_1, TipoError.NEGOCIO, "Error Mensaje Corto", "descripcion especifica del error")),
        ERROR_GUARDAR_COMPANIA(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar", "Hay un error al guardar la compañia")),
        ERROR_CONSULTANTE_NULL(new Error(CodigosErrorNegocio.NEGOCIO_3, TipoError.NEGOCIO, "Consultante nulo", "El consultante no puede ser nulo")),;

        private final Error error;

        public BusinessException build() {
            return new BusinessException(this);
        }

        public Supplier<Throwable> defer() {
            return () -> new BusinessException(this);
        }
        public BusinessException build(Throwable cause) {
            return new BusinessException(this, cause);
        }
        Type(Error error) {
            this.error = error;
        }

    }

    public BusinessException(Type type) {
        super(type.error);
    }

    public BusinessException(Type type, Throwable cause) {
        super(type.error, cause); // Asegúrate de que ApplicationException también reciba 'cause'
    }

}
