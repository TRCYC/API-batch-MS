package com.rcyc.batchsystem.configuration;

import com.rcyc.batchsystem.util.CryptoUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CustomDataSourceConfig {

    @Value("${db.driver}")
    private String databaseDriver;

    private String getDecryptedEnv(String envVar) {
        String value = System.getenv(envVar);
        System.out.println("=============>" + value);
        return value != null ? CryptoUtil.decrypt(value) : null;
    }

    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;

    private String getDatabaseUrl() {
        if (databaseUrl == null) {
            databaseUrl = getDecryptedEnv("DB_URL");
        }
        return databaseUrl;
    }

    private String getDatabaseUsername() {
        if (databaseUsername == null) {
            databaseUsername = getDecryptedEnv("DB_USERNAME");
        }
        return databaseUsername;
    }

    private String getDatabasePassword() {
        if (databasePassword == null) {
            databasePassword = getDecryptedEnv("DB_PASSWORD");
        }
        return databasePassword;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(databaseDriver);
        dataSource.setJdbcUrl(getDatabaseUrl());
        dataSource.setUsername(getDatabaseUsername());
        dataSource.setPassword(getDatabasePassword());
        System.out.println(dataSource.getJdbcUrl());
        return dataSource;
    }
} 