package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.ws.result.ErrorCode;
import com.nixsolutions.bondarenko.study.ws.result.ResultCode;
import com.nixsolutions.bondarenko.study.ws.result.WebServiceResult;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {
    @Override
    public Response toResponse(UserNotFoundException e) {
        Response.Status status = Response.Status.NOT_FOUND;
        WebServiceResult result = new WebServiceResult(ResultCode.ERROR, ErrorCode.USER_NOT_FOUND, e.getMessage());
        return Response.status(status)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
