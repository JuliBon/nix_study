package com.nixsolutions.bondarenko.study.rest.errorhandling;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        ErrorMessage errorMessage = new ErrorMessage();
        if (throwable instanceof WebApplicationException) {
            errorMessage.setStatus(((WebApplicationException) throwable).getResponse().getStatus());
            errorMessage.setMessage(throwable.getMessage());
        } else {
            errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            errorMessage.setMessage(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
