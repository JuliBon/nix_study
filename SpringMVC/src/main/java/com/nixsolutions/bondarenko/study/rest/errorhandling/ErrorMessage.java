package com.nixsolutions.bondarenko.study.rest.errorhandling;


import org.springframework.beans.BeanUtils;

import javax.ws.rs.core.Response;

public class ErrorMessage {
    private int status;
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(Response.Status status) {
        this.status = status.getStatusCode();
        this.message = status.getReasonPhrase();
    }

    public ErrorMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(Exception e) {
        BeanUtils.copyProperties(this, e);
    }
}
