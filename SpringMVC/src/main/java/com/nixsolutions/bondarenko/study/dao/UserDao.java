package com.nixsolutions.bondarenko.study.dao;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;

import java.util.List;

/**
 * @author Yuliya Bondarenko
 */
public interface UserDao {
    void create(User user) throws Exception;

    void update(User user) throws Exception;

    void remove(User user) throws Exception;

    List<User> findAll() throws Exception;

    User findById(Long id) throws UserNotFoundException;

    User findByLogin(String login) throws UserNotFoundException;

    User findByEmail(String email) throws UserNotFoundException;
}