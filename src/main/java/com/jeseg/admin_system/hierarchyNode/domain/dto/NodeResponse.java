package com.jeseg.admin_system.hierarchyNode.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NodeResponse {
    private Long id;             // 1. sibling.id / n.id
    private String name;         // 2. sibling.name / n.name (MOVIDO AQUÍ)
    private Long idUser;         // 3. u.id
    private String nombreCompleto; // 4. u.nombreCompleto
}