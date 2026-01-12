package com.jeseg.admin_system.role.infrastructure.entity;
import com.jeseg.admin_system.company.domain.model.Company;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@Builder
@Table(name = "roles")
@AllArgsConstructor // Genera constructor con todos los campos
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String name; // ADMIN, JEFE, COORDINADOR

    @ManyToOne
    private CompanyEntity company;
}