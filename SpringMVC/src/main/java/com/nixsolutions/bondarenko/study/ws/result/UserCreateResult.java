package com.nixsolutions.bondarenko.study.ws.result;

public class UserCreateResult extends WebServiceResult {
    private Long id;

    public UserCreateResult() {
    }

    public UserCreateResult(ResultCode resultCode, Long id, String message) {
        this.resultCode = resultCode;
        this.id = id;
        this.message = message;
    }

    public UserCreateResult(ResultCode resultCode, ErrorCode errorCode, Long id, String message) {
        this(resultCode, id, message);
        this.errorCode = errorCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
