package com.nixsolutions.bondarenko.study.ws.soap;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.ws.response.UserCreateResponse;
import com.nixsolutions.bondarenko.study.ws.response.WebServiceResponse;
import com.nixsolutions.bondarenko.study.ws.soap.response.SoapGetUserResponse;
import com.nixsolutions.bondarenko.study.ws.soap.response.SoapGetUsersResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SoapUserService {

    @WebMethod
    SoapGetUserResponse getUser(@WebParam(name = "text") Long id);

    @WebMethod
    SoapGetUsersResponse getUsers();

    @WebMethod
    WebServiceResponse deleteUser(@WebParam(name = "id") Long id);

    @WebMethod
    UserCreateResponse createUser(@WebParam(name = "user") User user);

    @WebMethod
    WebServiceResponse updateUser(@WebParam(name = "user") User user);
}