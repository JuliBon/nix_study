package com.nixsolutions.bondarenko.study.rest.service;

import com.nixsolutions.bondarenko.study.entity.User;

import java.util.List;

public interface UserService {

    User getUser(Long id);

    User getUser(String login);

    List<User> getUsers();

    void deleteUser(Long id);

    Long createUser(User user);

    void updateUser(User user);
}
