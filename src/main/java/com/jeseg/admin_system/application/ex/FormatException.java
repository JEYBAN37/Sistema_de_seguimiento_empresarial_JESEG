package com.jeseg.admin_system.application.ex;

import com.jeseg.admin_system.application.ex.codigos.CodigosErrorFormato;
import com.jeseg.admin_system.application.ex.codigos.TipoError;

import java.io.Serial;
import java.util.function.Supplier;

public class FormatException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public enum Type {

        ERROR_VALOR_ATRIBUTO_INVALIDO(new Error(CodigosErrorFormato.FORMATO_1, TipoError.FORMATO, "Valor atributo inv\u00E1lido", "El valor del atributo no cumple con el formato esperado")),;

        private final Error error;

        public FormatException build() {
            return new FormatException(this);
        }

        public Supplier<Throwable> defer() {
            return () -> new FormatException(this);
        }

        Type(Error error) {
            this.error = error;
        }

    }

    private FormatException(Type type) {
        super(type.error);
    }

}
