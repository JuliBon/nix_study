package com.nixsolutions.bondarenko.study.dao;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;

import java.util.List;

/**
 * @author Yuliya Bondarenko
 */
public interface UserDao {
    Long create(User user);

    void update(User user);

    void remove(User user);

    List<User> findAll();

    User findById(Long id) throws UserNotFoundException;

    User findByLogin(String login) throws UserNotFoundException;

    User findByEmail(String email) throws UserNotFoundException;
}