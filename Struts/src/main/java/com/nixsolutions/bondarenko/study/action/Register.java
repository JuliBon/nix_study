package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.model.UserModel;
import com.opensymphony.xwork2.ActionSupport;

import javax.validation.Valid;
import java.util.Collection;

public class Register extends ActionSupport {

    private static final long serialVersionUID = 1L;

    @Valid
    private UserModel userModel;

    public String execute() throws Exception {
        UserModel userModel = getUserModel();

        Collection<String> actionErrors = getActionErrors();
        return  INPUT;
    }

    @Override
    public void validate() {
        UserModel userModel = getUserModel();
        Collection<String> actionErrors = getActionErrors();
        super.validate();
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
