package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.List;

public class NotUniqueLoginExceptionMapper implements ExceptionMapper<NotUniqueLoginException> {
    @Override
    public Response toResponse(NotUniqueLoginException e) {
        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError("login", "not unique login"));
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(validationErrors)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
