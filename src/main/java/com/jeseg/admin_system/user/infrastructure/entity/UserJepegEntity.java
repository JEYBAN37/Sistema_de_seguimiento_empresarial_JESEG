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

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private Boolean active;

    @ManyToOne
    private CompanyEntity company;

    @ManyToOne
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "hierarchy_node_id")
    private HierarchyNodeEntity hierarchyNode;

    private LocalDate fechaInicioContrato;

    private LocalDate fechaTerminoContrato;

    @Column(length = 200)
    private String objeto;

    @Column(length = 5)
    private String contratistaTipoIdentificacion;

    @Column(length = 50)
    private String numeroSupervisor;

    @Column(length = 100)
    private String nombreSupervisor;

    private BigDecimal pagado;

    private BigDecimal valorContratado;

    private String idRecurso;

    private BigDecimal saldo;


}
