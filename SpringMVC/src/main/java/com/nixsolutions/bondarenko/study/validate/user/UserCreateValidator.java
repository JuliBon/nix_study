package com.nixsolutions.bondarenko.study.validate.user;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;

public class UserCreateValidator {
    private UserDao userDao;

    public UserCreateValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean isLoginUnique(User user) {
        try {
            userDao.findByLogin(user.getLogin());
            return false;
        } catch (UserNotFoundException e) {
            return true;
        }
    }

    public boolean isEmailUnique(User user) {
        try {
            userDao.findByEmail(user.getEmail());
            return false;
        } catch (UserNotFoundException e) {
            return true;
        }
    }
}
