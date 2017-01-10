package com.nixsolutions.bondarenko.study.ws.soap;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;
import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.service.UserService;
import com.nixsolutions.bondarenko.study.ws.result.*;
import org.apache.cxf.interceptor.OutFaultInterceptors;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;


@WebService(endpointInterface = "com.nixsolutions.bondarenko.study.ws.soap.SoapUserService",
        serviceName = "soapUserService")
@OutFaultInterceptors(interceptors = {"com.nixsolutions.bondarenko.study.ws.soap.interceptor.ExceptionInterceptor"})
public class SoapUserServiceImpl implements SoapUserService {
    @Autowired
    private UserService userService;

    private String invalidUserMessage = "Invalid user";
    private String serverErrorMessage = "Server error";

    @Override
    public GetUserResult getUser(Long id) {
        try {
            User user = userService.getUser(id);
            return new GetUserResult(ResultCode.OK, user);
        } catch (UserNotFoundException e) {
            return new GetUserResult(ResultCode.ERROR, ErrorCode.USER_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return new GetUserResult(ResultCode.ERROR, ErrorCode.SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public GetUsersResult getUsers() {
        try {
            return new GetUsersResult(ResultCode.OK, userService.getUsers());
        } catch (Exception e) {
            return new GetUsersResult(ResultCode.ERROR, ErrorCode.SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public WebServiceResult deleteUser(Long id) {
        try {
            userService.deleteUser(id);
            return new WebServiceResult(ResultCode.OK, "User have been deleted");
        } catch (UserNotFoundException e) {
            return new WebServiceResult(ResultCode.ERROR, ErrorCode.USER_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return new WebServiceResult(ResultCode.ERROR, ErrorCode.SERVER_ERROR, serverErrorMessage);
        }
    }

    @Override
    public UserCreateResult createUser(User user) {
        try {
            return new UserCreateResult(ResultCode.OK, userService.createUser(user), "User have been created");
        } catch (javax.validation.ValidationException e) {
            return new UserCreateResult(ResultCode.ERROR, ErrorCode.INVALID_USER, invalidUserMessage);
        } catch (NotUniqueEmailException e) {
            return new UserCreateResult(ResultCode.ERROR, ErrorCode.NOT_UNIQUE_EMAIL, e.getMessage());
        } catch (NotUniqueLoginException e) {
            return new UserCreateResult(ResultCode.ERROR, ErrorCode.NOT_UNIQUE_LOGIN, e.getMessage());
        } catch (Exception e) {
            return new UserCreateResult(ResultCode.ERROR, ErrorCode.SERVER_ERROR, serverErrorMessage);
        }
    }

    @Override
    public WebServiceResult updateUser(User user) {
        try {
            userService.updateUser(user);
            return new WebServiceResult(ResultCode.OK, "User have been updated");
        } catch (javax.validation.ValidationException e) {
            return new UserCreateResult(ResultCode.ERROR, ErrorCode.INVALID_USER, invalidUserMessage);
        } catch (NotUniqueEmailException e) {
            return new UserCreateResult(ResultCode.ERROR, ErrorCode.NOT_UNIQUE_EMAIL, e.getMessage());
        } catch (Exception e) {
            return new UserCreateResult(ResultCode.ERROR, ErrorCode.SERVER_ERROR, serverErrorMessage);
        }
    }
}
