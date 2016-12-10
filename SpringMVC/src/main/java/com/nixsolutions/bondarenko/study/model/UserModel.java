package com.nixsolutions.bondarenko.study.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;

public class UserModel {

    @Pattern(regexp = "^[a-zA-Z](([._-][a-zA-Z0-9])|[a-zA-Z0-9])*$",
            message = "3-15 characters, beginning with letter. Can include letters, numbers, dashes, and underscores")
    protected String login;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$",
            message = "at least one number and one uppercase and lowercase letters")
    protected String password;

    @NotNull
    @NotEmpty
    protected String passwordConfirm;

    @NotNull
    @NotEmpty
    @Email
    protected String email;

    @Pattern(regexp = "[A-Za-z]+",
            message = "one or more letters")
    protected String firstName;

    @Pattern(regexp = "[A-Za-z]+",
            message = "one or more letters")
    protected String lastName;

    @NotNull
    @NotEmpty
    protected String birthday;

    public UserModel() {
    }

    public UserModel(String login, String password, String passwordConfirm, String email, String firstName, String lastName, String birthday) {
        this.login = login;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
