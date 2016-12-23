package com.nixsolutions.bondarenko.study.validation;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.model.UserModel;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class UserUpdateValidator extends UserValidator {
    private UserDao userDao;

    public UserUpdateValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Check if user with this email was found and he is not this user
     */
    protected void validateEmail(UserModel userModel, Map<String, List<String>> fieldErrors) {
        try {
            User userByEmail = userDao.findByEmail(userModel.getUser().getEmail());
            if (userByEmail != null) {
                if (!userByEmail.getLogin().equals(userModel.getUser().getLogin())) {
                    fieldErrors.put("userModel.user.email", Arrays.asList(ERROR_NOT_UNIQUE_EMAIL));
                }
            }
        }  catch (UserNotFoundException e) {
        }
    }
}

