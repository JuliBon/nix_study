package com.nixsolutions.bondarenko.study.rest.errorhandling;

import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotUniqueLoginExceptionMapper implements ExceptionMapper<NotUniqueLoginException> {
    @Override
    public Response toResponse(NotUniqueLoginException e) {

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(Response.Status.BAD_REQUEST))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
