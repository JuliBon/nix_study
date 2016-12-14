package com.nixsolutions.bondarenko.study.validate;

import com.nixsolutions.bondarenko.study.dao.UserDao;
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

    /**
     * @throws RuntimeException if some error occurred while searching user in UserDao
     */
    @Override
    public void validate(Object object, Errors errors) throws RuntimeException{
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
            User user = userDao.findByLogin(userModel.getUser().getLogin());
            if (user != null) {
                errors.rejectValue("user.login", null, ERROR_NOT_UNIQUE_LOGIN);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if there is already a user with this email
     */
    @Override
    protected void validateEmail(UserModel userModel, Errors errors) {
        try {
            User userByEmail = userDao.findByEmail(userModel.getUser().getEmail());
            if (userByEmail != null) {
                errors.rejectValue("user.email", null, ERROR_NOT_UNIQUE_EMAIL);
            }
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
