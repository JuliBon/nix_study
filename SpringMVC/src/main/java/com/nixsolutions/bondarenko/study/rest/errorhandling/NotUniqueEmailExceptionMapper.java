package com.nixsolutions.bondarenko.study.rest.errorhandling;

import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotUniqueEmailExceptionMapper implements ExceptionMapper<NotUniqueEmailException> {
    @Override
    public Response toResponse(NotUniqueEmailException e) {
        Response.Status status = Response.Status.BAD_REQUEST;
        return Response.status(status)
                .entity(new ErrorMessage(Response.Status.BAD_REQUEST))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
