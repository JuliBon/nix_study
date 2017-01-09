package com.nixsolutions.bondarenko.study.ws.result;

import com.nixsolutions.bondarenko.study.entity.User;

import java.util.List;

public class GetUsersResult extends WebServiceResult {
    private List<User> userList;

    public GetUsersResult(){
    }

    public GetUsersResult(ResultCode resultCode, List<User> userList) {
        this.resultCode = resultCode;
        this.userList = userList;
    }
    public GetUsersResult(ResultCode resultCode, ErrorCode errorCode, String message) {
        this.resultCode = resultCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
