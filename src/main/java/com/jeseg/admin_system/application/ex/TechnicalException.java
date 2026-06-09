package com.jeseg.admin_system.application.ex;

import com.jeseg.admin_system.application.ex.codigos.CodigosErrorTecnico;
import com.jeseg.admin_system.application.ex.codigos.TipoError;
import lombok.Getter;
import java.io.Serial;
import java.util.function.Supplier;

public class TechnicalException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    public enum Type {
        ERROR_BASE (new Error(CodigosErrorTecnico.TECNICO_1, TipoError.TECNICO, "Error Mensaje Corto", "descripcion especifica del error")),
        ERROR_EXCEPCION_RESPUESTA_ESTADO(new Error(CodigosErrorTecnico.TECNICO_2, TipoError.TECNICO, "Error en ResponseStatusException", "Se produjo un error en ResponseStatusException")),
        ERROR_NO_ENCONTRADO_ARCHIVO_CREDENCIALES(new Error(CodigosErrorTecnico.TECNICO_3, TipoError.TECNICO, "Error en credenciales", "CRÍTICO: No se encontró el archivo 'credentials.json' dentro de src/main/resources/\"")),
        ERROR_CLOUD_FILES_CONECTED(new Error(CodigosErrorTecnico.TECNICO_3, TipoError.TECNICO, "Error de conexión con Google Drive", "Se produjo un error al conectar con la API de Google Drive")),
        ERROR_EXCEPCION_GENERICA(new Error(CodigosErrorTecnico.TECNICO_3, TipoError.TECNICO, "Error genérico", "Se produjo un error genérico")),;
        ;
        private final Error error;

        public TechnicalException build() {
            return new TechnicalException(this);
        }

        public Supplier<Throwable> defer() {
            return () -> new TechnicalException(this);
        }

        Type(Error error) {
            this.error = error;
        }

    }

    private TechnicalException(Type type) {
        super(type.error);
    }

}
