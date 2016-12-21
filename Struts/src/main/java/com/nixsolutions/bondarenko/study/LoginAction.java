package com.nixsolutions.bondarenko.study;

public class LoginAction {
    public String execute() throws Exception {

        if (isInvalid(getLogin())) return "input";

        if (isInvalid(getPassword())) return "input";

        return "success";
    }

    private boolean isInvalid(String value) {

        return (value == null || value.length() == 0);
    }

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
