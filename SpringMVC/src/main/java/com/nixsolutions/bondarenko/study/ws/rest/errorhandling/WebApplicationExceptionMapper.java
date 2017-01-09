package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

import com.nixsolutions.bondarenko.study.ws.result.ErrorCode;
import com.nixsolutions.bondarenko.study.ws.result.ResultCode;
import com.nixsolutions.bondarenko.study.ws.result.WebServiceResult;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException e) {
        int status = e.getResponse().getStatus();
        WebServiceResult result = new WebServiceResult(ResultCode.ERROR, ErrorCode.WEB_APPLICATION_ERROR, e.getMessage());
        return Response.status(status)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
