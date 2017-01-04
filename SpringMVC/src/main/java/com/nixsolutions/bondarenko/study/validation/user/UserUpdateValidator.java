package com.nixsolutions.bondarenko.study.validation.user;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;

public class UserUpdateValidator {
    private UserDao userDao;

    public UserUpdateValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Check if user with this email was found and he is not this user
     */
    public boolean isEmailUnique(User user) {
        try {
            User userByEmail = userDao.findByEmail(user.getEmail());
            if (userByEmail != null) {
                if (!userByEmail.getLogin().equals(user.getLogin())) {
                    return false;
                }
            }
        } catch (UserNotFoundException e) {
        }
        return true;
    }
}
