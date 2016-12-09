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
        super.validate(object, errors);
        if (!errors.hasFieldErrors("login")) {
            validateLogin((UserModel) object, errors);
        }
    }

    /***
     * Check whether login is not unique
     */
    private void validateLogin(UserModel userModel, Errors errors) {
        User user = null;
        try {
            user = userDao.findByLogin(userModel.getLogin());
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
    @Override
    protected void validateEmail(UserModel userModel, Errors errors) {
        User userByEmail = null;
        try {
            userByEmail = userDao.findByEmail(userModel.getEmail());
        } catch (Exception e) {
        } finally {
            if (userByEmail != null) {
                errors.rejectValue("email", null, ERROR_NOT_UNIQUE_EMAIL);
            }
        }
    }
}
