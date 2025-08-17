package com.rapidapi.core.infrastructure.persistence;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariDataSourceFactory implements DataSourceFactory {

    private final HikariDataSource dataSource;

    public HikariDataSourceFactory(String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        // config.setDriverClassName("org.postgresql.Driver");
        // for MySQL com.mysql.cj.jdbc.Driver
        // assuming that we have a dbConfig where we store the values
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        this.dataSource = new HikariDataSource(config);
    }

    @Override
    public DataSource getDataSource() {
        
        return dataSource;
    }

    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}