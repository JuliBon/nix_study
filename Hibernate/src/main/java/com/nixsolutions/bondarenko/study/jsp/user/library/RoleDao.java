package com.nixsolutions.bondarenko.study.jsp.user.library;

import java.sql.SQLException;

/**
 * @author Yuliya Bondarenko
 */
public interface RoleDao {
    void create(Role role) throws SQLException;

    void update(Role role) throws SQLException;

    void remove(Role role) throws SQLException;

    /**
     * Search role by name in the database
     * @param name - name of the role
     * @return instance of Role if there is a role with specified name
     * null if there is no role with specified name
     * @throws SQLException if SQLException occurred while executing search
     */
    Role findByName(String name) throws SQLException;
}
