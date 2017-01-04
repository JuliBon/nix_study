package com.nixsolutions.bondarenko.study.rest.service;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;
import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;

import java.util.List;

public interface UserService {

    User getUser(Long id);

    User getUser(String login);

    List<User> getUsers();

    void deleteUser(Long id);

    void createUser(User user) throws NotUniqueLoginException, NotUniqueEmailException;

    void updateUser(User user) throws NotUniqueEmailException;

    boolean verifyUserExistence(Long id);
}
