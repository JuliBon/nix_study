package com.nixsolutions.bondarenko.study.validate;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.entity.User;
import org.springframework.validation.Errors;

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
        if (!errors.hasFieldErrors("user.login")) {
            validateLogin((UserModel) object, errors);
        }
    }

    /***
     * Check whether login is not unique
     */
    private void validateLogin(UserModel userModel, Errors errors) {
        try {
            userDao.findByLogin(userModel.getUser().getLogin());
            errors.rejectValue("user.login", null, ERROR_NOT_UNIQUE_LOGIN);
        } catch (UserNotFoundException e) { }
    }

    /**
     * Check if there is already a user with this email
     */
    @Override
    protected void validateEmail(UserModel userModel, Errors errors) {
        try {
            userDao.findByEmail(userModel.getUser().getEmail());
            errors.rejectValue("user.email", null, ERROR_NOT_UNIQUE_EMAIL);
        } catch (UserNotFoundException e) {
        }
    }
}
