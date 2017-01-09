package com.nixsolutions.bondarenko.study.ws.response;

public class UserCreateResponse extends WebServiceResponse {
    private Long id;

    public UserCreateResponse() {
    }

    public UserCreateResponse(int code, Long id, String message) {
        this.code = code;
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
