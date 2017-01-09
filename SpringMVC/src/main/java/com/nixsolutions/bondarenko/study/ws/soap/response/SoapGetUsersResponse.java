package com.nixsolutions.bondarenko.study.ws.soap.response;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.ws.response.WebServiceResponse;

import java.util.List;

public class SoapGetUsersResponse extends WebServiceResponse {
    private List<User> userList;

    public SoapGetUsersResponse(int code, List<User> userList) {
        this.code = code;
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
