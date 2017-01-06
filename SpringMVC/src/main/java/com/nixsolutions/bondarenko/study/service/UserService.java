package com.nixsolutions.bondarenko.study.service;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;
import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface UserService {

    @WebMethod
    User getUser(@WebParam(name = "text") String login);

    @WebMethod
    List<User> getUsers();

    @WebMethod
    void deleteUser(@WebParam(name = "id") Long id);

    @WebMethod
    void createUser(@WebParam(name = "user") User user) throws NotUniqueLoginException, NotUniqueEmailException;

    @WebMethod
    void updateUser(@WebParam(name = "user") User user) throws NotUniqueEmailException;

    boolean verifyUserExistence(Long id);
}
