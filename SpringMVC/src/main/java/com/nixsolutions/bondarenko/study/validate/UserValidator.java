package com.nixsolutions.bondarenko.study.validate;

import com.nixsolutions.bondarenko.study.model.UserModel;

import java.util.Map;

/**
 * @author Yulya Bondarenko
 */
public interface UserValidator {
    String ERROR_NOT_UNIQUE_LOGIN = "User with this login already exists";
    String ERROR_NOT_UNIQUE_EMAIL = "This email is already attached to another user";

    Map<String, String> validate(UserModel userModel);
}
