package com.nixsolutions.bondarenko.study.model;

import com.nixsolutions.bondarenko.study.entity.User;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import java.io.Serializable;


public class UserModel implements Serializable {
    private User user;

    private String passwordConfirm;

    private String birthdayStr;

    private String roleName;

    public UserModel() {
        user = new User();
    }

    public UserModel(User user) {
        this.user = user;
        if (user.getBirthday() != null) {
            this.birthdayStr = user.getBirthday().toString();
        }
        if (user.getRole() != null) {
            this.roleName = user.getRole().getName();
        }
    }


    public User getUser() {
        return user;
    }

    @VisitorFieldValidator
    public void setUser(User user) {
        this.user = user;
    }


    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    @RequiredStringValidator(message = "confirm password")
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }


    public String getBirthdayStr() {
        return birthdayStr;
    }

    @RequiredStringValidator(message = "birthday required")
    @RegexFieldValidator(regex = "^\\d{4}-(0\\d|10|11|12)-([012]\\d|30|31)$", message = "bad date format")
    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}