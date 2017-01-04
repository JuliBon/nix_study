package com.nixsolutions.bondarenko.study.validate.user;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;

public class UserUpdateValidator {
    private UserDao userDao;

    public UserUpdateValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean isEmailValid(User user) {
        try {
            User userByEmail = userDao.findByEmail(user.getEmail());
            if (!userByEmail.getLogin().equals(user.getLogin())) {
                return false;
            }
        } catch (UserNotFoundException e) {
        }
        return true;
    }
}
