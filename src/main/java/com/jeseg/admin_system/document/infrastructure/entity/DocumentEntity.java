package com.jeseg.admin_system.document.infrastructure.entity;
import com.jeseg.admin_system.company.infrastructure.entity.CompanyEntity;
import com.jeseg.admin_system.task.infrastructure.entity.TaskEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "documents")
@AllArgsConstructor // Genera constructor con todos los campos
@NoArgsConstructor
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    @NotBlank(message = "El filename es obligatorio")
    private String filename;

    @Column(length = 500, nullable = false)
    @NotBlank(message = "El url es obligatorio")
    private String url;

    @ManyToOne
    private TaskEntity task;
}