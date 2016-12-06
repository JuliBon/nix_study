package com.nixsolutions.bondarenko.study.model;

public class UserRegisterModel extends UserModel {
    protected String oldPassword;

    public UserRegisterModel(){ }

    public UserRegisterModel(String id, String login, String oldPassword, String password, String passwordConfirm, String email, String firstName, String lastName, String birthday, String roleName) {
        super(id, login, password, passwordConfirm, email, firstName, lastName, birthday, roleName);
    }
}
