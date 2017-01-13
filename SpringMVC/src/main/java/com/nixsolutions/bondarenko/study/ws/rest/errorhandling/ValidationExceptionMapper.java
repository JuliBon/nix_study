package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionMapper implements ExceptionMapper<javax.validation.ValidationException> {
    @Override
    public Response toResponse(ValidationException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Invalid user")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
