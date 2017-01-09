package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;
import com.nixsolutions.bondarenko.study.ws.result.ErrorCode;
import com.nixsolutions.bondarenko.study.ws.result.ResultCode;
import com.nixsolutions.bondarenko.study.ws.result.WebServiceResult;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotUniqueLoginExceptionMapper implements ExceptionMapper<NotUniqueLoginException> {
    @Override
    public Response toResponse(NotUniqueLoginException e) {
        Response.Status status = Response.Status.BAD_REQUEST;
        WebServiceResult result = new WebServiceResult(ResultCode.ERROR, ErrorCode.NOT_UNIQUE_LOGIN, e.getMessage());
        return Response.status(status)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
