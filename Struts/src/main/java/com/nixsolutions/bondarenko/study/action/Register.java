package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.validation.UserCreateValidator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import java.util.List;
import java.util.Map;

public class Register extends ActionSupport implements ModelDriven<UserModel> {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

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
        Map<String, List<String>> fieldErrors = getFieldErrors();
        new UserCreateValidator(userDao).validate(userModel, fieldErrors);
        setFieldErrors(fieldErrors);
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
