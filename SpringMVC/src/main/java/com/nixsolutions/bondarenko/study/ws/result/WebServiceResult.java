package com.nixsolutions.bondarenko.study.ws.result;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "result")
public class WebServiceResult implements Serializable {
    protected ResultCode resultCode;
    protected ErrorCode errorCode;
    protected String message;

    public WebServiceResult() {
    }

    public WebServiceResult(ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public WebServiceResult(ResultCode resultCode, ErrorCode errorCode, String message) {
        this.resultCode = resultCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
