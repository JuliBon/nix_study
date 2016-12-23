package com.nixsolutions.bondarenko.study.validation;

import com.nixsolutions.bondarenko.study.model.UserModel;

import java.util.List;
import java.util.Map;

public abstract class UserValidator {
    String ERROR_NOT_UNIQUE_LOGIN = "user with this login already exists";
    String ERROR_NOT_UNIQUE_EMAIL = "this email is already attached to another user";

    public void validate(Object object, Map<String, List<String>> fieldErrors) {
        if (!fieldErrors.containsKey("userModel.user.email")) {
            validateEmail((UserModel) object, fieldErrors);
        }
    }

    protected abstract void validateEmail(UserModel userModel, Map<String, List<String>> fieldErrors);
}
