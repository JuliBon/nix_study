package com.nixsolutions.bondarenko.study.validate;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.entity.User;
import org.springframework.validation.Errors;


public class UserUpdateValidator extends UserValidator {
    private UserDao userDao;

    public UserUpdateValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Check if user with this email was found and he is not this user
     */
    @Override
    protected void validateEmail(UserModel userModel, Errors errors) {
        try {
            User userByEmail = userDao.findByEmail(userModel.getUser().getEmail());
            if (userByEmail != null) {
                if (!userByEmail.getLogin().equals(userModel.getUser().getLogin())) {
                    errors.rejectValue("user.email", null, ERROR_NOT_UNIQUE_EMAIL);
                }
            }
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

