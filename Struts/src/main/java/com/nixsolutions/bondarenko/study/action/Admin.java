package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class Admin extends ActionSupport {
    private String userName;
    private UserDao userDao;
    private List<User> userList;

    public String getUserName() {
        return userName;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public String execute() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            userName = authentication.getName();
        }

        userList = userDao.findAll();
        return SUCCESS;
    }
}
