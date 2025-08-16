package com.rapidapi.core.application.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.rapidapi.core.config.DataSourceConfig;
import com.rapidapi.core.infrastructure.persistence.DataSourceFactory;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public abstract class BaseService {
    
    @Inject
    private DataSourceConfig dataSourceConfig;
    
    private DataSourceFactory databaseFactory;
    
    @PostConstruct
    public void init() {
        if (databaseFactory == null) {
            DataSourceFactory.PoolType type = DataSourceFactory.PoolType.valueOf(
                dataSourceConfig.getPoolType().toUpperCase());
            
            databaseFactory = DataSourceFactory.create(
                type,
                dataSourceConfig.getUrl(),
                dataSourceConfig.getUsername(),
                dataSourceConfig.getPassword()
            );
        }
    }
    
    @PreDestroy
    public void cleanup() {
        if (databaseFactory != null) {
            databaseFactory.close();
            databaseFactory = null;
        }
    }
    
    protected Connection getConnection() throws SQLException {
        return databaseFactory.getConnection();
    }
}