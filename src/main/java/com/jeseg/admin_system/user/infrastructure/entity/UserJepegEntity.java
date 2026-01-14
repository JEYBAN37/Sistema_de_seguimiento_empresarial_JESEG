package com.jeseg.admin_system.user.infrastructure.entity;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.hierarchyNode.infrastructure.entity.HierarchyNodeEntity;
import com.jeseg.admin_system.role.infrastructure.entity.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_cedula", columnNames = {"cedula"})
})
@Data
@Builder
@AllArgsConstructor // Necesario para @Builder
@NoArgsConstructor  // <--- ESTA ES LA QUE SOLUCIONA EL ERROR
public class UserJepegEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String username;

    private String password;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreCompleto;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "La cedula es obligatorio")
    private String cedula;

    @Column(length = 50)
    private String telefono;

    @Column(length = 50)
    private String contrato;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = false;

    @ManyToOne
    private CompanyEntity company;

    @ManyToOne
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "hierarchy_node_id")
    private HierarchyNodeEntity hierarchyNode;

}
