package com.jeseg.admin_system.company.infrastructure.entity;



import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Data
@Table(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean status;

    // @JoinTable(name = "users", joinColumns = @JoinColumn(name = "company_id"))
    @Column(columnDefinition = "TEXT") // Mejor usar TEXT por si la lista de nombres es larga
    private String users;

    @Column(length = 255)
    private String logoUrl;

    @Column(length = 50)
    private String color;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;
}
