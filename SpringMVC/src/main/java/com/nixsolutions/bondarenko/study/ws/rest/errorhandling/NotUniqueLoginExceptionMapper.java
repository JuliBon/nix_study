package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;
import com.nixsolutions.bondarenko.study.ws.rest.response.ResponseCode;
import com.nixsolutions.bondarenko.study.ws.rest.response.WebServiceResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotUniqueLoginExceptionMapper implements ExceptionMapper<NotUniqueLoginException> {
    @Override
    public Response toResponse(NotUniqueLoginException e) {
        Response.Status status = Response.Status.BAD_REQUEST;
        WebServiceResponse response = new WebServiceResponse(ResponseCode.NOT_UNIQUE_LOGIN, e.getMessage());
        return Response.status(status)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
