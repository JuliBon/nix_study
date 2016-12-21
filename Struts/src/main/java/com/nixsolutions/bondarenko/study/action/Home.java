package com.nixsolutions.bondarenko.study.action;

public class Home {
    private String login;

    public String execute() throws Exception {
        return "success";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
