package com.nixsolutions.bondarenko.study.jsp.user.library.dao;

import com.nixsolutions.bondarenko.study.jsp.user.library.Role;
import com.nixsolutions.bondarenko.study.jsp.user.library.RoleDao;

import java.sql.*;

/**
 * @author Yuliya Bondarenko
 */
public class JdbcRoleDao extends AbstractJdbcDao implements RoleDao {
    @Override
    public void create(Role role) throws SQLException {
        String sql = "INSERT into Role (id, name) VALUES (?, ?)";

        String errorMessage = "Error while creating new role";
        try (Connection connection = createConnection()) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(sql)) {

                preparedStatement.setLong(1, role.getId());
                preparedStatement.setString(2, role.getName());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new SQLException(errorMessage, e);
        }
    }

    @Override
    public void update(Role role) throws SQLException {
        String sql = "UPDATE Role " + "SET name = ? WHERE id = ?";

        try (Connection connection = createConnection()) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(sql)) {
                preparedStatement.setString(1, role.getName());
                preparedStatement.setLong(2, role.getId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new SQLException("Error while updating role", e);
        }
    }

    @Override
    public void remove(Role role) throws SQLException {
        String sql = "DELETE FROM Role " + "WHERE id = ?";

        try (Connection connection = createConnection()) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(sql)) {

                preparedStatement.setLong(1, role.getId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new SQLException("Error while removing role", e);
        }
    }

    @Override
    public Role findByName(String name) throws SQLException {
        String sql = "SELECT id, name FROM Role WHERE name = ?";

        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Role(resultSet.getLong("id"),
                            resultSet.getString("name"));
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error while removing role",
                    e);
        }
    }
}
