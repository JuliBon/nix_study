package com.nixsolutions.bondarenko.study.validation.model;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.validation.user.UserCreateValidator;
import org.springframework.validation.Errors;

/**
 * @author Yulya Bondarenko
 */
public class UserModelCreateValidator extends UserModelValidator {
    private UserCreateValidator userCreateValidator;

    public UserModelCreateValidator(UserDao userDao) {
        userCreateValidator = new UserCreateValidator(userDao);

    }

    @Override
    public void validate(Object object, Errors errors) {
        super.validate(object, errors);
        if (!errors.hasFieldErrors("user.login")) {
            validateLogin((UserModel) object, errors);
        }
    }

    /***
     * Check whether login is not unique
     */
    public void validateLogin(UserModel userModel, Errors errors) {
        if (!userCreateValidator.isLoginUnique(userModel.getUser())) {
            errors.rejectValue("user.login", null, ERROR_NOT_UNIQUE_LOGIN);
        }
    }

    /**
     * Check if there is already a user with this email
     */
    @Override
    public void validateEmail(UserModel userModel, Errors errors) {
        if (!userCreateValidator.isEmailUnique(userModel.getUser())) {
            errors.rejectValue("user.email", null, ERROR_NOT_UNIQUE_EMAIL);
        }
    }
}
