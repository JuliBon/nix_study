package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

public class Admin extends ActionSupport {

    private UserDao userDao;
    private List<User> userList;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String execute() throws Exception {
        userList = userDao.findAll();
        return SUCCESS;
    }
}
