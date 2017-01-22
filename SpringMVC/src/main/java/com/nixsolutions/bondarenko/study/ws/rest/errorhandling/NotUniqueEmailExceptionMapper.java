package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.List;

public class NotUniqueEmailExceptionMapper implements ExceptionMapper<NotUniqueEmailException> {
    @Override
    public Response toResponse(NotUniqueEmailException e) {
        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError("email", "not unique email"));
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(validationErrors)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
