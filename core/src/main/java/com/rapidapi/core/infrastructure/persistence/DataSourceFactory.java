package com.rapidapi.core.infrastructure.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DataSourceFactory {

    Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);

    DataSource getDataSource();

    default Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    void close();

    enum PoolType {
        DBCP2, HIKARI, VIBUR
    }

    static DataSourceFactory create(PoolType poolType, String url, String username, String password) {
        return switch (poolType) {
            case DBCP2 -> new DbcpSourceFactory(url, username, password);
            case HIKARI -> new HikariDataSourceFactory(url, username, password);
            case VIBUR -> new ViburDataSourceFactory(url, username, password);
        };
    }

}