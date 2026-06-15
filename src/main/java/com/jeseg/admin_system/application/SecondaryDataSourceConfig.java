package com.jeseg.admin_system.application;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jakarta.annotation.PostConstruct;
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
        basePackages = {
                "com.jeseg.admin_system.canalizaciones.infrastructure.repository",
                "com.jeseg.admin_system.parameters.infrastructure.repository"
        },
        entityManagerFactoryRef = "secondaryEntityManager",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDataSourceConfig {
    private Session sshSession;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create()
                .type(com.zaxxer.hikari.HikariDataSource.class)
                .build();
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean secondaryEntityManager(
            EntityManagerFactoryBuilder builder) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "none");

        return builder
                .dataSource(secondaryDataSource())
                .packages(
                        "com.jeseg.admin_system.canalizaciones.infrastructure.entity",
                                        "com.jeseg.admin_system.parameters.infrastructure.entity"
                        )
                .persistenceUnit("secondary")
                .properties(properties)
                .build();
    }
    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManager")
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * 🚀 ARRANQUE AUTOMÁTICO DEL TÚNEL
     * @PostConstruct asegura que el túnel se abra ANTES de que Hibernate intente conectar
     */
    @PostConstruct
    public void openSSHTunnel() {

        try {
            JSch jsch = new JSch();
            // Cambia los datos por tus credenciales reales de MochaHost
            sshSession = jsch.getSession("agsolutic", "195.250.27.25", 22);
            sshSession.setPassword("Query*AP5");

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(config);

            // 🚀 LOS LATIDOS: Configura el Keep-Alive en milisegundos (30 segundos)
            sshSession.setServerAliveInterval(30000);
            // Si falla 3 veces consecutivas, recién ahí se da por muerta la conexión
            sshSession.setServerAliveCountMax(3);

            System.out.println("🔄 Iniciando túnel SSH con persistencia Keep-Alive...");
            sshSession.connect();

            // Mapeo del puerto local (ej. 3307) hacia el 3306 del servidor de MochaHost
            sshSession.setPortForwardingL(3307, "127.0.0.1", 3306);
            System.out.println("✅ Túnel SSH establecido en el puerto local 3307");

        } catch (Exception e) {
            System.err.println("❌ Error al levantar el túnel SSH: " + e.getMessage());
        }
    }
}