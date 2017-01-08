package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.ws.rest.response.ResponseCode;
import com.nixsolutions.bondarenko.study.ws.rest.response.WebServiceResponse;

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionMapping implements ExceptionMapper<javax.validation.ValidationException> {
    @Override
    public Response toResponse(ValidationException e) {
        Response.Status status = Response.Status.BAD_REQUEST;
        //TODO say about invalid field
        WebServiceResponse response = new WebServiceResponse(ResponseCode.INVALID_USER, "Invalid user data");
        return Response.status(status)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
