package com.nixsolutions.bondarenko.study.jsp.validate;

import com.nixsolutions.bondarenko.study.jsp.dto.UserDto;

import java.util.Map;

/**
 * @author Yulya Bondarenko
 */
public interface UserValidator {
    String ERROR_NOT_UNIQUE_LOGIN = "User with this login already exists";
    String ERROR_NOT_UNIQUE_EMAIL = "This email is already attached to another user";

    Map<String, String> validate(UserDto userDTO);
}
