package com.nixsolutions.bondarenko.study.validate;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

public class UserUpdateValidator extends UserValidator {
    private UserDao userDao;

    public UserUpdateValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserModel userModel = (UserModel) object;
        if (!errors.hasFieldErrors("login")) {
            validateEmail(userModel.getEmail(), userModel.getLogin(), errors);
        }
        if(!errors.hasFieldErrors("password")) {
            validatePassword(userModel.getPassword(), userModel.getPasswordConfirm(), errors);
        }
        if(!errors.hasFieldErrors("birthday")){
            validateBirthday(userModel.getBirthday(), errors);
        }
    }

    /**
     * Check if user with this email was found and he is not this user
     */
    private void validateEmail(String email, String login, Errors errors) {
        User userByEmail = null;
        try {
            userByEmail = userDao.findByEmail(email);

        } catch (Exception e) {
        } finally {
            if (userByEmail != null) {
                if (!userByEmail.getLogin().equals(login)) {
                    errors.rejectValue("email", null, ERROR_NOT_UNIQUE_EMAIL);
                }
            }
        }
    }
}

