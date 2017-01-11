package com.nixsolutions.bondarenko.study.validation.model;

import com.nixsolutions.bondarenko.study.model.UserModel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public abstract class UserModelValidator implements Validator {
    protected String ERROR_NOT_UNIQUE_LOGIN = "user with this login already exists";
    protected String ERROR_NOT_UNIQUE_EMAIL = "this email is already attached to another user";

    @Override
    public boolean supports(Class<?> aClass) {
        return UserModel.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserModel userModel = (UserModel) object;
        if (!errors.hasFieldErrors("passwordConfirm")) {
            validatePassword(userModel, errors);
        }
        if (!errors.hasFieldErrors("user.email")) {
            validateEmail((UserModel) object, errors);
        }
    }

    public abstract void validateEmail(UserModel userModel, Errors errors);

    private void validatePassword(UserModel userModel, Errors errors) {
        if (!userModel.getUser().getPassword().equals(userModel.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", null, "passwords do not match");
        }
    }
}