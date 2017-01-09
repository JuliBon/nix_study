package com.nixsolutions.bondarenko.study.service;

import com.nixsolutions.bondarenko.study.entity.User;

import javax.jws.WebParam;
import java.util.List;

public interface UserService {

    User getUser(@WebParam(name = "id") Long id);

    List<User> getUsers();

    void deleteUser(@WebParam(name = "id") Long id);

    Long createUser(@WebParam(name = "user") User user);

    void updateUser(@WebParam(name = "user") User user);

    boolean verifyUserExistence(Long id);
}
