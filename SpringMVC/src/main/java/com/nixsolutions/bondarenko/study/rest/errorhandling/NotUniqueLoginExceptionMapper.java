package com.nixsolutions.bondarenko.study.rest.errorhandling;

import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotUniqueLoginExceptionMapper implements ExceptionMapper<NotUniqueLoginException> {
    @Override
    public Response toResponse(NotUniqueLoginException e) {
        Response.Status status = Response.Status.BAD_REQUEST;
        return Response.status(status)
                .entity(new ErrorMessage(status))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
