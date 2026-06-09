package com.jeseg.admin_system.hierarchyNode.infrastructure.entity;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.role.infrastructure.entity.RoleEntity;
import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "hierarchy_nodes")
@Data
@Builder
@AllArgsConstructor // Necesario para @Builder
@NoArgsConstructor  // <--- ESTA ES LA QUE SOLUCIONA EL ERROR
public class HierarchyNodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private HierarchyNodeEntity parent;

    @OneToMany(mappedBy = "hierarchyNode")
    private List<UserJepegEntity> users;

}
