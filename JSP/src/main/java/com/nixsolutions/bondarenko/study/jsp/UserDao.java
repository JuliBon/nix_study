package com.nixsolutions.bondarenko.study.jsp;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Yuliya Bondarenko
 */
public interface UserDao {
    void create(User user) throws SQLException;

    void update(User user) throws SQLException;

    void remove(User user) throws SQLException;

    List<User> findAll() throws SQLException;

    User findByLogin(String login) throws SQLException;

    User findByEmail(String email) throws SQLException;
}