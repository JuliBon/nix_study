package com.nixsolutions.bondarenko.study.rest.errorhandling;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        return Response.status(status)
                .entity(new ErrorMessage(status))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
