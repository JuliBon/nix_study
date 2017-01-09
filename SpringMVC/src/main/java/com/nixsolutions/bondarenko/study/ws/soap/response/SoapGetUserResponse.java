package com.nixsolutions.bondarenko.study.ws.soap.response;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.ws.response.WebServiceResponse;

public class SoapGetUserResponse extends WebServiceResponse {
    private User user;

    public SoapGetUserResponse(int code, User user){
        this.code = code;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
