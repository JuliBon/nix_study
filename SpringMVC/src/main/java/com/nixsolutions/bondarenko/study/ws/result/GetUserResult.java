package com.nixsolutions.bondarenko.study.ws.result;

import com.nixsolutions.bondarenko.study.entity.User;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
public class GetUserResult extends WebServiceResult {
    private User user;

    public GetUserResult(){
    }

    public GetUserResult(ResultCode resultCode, ErrorCode errorCode, String message) {
        super(resultCode, errorCode, message);
    }

    public GetUserResult(ResultCode resultCode, User user) {
        this.resultCode = resultCode;
        this.user = user;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
