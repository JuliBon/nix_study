package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.ws.rest.response.ResponseCode;
import com.nixsolutions.bondarenko.study.ws.rest.response.WebServiceResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        WebServiceResponse response = new WebServiceResponse(ResponseCode.SERVER_ERROR, throwable.getMessage());
        return Response.status(status)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
