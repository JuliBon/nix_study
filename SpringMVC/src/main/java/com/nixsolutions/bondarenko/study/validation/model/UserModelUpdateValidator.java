package com.nixsolutions.bondarenko.study.validation.model;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.validation.user.UserUpdateValidator;
import org.springframework.validation.Errors;


public class UserModelUpdateValidator extends UserModelValidator {
    private UserDao userDao;

    public UserModelUpdateValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void validateEmail(UserModel userModel, Errors errors) {
        if (!new UserUpdateValidator(userDao).isEmailUnique(userModel.getUser())) {
            errors.rejectValue("user.email", null, ERROR_NOT_UNIQUE_EMAIL);
        }
    }
}

