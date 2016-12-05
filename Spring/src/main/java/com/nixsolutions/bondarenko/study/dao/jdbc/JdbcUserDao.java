package com.nixsolutions.bondarenko.study.dao.jdbc;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.user.library.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuliya Bondarenko
 */
public class JdbcUserDao extends AbstractJdbcDao implements UserDao {
    @Override
    public void create(User user) throws SQLException {
        String sql = "INSERT into User (login, password, email, firstName, lastName, birthday, id_role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = createConnection()) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getFirstName());
                preparedStatement.setString(5, user.getLastName());
                preparedStatement.setDate(6, user.getBirthday());
                preparedStatement.setLong(7, user.getRole().getId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new SQLException("Error while creating new user", e);
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE User SET password = ?, email = ?, firstName = ?, lastName = ?, birthday = ?, id_role = ? where login = ?";

        try (Connection connection = createConnection()) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, user.getPassword());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getFirstName());
                preparedStatement.setString(4, user.getLastName());
                preparedStatement.setDate(5, user.getBirthday());
                preparedStatement.setLong(6, user.getRole().getId());
                preparedStatement.setString(7, user.getLogin());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new SQLException("Error while updating user", e);
        }

    }

    @Override
    public void remove(User user) throws SQLException {
        String sql = "DELETE FROM User " + "WHERE id = ?";

        try (Connection connection = createConnection()) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(sql)) {

                preparedStatement.setLong(1, user.getId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new SQLException("Error while removing user", e);
        }

    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.id \"user_id\", u.login, u.password, u.email, u.firstName, u.lastName, u.birthday, r.id \"role_id\", r.name \"role_name\""
                + "FROM User u, Role r WHERE u.id_role=r.id";

        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    users.add(new User(resultSet.getLong("user_id"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getDate("birthday"),
                            resultSet.getLong("role_id"),
                            resultSet.getString("role_name")));
                }
                return users;
            }

        } catch (SQLException e) {
            throw new SQLException("Error while finding all users", e);
        }
    }

    @Override
    public User findById(Long id) throws Exception {
        String sql = "SELECT u.id \"user_id\", u.login, u.password, u.email, u.firstName, u.lastName, u.birthday, r.id \"role_id\", r.name \"role_name\""
                + "FROM User u, Role r WHERE u.id_role=r.id AND u.id = ? ";

        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getLong("user_id"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getDate("birthday"),
                            resultSet.getLong("role_id"),
                            resultSet.getString("role_name"));
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new SQLException(
                    "Error while searching user with id = " + id, e);
        }
    }

    @Override
    public User findByLogin(String login) throws SQLException {
        String sql = "SELECT u.id \"user_id\", u.login, u.password, u.email, u.firstName, u.lastName, u.birthday, r.id \"role_id\", r.name \"role_name\""
                + "FROM User u, Role r WHERE u.id_role=r.id AND u.login = ? ";

        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(sql)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getLong("user_id"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getDate("birthday"),
                            resultSet.getLong("role_id"),
                            resultSet.getString("role_name"));
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new SQLException(
                    "Error while searching user with login = " + login, e);
        }
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT u.id \"user_id\", u.login, u.password, u.email, u.firstName, u.lastName, u.birthday, r.id \"role_id\", r.name \"role_name\""
                + "FROM User u, Role r WHERE u.id_role=r.id AND u.email = ? ";

        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getLong("user_id"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getDate("birthday"),
                            resultSet.getLong("role_id"),
                            resultSet.getString("role_name"));
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new SQLException(
                    "Error while searching user with email = " + email, e);
        }
    }
}
