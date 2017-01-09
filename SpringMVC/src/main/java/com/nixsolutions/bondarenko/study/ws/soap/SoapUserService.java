package com.nixsolutions.bondarenko.study.ws.soap;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.ws.result.UserCreateResult;
import com.nixsolutions.bondarenko.study.ws.result.WebServiceResult;
import com.nixsolutions.bondarenko.study.ws.result.GetUserResult;
import com.nixsolutions.bondarenko.study.ws.result.GetUsersResult;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SoapUserService {

    @WebMethod
    GetUserResult getUser(@WebParam(name = "text") Long id);

    @WebMethod
    GetUsersResult getUsers();

    @WebMethod
    WebServiceResult deleteUser(@WebParam(name = "id") Long id);

    @WebMethod
    UserCreateResult createUser(@WebParam(name = "user") User user);

    @WebMethod
    WebServiceResult updateUser(@WebParam(name = "user") User user);
}