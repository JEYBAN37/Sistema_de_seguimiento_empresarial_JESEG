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
        ERROR_ID_USUARIO_NULO(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error ID Usuario Nulo", "El ID del usuario no puede ser nulo")),
        ERROR_GUARDAR_COMPANIA(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar", "Hay un error al guardar la compañia")),
        ERROR_GUARDAR_ROLE_NOMBRE_REPETIDO(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar", "Tienes roles duplicados")),
        ERROR_GUARDAR_COMPANIA_LOGO_VACIO(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar", "El logo de la compañia no puede estar vacio")),
        ERROR_GUARDAR_HIERARCHY_COMPANIA_NO_EXISTE(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar jerarquias", "La compañia no existe")),
        ERROR_ROL_NO_ENCONTRADO (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar", "El rol no fue encontrado")),
        ERROR_GUARDAR_COMPANIA_NOMBRE_REPETIDO(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar", "Esta compania ya existe")),
        ERROR_GUARDAR_ROLE(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar", "La compañia no existe")),
        ERROR_CARGO_NO_ENCONTRADO (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar", "El cargo no fue encontrado")),
        LISTADO_VACIO_ROLE (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar", "El listado de roles no puede estar vacio")),
        ROLE_YA_EXISTE_EN_COMPANIA (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar", "El rol ya existe en la compañia")),
        ERROR_FORMATO_CSV_INVALIDO (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar usuarios", "El formato del archivo es invalido (CSV)")),
        ERROR_USUARIO_NO_ENCONTRADO (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar usuarios", "El usuario a eliminar no fue encontrado")),
        ERROR_USUARIO_YA_EXISTE (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar usuarios", "El usuario ya existe en la compañia")),
        ERROR_GUARDAR_USUARIOS(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar usuarios", "La compañia no existe")),
        ERROR_GUARDAR_HIERARCHY(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar jerarquias", "La compañia no existe")),
        ERROR_FORMATO_FECHA_INVALIDO (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar usuarios", "El formato de la fecha es invalido (dd-MM-yyyy)")),
        ERROR_GUARDAR_HIERARCHY_NO_EXISTE(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar jerarquias no existe node", "El nodo no existe")),
        ERROR_ENCABEZADOS_CSV_INVALIDOS (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar usuarios", "Los encabezados del archivo son invalidos")),
        ERROR_CSV_SIN_ENCABEZADOS (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar usuarios", "El archivo no contiene encabezados")),
        ERROR_GUARDAR_TASK(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar tarea no existe node", "La tarea no existe")),
        ERROR_GUARDAR_APPROVAL(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al guardar la aprobacion no existe", "La aprobacion no existe")),
        ERROR_VERIFICAR_CARGO_NO_ENCONTRADO_NINUNO (new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error al verificar cargo", "El cargo no fue encontrado")),
        ERROR_ID_EMPRESA_NULO(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error ID Empresa Nulo", "El ID de la empresa no puede ser nulo")),
        ERROR_ID_TAREA_NULO(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Error ID Tarea Nulo", "El ID de la tarea no puede ser nulo")),
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

        public Exception build(String s) {
            return null;
        }


    }

    public BusinessException(Type type) {
        super(type.error);
    }

    public BusinessException(Type type, Throwable cause) {
        super(type.error, cause); // Asegúrate de que ApplicationException también reciba 'cause'
    }



}
