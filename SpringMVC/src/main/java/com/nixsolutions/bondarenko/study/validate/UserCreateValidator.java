package com.nixsolutions.bondarenko.study.validate;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.entity.User;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yulya Bondarenko
 */
public class UserCreateValidator extends UserValidator {
    private UserDao userDao;

    public UserCreateValidator(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public void validate(Object object, Errors errors) {
        UserModel userModel = (UserModel) object;
        if (!errors.hasFieldErrors("login")) {
            validateLogin(userModel.getLogin(), errors);
        }
        if (!errors.hasFieldErrors("email")) {
            validateEmail(userModel.getEmail(), errors);
        }
        if (!errors.hasFieldErrors("password")) {
            validatePassword(userModel.getPassword(), userModel.getPasswordConfirm(), errors);
        }
        if(!errors.hasFieldErrors("birthday")){
            validateBirthday(userModel.getBirthday(), errors);
        }
    }

    /***
     * Check whether login is not unique
     */
    private void validateLogin(String login, Errors errors) {
        User user = null;
        try {
            user = userDao.findByLogin(login);
        } catch (Exception e) {
        } finally {
            if (user != null) {
                errors.rejectValue("login", null, ERROR_NOT_UNIQUE_LOGIN);
            }
        }
    }

    /**
     * Check if there is already a user with this email
     */
    private void validateEmail(String email, Errors errors) {
        User userByEmail = null;
        try {
            userByEmail = userDao.findByEmail(email);
        } catch (Exception e) {
        } finally {
            if (userByEmail != null) {
                errors.rejectValue("email", null, ERROR_NOT_UNIQUE_EMAIL);
            }
        }
    }
}
