package com.nixsolutions.bondarenko.study.action;

import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        if (error != null) {
            this.error = "Invalid user";
        }
    }
}
