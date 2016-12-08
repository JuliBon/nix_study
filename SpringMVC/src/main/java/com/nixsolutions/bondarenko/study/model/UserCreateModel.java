package com.nixsolutions.bondarenko.study.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserCreateModel extends UserModel {
    @NotNull
    @NotEmpty
    protected String roleName;

    public UserCreateModel(String login, String password, String passwordConfirm, String email, String firstName, String lastName, String birthday, String roleName) {
        super(login, password, passwordConfirm, email, firstName, lastName, birthday);
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
