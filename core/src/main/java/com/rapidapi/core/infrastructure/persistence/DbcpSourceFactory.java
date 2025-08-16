package com.rapidapi.core.infrastructure.persistence;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbcpSourceFactory implements DataSourceFactory {

    private final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);
    
    private final BasicDataSource dataSource;
    
    public DbcpSourceFactory(String url, String username, String password) {
        dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxTotal(10);
        dataSource.setMinIdle(2);
        dataSource.setMaxIdle(5);
        // dataSource.setMaxWaitMillis(30000);
    }
    
    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
    
    @Override
    public void close() {
        try {
            if (dataSource != null) {
                dataSource.close();
            }
        } catch (Exception e) {
            logger.error("Error closing data source", e);
        }
    }
}