package com.nixsolutions.bondarenko.study.ws.soap;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.service.UserService;
import com.nixsolutions.bondarenko.study.ws.response.ResponseCode;
import com.nixsolutions.bondarenko.study.ws.response.UserCreateResponse;
import com.nixsolutions.bondarenko.study.ws.response.WebServiceResponse;
import com.nixsolutions.bondarenko.study.ws.soap.response.SoapGetUserResponse;
import com.nixsolutions.bondarenko.study.ws.soap.response.SoapGetUsersResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;

@WebService(endpointInterface = "com.nixsolutions.bondarenko.study.ws.soap.SoapUserService",
        serviceName = "soapUserService")
public class SoapUserServiceImpl implements SoapUserService {
    @Autowired
    private UserService userService;

    @Override
    public SoapGetUserResponse getUser(Long id) {
        return new SoapGetUserResponse(ResponseCode.OK, userService.getUser(id));
    }

    @Override
    public SoapGetUsersResponse getUsers() {
        return new SoapGetUsersResponse(ResponseCode.OK, userService.getUsers());
    }

    @Override
    public WebServiceResponse deleteUser(Long id) {
        userService.deleteUser(id);
        return new WebServiceResponse(ResponseCode.OK, "User have been deleted");
    }

    @Override
    public UserCreateResponse createUser(User user) {
        return new UserCreateResponse(ResponseCode.OK, userService.createUser(user), "User have been created");
    }

    @Override
    public WebServiceResponse updateUser(User user) {
        userService.updateUser(user);
        return new WebServiceResponse(ResponseCode.OK, "User have been updated");
    }
}
