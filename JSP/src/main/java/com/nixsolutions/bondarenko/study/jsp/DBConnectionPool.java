package com.nixsolutions.bondarenko.study.jsp;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Yulya Bondarenko
 */
public class DBConnectionPool {
    private BasicDataSource basicDataSource;
    private static DBConnectionPool connectionPool;

    private DBConnectionPool(Properties dbProperties) {
        try {
            basicDataSource = new BasicDataSource();
            basicDataSource
                    .setDriverClassName(dbProperties.getProperty("driver"));
            basicDataSource.setUsername(dbProperties.getProperty("username"));
            basicDataSource.setPassword(dbProperties.getProperty("password"));
            basicDataSource.setUrl(dbProperties.getProperty("db.url"));

            basicDataSource.setMaxTotal(-1);
            basicDataSource.setMaxOpenPreparedStatements(180);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnectionPool getInstance(Properties dbProperties)
            throws SQLException {
        if (connectionPool == null) {
            connectionPool = new DBConnectionPool(dbProperties);
        }
        return connectionPool;
    }

    public Connection getConnection(boolean autoCommit) throws SQLException {
        Connection connection = this.basicDataSource.getConnection();
        connection.setAutoCommit(autoCommit);
        return connection;
    }
}
