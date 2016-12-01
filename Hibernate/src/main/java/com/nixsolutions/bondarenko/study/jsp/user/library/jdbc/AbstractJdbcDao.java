package com.nixsolutions.bondarenko.study.jsp.user.library.jdbc;

import com.nixsolutions.bondarenko.study.jsp.user.library.DBConnectionPool;
import com.nixsolutions.bondarenko.study.jsp.user.library.PropertySource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Yuliya Bondarenko
 */
public abstract class AbstractJdbcDao {

    public Connection createConnection() throws SQLException {
        Properties properties = PropertySource
                .getDbProperties();
        return DBConnectionPool.getInstance(properties)
                .getConnection(false);
    }
}
