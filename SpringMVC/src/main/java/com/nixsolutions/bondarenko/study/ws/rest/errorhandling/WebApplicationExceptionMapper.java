package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.ws.response.WebServiceResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException e) {
        int status = e.getResponse().getStatus();
        WebServiceResponse response = new WebServiceResponse(status, e.getMessage());
        return Response.status(status)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
