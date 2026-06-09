package com.jeseg.admin_system.hierarchyNode.infrastructure.repository;

import com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyCountResponse;
import com.jeseg.admin_system.hierarchyNode.domain.dto.NodeResponse;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HierarchyNodeRepository extends JpaRepository<HierarchyNodeEntity, Long> {


    @Query("SELECT new com.jeseg.admin_system.hierarchyNode.domain.dto.HierarchyCountResponse(" +
            "h.name, " +
            "COUNT(h), " + // Total de plazas
            "SUM(CASE WHEN h.users IS NOT EMPTY THEN 1 ELSE 0 END), " + // Ocupadas
            "SUM(CASE WHEN h.users IS EMPTY THEN 1 ELSE 0 END)) " +     // Disponibles
            "FROM HierarchyNodeEntity h " +
            "WHERE h.company.id = :companyId " +
            "GROUP BY h.name")
    List<HierarchyCountResponse> countPlazasPorNivel(@Param("companyId") Long companyId);

    @Query("SELECT h FROM HierarchyNodeEntity h " +
            "WHERE h.name LIKE :namePrefix || ' %' " + // Busca "Auxiliar #" o "Auxiliar "
            "OR h.name = :namePrefix " +               // O el nombre exacto por si no tiene numeral
            "AND h.company.id = :companyId " +
            "AND h.users IS EMPTY " +
            "ORDER BY h.name ASC") // Para que asigne en orden: #1, luego #2, etc.
    List<HierarchyNodeEntity> findAvailableNodesByPrefix(
            @Param("namePrefix") String namePrefix,
            @Param("companyId") Long companyId);



    @Query("SELECT h FROM HierarchyNodeEntity h " +
            "WHERE h.company.id = :companyId " +
            "AND h.users IS EMPTY " +
            "ORDER BY h.name ASC")
    List<HierarchyNodeEntity> findAllAvailableNodes(@Param("companyId") Long companyId);


    @Query("SELECT new com.jeseg.admin_system.hierarchyNode.domain.dto.NodeResponse(" +
            "n.id, n.name, u.id, u.nombreCompleto, r.name, u.telefono) " + // Cambiado r.rol a r.name
            "FROM HierarchyNodeEntity n " +
            "LEFT JOIN n.users u " +
            "LEFT JOIN u.role r " + // Unimos con la tabla de roles
            "WHERE n.parent.id = :nodeId")
    List<NodeResponse> findSubordinatesWithUsers(@Param("nodeId") Long nodeId);

    // 2. Supervisor y Compañeros con su Rol
    @Query("SELECT new com.jeseg.admin_system.hierarchyNode.domain.dto.NodeResponse(" +
            "node.id, node.name, u.id, u.nombreCompleto, r.name, u.telefono) " +
            "FROM HierarchyNodeEntity me " +
            "JOIN me.parent supervisor " +
            "JOIN HierarchyNodeEntity node ON (node.id = supervisor.id OR node.parent.id = supervisor.id) " +
            "LEFT JOIN node.users u " +
            "LEFT JOIN u.role r " + // Unimos con la tabla de roles
            "WHERE me.id = :nodeId")
    List<NodeResponse> findSupervisorAndPeers(@Param("nodeId") Long nodeId);
}
