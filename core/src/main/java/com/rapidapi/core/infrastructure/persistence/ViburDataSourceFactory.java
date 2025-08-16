package com.rapidapi.core.infrastructure.persistence;

import javax.sql.DataSource;

import org.vibur.dbcp.ViburDBCPDataSource;

public class ViburDataSourceFactory implements DataSourceFactory {
    
    private final ViburDBCPDataSource dataSource;
    
    public ViburDataSourceFactory(String url, String username, String password) {
        dataSource = new ViburDBCPDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setPoolInitialSize(2);
        dataSource.setPoolMaxSize(10);
        dataSource.setConnectionIdleLimitInSeconds(600);
        dataSource.setTestConnectionQuery("SELECT 1");
        dataSource.start();
    }
    
    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
    
    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.terminate();
        }
    }
}