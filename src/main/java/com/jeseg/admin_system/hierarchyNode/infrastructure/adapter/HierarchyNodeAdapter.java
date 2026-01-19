package com.jeseg.admin_system.hierarchyNode.infrastructure.adapter;

import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;
import com.jeseg.admin_system.hierarchyNode.domain.dto.*;
import com.jeseg.admin_system.hierarchyNode.domain.intreface.HierarchyNodeInterface;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.hierarchyNode.infrastructure.repository.HierarchyNodeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;


@Repository
@AllArgsConstructor
public class HierarchyNodeAdapter implements HierarchyNodeInterface {

    private final HierarchyNodeRepository hierarchyNodeRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public void saveHierarchy(HierarchyNodeCreateRequest hierarchy) {
        List<HierarchyEmployee> originales = hierarchy.getJerarquias();
        List<List<HierarchyEmployee>> nivelesAgrupados = new ArrayList<>();

        // Nombres procesados para saber quién ya tiene "lugar" en el árbol
        Set<String> procesados = new HashSet<>();

        // 1. Obtener el Nivel 0 (Las Raíces)
        List<HierarchyEmployee> nivelActual = originales.stream()
                .filter(h -> h.getParent() == null || h.getParent().equalsIgnoreCase("null") || h.getParent().isBlank())
                .toList();

        while (!nivelActual.isEmpty()) {
            // Añadir el nivel completo a nuestra lista de listas
            nivelesAgrupados.add(nivelActual);

            // Registrar nombres de este nivel como procesados (en minúsculas para evitar errores)
            for (HierarchyEmployee h : nivelActual) {
                procesados.add(h.getName().toUpperCase().trim());
            }

            // 2. Buscar quiénes son hijos de los que acabamos de procesar
            List<HierarchyEmployee> siguienteNivel = new ArrayList<>();
            for (HierarchyEmployee h : originales) {
                String padreNombre = h.getParent() != null ? h.getParent().toUpperCase().trim() : "";
                String nombreActual = h.getName().toUpperCase().trim();

                // Si el padre ya está procesado pero el hijo aún no, pertenece al siguiente nivel
                if (procesados.contains(padreNombre) && !procesados.contains(nombreActual)) {
                    siguienteNivel.add(h);
                }
            }

            // Mover el puntero al siguiente nivel para la próxima iteración
            nivelActual = siguienteNivel;
        }

        // 3. Guardar en Base de Datos nivel por nivel
        Map<String, List<HierarchyNodeEntity>> nameToSavedNodes = new HashMap<>();

        for (List<HierarchyEmployee> nivel : nivelesAgrupados) {
            for (HierarchyEmployee dto : nivel) {
                // Aquí ejecutas tu lógica de crear plazas (for i < quantity)
                // Como vamos nivel por nivel, el 'parent' SIEMPRE existirá en el mapa
                List<HierarchyNodeEntity> plazasGuardadas = guardarPlazas(dto, nameToSavedNodes, hierarchy.getCompany());

                nameToSavedNodes.put(dto.getName().toUpperCase().trim(), plazasGuardadas);
            }
        }
    }

    private List<HierarchyNodeEntity> guardarPlazas(
            HierarchyEmployee dto,
            Map<String, List<HierarchyNodeEntity>> nameToSavedNodes,
            Long company
    ) {
        List<HierarchyNodeEntity> currentLevelPlazas = new ArrayList<>();
        String currentName = dto.getName().toUpperCase().trim();

        for (int i = 0; i < dto.getQuantity(); i++) {
            // Generar nombre de la plaza (ej: Vendedor #1, Vendedor #2 o solo Gerente)
            String plazaName = (dto.getQuantity() > 1)
                    ? currentName + " #" + (i + 1)
                    : currentName;

            HierarchyNodeEntity node = HierarchyNodeEntity.builder()
                    .name(plazaName)
                    .company(companyRepository.getReferenceById(company))
                    .build();

            // Lógica de asignación de padre
            String parentName = (dto.getParent() != null) ? dto.getParent().toUpperCase().trim() : null;

            if (parentName != null && !parentName.equals("null") && !parentName.isEmpty()) {
                List<HierarchyNodeEntity> parentPlazas = nameToSavedNodes.get(parentName);

                if (parentPlazas != null && !parentPlazas.isEmpty()) {
                    // Distribución Round-Robin: repartimos los hijos entre las plazas del padre
                    int parentIndex = i % parentPlazas.size();
                    node.setParent(parentPlazas.get(parentIndex));
                }
            }

            // Guardar en base de datos
            currentLevelPlazas.add(hierarchyNodeRepository.save(node));
        }

        return currentLevelPlazas;
    }

    @Override
    public List<HierarchyNodeResponse> getResumenHierarchy(Long id) {
        List<HierarchyCountResponse> rawData = hierarchyNodeRepository.countPlazasPorNivel(id);

        // Agrupamos y sumamos todos los contadores
        Map<String, HierarchyCountResponse> agrupadoLimpio = rawData.stream()
                .collect(Collectors.toMap(
                        dto -> dto.getName().replaceAll("\\s#\\d+", ""), // Key: Nombre limpio
                        dto -> dto, // Value inicial
                        (existente, nuevo) -> new HierarchyCountResponse( // Función de combinación (sumar)
                                existente.getName(),
                                existente.getTotal() + nuevo.getTotal(),
                                existente.getOcupadas() + nuevo.getOcupadas(),
                                existente.getDisponibles() + nuevo.getDisponibles()
                        )
                ));

        // Convertimos el mapa al DTO de respuesta final
        return agrupadoLimpio.entrySet().stream()
                .map(e -> HierarchyNodeResponse.builder()
                        .nombre(e.getKey())
                        .plazas(e.getValue().getTotal())
                        .disponibles(e.getValue().getDisponibles())
                        .ocupadas(e.getValue().getOcupadas())
                        .build())
                .toList();
    }

    @Override
    public HierarchyNodeTaskResponse getStructureTask(Long idHierarchyNode) {

        if(idHierarchyNode == null) {
            throw BusinessException.Type.ERROR_ID_USUARIO_NULO.build();
        }

        List<NodeResponse> team = hierarchyNodeRepository.findSubordinatesWithUsers(idHierarchyNode);

        List<NodeResponse> chip = hierarchyNodeRepository.findSupervisorAndPeers(idHierarchyNode);


        return HierarchyNodeTaskResponse.builder()
                .subordinados(team)
                .supervisores(chip)
                .build();
    }


}
