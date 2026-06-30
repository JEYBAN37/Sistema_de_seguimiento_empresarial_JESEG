package com.jeseg.admin_system.canalizaciones.domain.usecase;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.canalizaciones.domain.dto.ContenedorCache;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaAskRequest;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonaUpdateState;
import com.jeseg.admin_system.canalizaciones.domain.dto.PersonasResponse;
import com.jeseg.admin_system.canalizaciones.domain.intreface.PersonaInterface;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PersonaApiUseCase {
    private final PersonaInterface personaInterface;
    public List<PersonasResponse> getAll (PersonaAskRequest request){
        return personaInterface.sendPersonasCanalizacion(request);
    }

    public void updateState (PersonaUpdateState request){
        personaInterface.updateState(request);
    }

    public List<PersonasResponse> obtenerDeBaseDatos (PersonaAskRequest request){

        if (request.getPeriodoBusqueda() == null || request.getPeriodoBusqueda().isBlank()) {
            throw BusinessException.Type.ERROR_FECHA_FILTRO_OBLIGATORIA.build();
        }

        LocalDate fecha = LocalDate.parse(request.getPeriodoBusqueda());
        String llaveCache = generarLlaveDinamica(fecha, request);

        ContenedorCache cache = personaInterface.obtenerDeCache(llaveCache);

        if (cache == null) {
            List<PersonasResponse> todosLosDatos = personaInterface.obtenerTodoElDia(request);

            LocalDateTime maxFecha = buscarUltimoTimestamp(todosLosDatos);

            personaInterface.guardarEnCache(llaveCache, todosLosDatos, maxFecha);
            return todosLosDatos;
        }

        LocalDateTime ultimoCheck = cache.getUltimaActualizacion();
        List<PersonasResponse> registrosNuevos = personaInterface.obtenerNuevosDesde(ultimoCheck, request);

        if (registrosNuevos.isEmpty()) {
            return cache.getData();
        }

        List<PersonasResponse> listaActualizada = new ArrayList<>(cache.getData());
        listaActualizada.addAll(registrosNuevos); // Añadimos los nuevos al final

        LocalDateTime nuevoMaxFecha = buscarUltimoTimestamp(registrosNuevos);
        personaInterface.guardarEnCache(llaveCache, listaActualizada, nuevoMaxFecha);

        return listaActualizada;
    }

    private LocalDateTime buscarUltimoTimestamp(List<PersonasResponse> lista) {
        return lista.stream()
                .map(PersonasResponse::getFechaRegistro)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());
    }

    private String generarLlaveDinamica(LocalDate fecha, PersonaAskRequest request) {
        StringBuilder sb = new StringBuilder(128);
        sb.append("c:=").append(fecha);

        String busqueda = request.getBusqueda();
        if (busqueda != null && !busqueda.isBlank()) {
            sb.append(":b=").append(busqueda.trim().toLowerCase());
        }

        String deteccion = request.getDeteccionTemprana();
        if (deteccion != null && !deteccion.isBlank()) {
            sb.append(":d=").append(deteccion.trim().toLowerCase());
        }

        String urgencia = request.getUrgencia();
        if (urgencia != null && !urgencia.isBlank()) {
            sb.append(":u=").append(urgencia.trim().toLowerCase());
        }

        String rias = request.getRias();
        if (rias != null && !rias.isBlank()) {
            sb.append(":r=").append(rias.trim().toLowerCase());
        }

        List<String> ips = request.getIps();
        if (ips != null && !ips.isEmpty()) {
            String ipsUnidas = String.join(",", ips).trim().toLowerCase();
            if (!ipsUnidas.isBlank()) {
                sb.append(":i=").append(ipsUnidas);
            }
        }

        String periodo = request.getPeriodoBusquedaBetween();
        if (periodo != null && !periodo.isBlank()) {
            sb.append(":p=").append(periodo.trim());
        }

        return sb.toString();
    }


}
