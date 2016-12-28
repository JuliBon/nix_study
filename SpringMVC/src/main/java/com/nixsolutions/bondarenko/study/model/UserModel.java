package com.nixsolutions.bondarenko.study.model;

import com.nixsolutions.bondarenko.study.entity.User;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserModel {
    @Valid
    private User user;

    @NotNull
    @NotEmpty
    private String passwordConfirm;

    @Pattern(regexp = "^\\d{4}-(0\\d|10|11|12)-([012]\\d|30|31)$", message = "bad date format")
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
