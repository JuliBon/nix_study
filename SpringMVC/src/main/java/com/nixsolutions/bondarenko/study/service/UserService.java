package com.nixsolutions.bondarenko.study.service;

import com.nixsolutions.bondarenko.study.entity.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface UserService {

    @WebMethod
    User getUser(@WebParam(name = "text") Long id);

    @WebMethod
    List<User> getUsers();

    @WebMethod
    void deleteUser(@WebParam(name = "id") Long id);

    @WebMethod
    void createUser(@WebParam(name = "user") User user);

    @WebMethod
    void updateUser(@WebParam(name = "user") User user);

    boolean verifyUserExistence(Long id);
}
