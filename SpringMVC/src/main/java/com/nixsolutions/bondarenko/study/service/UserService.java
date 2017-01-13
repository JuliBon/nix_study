package com.nixsolutions.bondarenko.study.service;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;
import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;

import javax.jws.WebParam;
import javax.validation.ValidationException;
import java.util.List;

public interface UserService {

    User getUser(@WebParam(name = "id") Long id) throws UserNotFoundException;

    List<User> getUsers();

    void deleteUser(@WebParam(name = "id") Long id) throws UserNotFoundException;

    Long createUser(@WebParam(name = "user") User user) throws NotUniqueLoginException, NotUniqueEmailException, ValidationException;

    void updateUser(@WebParam(name = "user") User user) throws NotUniqueEmailException, ValidationException;

    boolean verifyUserExistence(Long id);
}
