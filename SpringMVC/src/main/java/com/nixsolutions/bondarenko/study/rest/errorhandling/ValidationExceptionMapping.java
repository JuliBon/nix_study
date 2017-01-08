package com.nixsolutions.bondarenko.study.rest.errorhandling;

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionMapping implements ExceptionMapper<javax.validation.ValidationException> {

    @Override
    public Response toResponse(ValidationException e) {
        ErrorMessage errorMessage = new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Invalid user data");
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
