package com.jeseg.admin_system.company.infrastructure.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@Table(name = "companies", uniqueConstraints = {
        @UniqueConstraint(name = "uk_companies_name", columnNames = {"name"})
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID numérico para IDENTITY

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Column(nullable = false)
    private boolean status = true; // Valor por defecto

    @Column(length = 255, nullable = false)
    @NotBlank(message = "El logo es obligatorio")
    private String logoUrl;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "El color es obligatorio")
    private String color;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private java.util.Date createdAt;
}