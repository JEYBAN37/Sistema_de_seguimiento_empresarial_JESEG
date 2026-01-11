package com.jeseg.admin_system.application.ex;

import com.jeseg.admin_system.application.ex.codigos.TipoError;

public record Error(
        String id,
        TipoError tipo,
        String mensaje,
        String detalle) {
}
