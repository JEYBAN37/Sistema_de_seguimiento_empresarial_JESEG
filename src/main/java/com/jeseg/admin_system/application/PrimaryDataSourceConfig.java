package com.jeseg.admin_system.application;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.jeseg.admin_system.approval.infrastructure.repository",
                "com.jeseg.admin_system.company.infrastructure.repository",
                "com.jeseg.admin_system.document.infrastructure.repository",
                "com.jeseg.admin_system.hierarchyNode.infrastructure.repository",
                "com.jeseg.admin_system.role.infrastructure.repository",
                "com.jeseg.admin_system.task.infrastructure.repository",
                "com.jeseg.admin_system.user.infrastructure.repository"},
        entityManagerFactoryRef = "primaryEntityManager",
        transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
                .type(com.zaxxer.hikari.HikariDataSource.class)
                .build();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean primaryEntityManager(
            EntityManagerFactoryBuilder builder) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update"); // 👈 solo primary

        return builder
                .dataSource(primaryDataSource())
                .packages("com.jeseg.admin_system.approval.infrastructure.entity",
                        "com.jeseg.admin_system.company.infrastructure.entity",
                        "com.jeseg.admin_system.document.infrastructure.entity",
                        "com.jeseg.admin_system.hierarchyNode.infrastructure.entity",
                        "com.jeseg.admin_system.role.infrastructure.entity",
                        "com.jeseg.admin_system.task.infrastructure.entity",
                        "com.jeseg.admin_system.user.infrastructure.entity")
                .persistenceUnit("primary")
                .properties(properties)
                .build();
    }

    @Bean
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManager")
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}