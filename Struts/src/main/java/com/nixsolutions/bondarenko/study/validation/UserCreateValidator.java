package com.nixsolutions.bondarenko.study.validation;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.model.UserModel;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Yulya Bondarenko
 */
public class UserCreateValidator extends UserValidator {
    private UserDao userDao;

    public UserCreateValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    public void validate(Object object, Map<String, List<String>> fieldErrors) {


        super.validate(object, fieldErrors);
        if (!fieldErrors.containsKey("userModel.user.login")) {
            validateLogin((UserModel) object, fieldErrors);
        }
    }

    /***
     * Check whether login is not unique
     */
    private void validateLogin(UserModel userModel, Map<String, List<String>> fieldErrors) {
        try {
            userDao.findByLogin(userModel.getUser().getLogin());
            fieldErrors.put("userModel.user.login", Arrays.asList(ERROR_NOT_UNIQUE_LOGIN));
        } catch (UserNotFoundException e) {
        }
    }

    /**
     * Check if there is already a user with this email
     */
    @Override
    protected void validateEmail(UserModel userModel, Map<String, List<String>> fieldErrors) {
        try {
            userDao.findByEmail(userModel.getUser().getEmail());
            fieldErrors.put("userModel.user.email", Arrays.asList(ERROR_NOT_UNIQUE_EMAIL));
        } catch (UserNotFoundException e) {
        }
    }
}
