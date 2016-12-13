package com.nixsolutions.bondarenko.study.model;

import com.nixsolutions.bondarenko.study.entity.User;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserModel {
    private User user;

    @NotNull
    @NotEmpty
    private String passwordConfirm;


    @NotNull
    @NotEmpty
    private String birthdayStr;

    @NotNull
    @NotEmpty
    private String roleName;

    public UserModel() {
    }

    public UserModel(User user){
        this.user = user;
        this.birthdayStr = user.getBirthday().toString();
        this.roleName = user.getRole().getName();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

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
