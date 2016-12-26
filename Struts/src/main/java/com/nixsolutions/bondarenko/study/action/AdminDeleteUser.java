package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

public class AdminDeleteUser extends ActionSupport {
    private UserDao userDao;
    private Long idUser;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Override
    public String execute() throws Exception {
        userDao.remove(userDao.findById(idUser));
        return SUCCESS;
    }
}
