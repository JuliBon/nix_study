package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String execute() throws Exception {
        if (isInvalid(getLogin())) return "input";

        if (isInvalid(getPassword())) return "input";

        User user = userDao.findByLogin(getLogin());
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
