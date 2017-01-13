package com.nixsolutions.bondarenko.study.ws.soap;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;
import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.ValidationException;
import java.util.List;

@WebService
public interface SoapUserService {

    @WebMethod
    User getUser(@WebParam(name = "id") Long id) throws UserNotFoundException;

    @WebMethod
    List<User> getUsers();

    @WebMethod
    void deleteUser(@WebParam(name = "id") Long id) throws UserNotFoundException;

    @WebMethod
    Long createUser(@WebParam(name = "user") User user) throws NotUniqueLoginException, NotUniqueEmailException, ValidationException;

    @WebMethod
    void updateUser(@WebParam(name = "user") User user) throws NotUniqueEmailException, ValidationException;
}