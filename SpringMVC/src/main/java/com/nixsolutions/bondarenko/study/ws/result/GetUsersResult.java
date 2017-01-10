package com.nixsolutions.bondarenko.study.ws.result;

import com.nixsolutions.bondarenko.study.entity.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "result")
public class GetUsersResult extends WebServiceResult {
    private List<User> userList;

    public GetUsersResult(){
    }

    public GetUsersResult(ResultCode resultCode, List<User> userList) {
        this.resultCode = resultCode;
        this.userList = userList;
    }
    public GetUsersResult(ResultCode resultCode, ErrorCode errorCode, String message) {
        super(resultCode, errorCode, message);

    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
