package com.nixsolutions.bondarenko.study.dao;

import com.nixsolutions.bondarenko.study.entity.User;

import java.util.List;

public interface UserDao {
    Long create(User user);

    void update(User user);

    void remove(User user);

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);
}