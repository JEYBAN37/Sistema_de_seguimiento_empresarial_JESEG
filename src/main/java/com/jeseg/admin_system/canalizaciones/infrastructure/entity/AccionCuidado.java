package com.jeseg.admin_system.canalizaciones.infrastructure.entity;

import com.jeseg.admin_system.parameters.domain.dto.Responsable;
import com.jeseg.admin_system.parameters.infrastructure.entity.ResponsableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accioncuidado")
public class AccionCuidado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numerodoc", referencedColumnName = "numerodoc") // Revisa si en MySQL la columna se llama 'numerodoc' en minúscula
    private Persona persona;

    @Column(name = "situaciones_priorizadas") // Asegúrate de que se llame así en tu MySQL (todo en minúsculas)
    private String situacionesPriorizadas;

    @Column(name = "logros_alcanzados") // Asegúrate de que se llame así en tu MySQL (todo en minúsculas)
    private String logrosAlcanzados;

    @Column(name = "responsable_familia") // Asegúrate de que se llame así en tu MySQL (todo en minúsculas)
    private String responsableFamilia;


    @Column(name = "fecha_compromiso") // Asegúrate de que se llame así en tu MySQL (todo en minúsculas)
    private LocalDate fechaCompromiso;

    @Column(name = "fecha_seguimiento") // Asegúrate de que se llame así en tu MySQL
    private LocalDate fechaSeguimiento;

    @Column(name = "seguimiento_compromiso") // Asegúrate de que se llame así en tu MySQL
    private String seguimientoCompromiso;


    private String estado;
    @Column(name = "cedula_responsable_seguimiento")
    private String cedulaResponsableSeguimiento;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cedula_responsable_seguimiento", referencedColumnName = "numero", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ResponsableEntity responsableRelacion;
}
