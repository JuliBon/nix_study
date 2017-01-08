package com.nixsolutions.bondarenko.study.rest.errorhandling;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException e) {
        int status = e.getResponse().getStatus();
        ErrorMessage message = new ErrorMessage(status, e.getMessage());
        return Response.status(status)
                .entity(message)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
