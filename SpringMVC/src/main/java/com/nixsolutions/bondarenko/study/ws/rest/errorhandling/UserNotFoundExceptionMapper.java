package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.ws.rest.response.ResponseCode;
import com.nixsolutions.bondarenko.study.ws.rest.response.WebServiceResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {
    @Override
    public Response toResponse(UserNotFoundException e) {
        Response.Status status = Response.Status.NOT_FOUND;
        WebServiceResponse response = new WebServiceResponse(ResponseCode.USER_NOT_FOUND, e.getMessage());
        return Response.status(status)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
