package com.nixsolutions.bondarenko.study.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserUpdateModel extends UserModel {

    protected String id = null;

    @NotNull
    @NotEmpty
    protected String roleName;

    public UserUpdateModel(){

    }

    public UserUpdateModel(String id, String login, String password, String passwordConfirm, String email, String firstName, String lastName, String birthday, String roleName) {
        super(login, password, passwordConfirm, email, firstName, lastName, birthday);
        this.id = id;
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
