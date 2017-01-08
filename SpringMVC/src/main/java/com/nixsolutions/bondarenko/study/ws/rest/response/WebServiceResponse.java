package com.nixsolutions.bondarenko.study.ws.rest.response;


public class WebServiceResponse {
    protected int code;
    protected String message;

    public WebServiceResponse() {
    }

    public WebServiceResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
