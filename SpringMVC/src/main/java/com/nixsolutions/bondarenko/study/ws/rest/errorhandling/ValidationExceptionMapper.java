package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.ws.result.ErrorCode;
import com.nixsolutions.bondarenko.study.ws.result.ResultCode;
import com.nixsolutions.bondarenko.study.ws.result.WebServiceResult;

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionMapper implements ExceptionMapper<javax.validation.ValidationException> {
    @Override
    public Response toResponse(ValidationException e) {
        Response.Status status = Response.Status.BAD_REQUEST;
        //TODO say about invalid field
        WebServiceResult result = new WebServiceResult(ResultCode.ERROR, ErrorCode.INVALID_USER, "Invalid user data");
        return Response.status(status)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
