package com.nixsolutions.bondarenko.study.ws.soap;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.service.UserService;
import com.nixsolutions.bondarenko.study.ws.result.ResultCode;
import com.nixsolutions.bondarenko.study.ws.result.UserCreateResult;
import com.nixsolutions.bondarenko.study.ws.result.WebServiceResult;
import com.nixsolutions.bondarenko.study.ws.result.GetUserResult;
import com.nixsolutions.bondarenko.study.ws.result.GetUsersResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;

@WebService(endpointInterface = "com.nixsolutions.bondarenko.study.ws.soap.SoapUserService",
        serviceName = "soapUserService")
public class SoapUserServiceImpl implements SoapUserService {
    @Autowired
    private UserService userService;

    @Override
    public GetUserResult getUser(Long id) {
        return new GetUserResult(ResultCode.OK, userService.getUser(id));
    }

    @Override
    public GetUsersResult getUsers() {
        return new GetUsersResult(ResultCode.OK, userService.getUsers());
    }

    @Override
    public WebServiceResult deleteUser(Long id) {
        userService.deleteUser(id);
        return new WebServiceResult(ResultCode.OK, "User have been deleted");
    }

    @Override
    public UserCreateResult createUser(User user) {
        return new UserCreateResult(ResultCode.OK, userService.createUser(user), "User have been created");
    }

    @Override
    public WebServiceResult updateUser(User user) {
        userService.updateUser(user);
        return new WebServiceResult(ResultCode.OK, "User have been updated");
    }
}
