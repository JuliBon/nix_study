package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.model.UserModel;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import java.util.List;
import java.util.Map;

public class Register extends ActionSupport implements ModelDriven<UserModel> {


    private UserModel userModel = new UserModel();

    @Override
    public String execute() throws Exception {
        UserModel userModel = getUserModel();
        return SUCCESS;
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    @Override
    public void validate() {
        UserModel userModel = getUserModel();
        Map<String, List<String>> fieldErrors = getFieldErrors();
    }

    public UserModel getUserModel() {
        return userModel;
    }

    @VisitorFieldValidator
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public UserModel getModel() {
        return userModel;
    }
}
