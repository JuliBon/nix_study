package com.nixsolutions.bondarenko.study.validate;

import com.nixsolutions.bondarenko.study.model.UserModel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Yulya Bondarenko
 */
public abstract class UserValidator implements Validator {
    String ERROR_NOT_UNIQUE_LOGIN = "user with this login already exists";
    String ERROR_NOT_UNIQUE_EMAIL = "this email is already attached to another user";

    @Override
    public boolean supports(Class<?> aClass) {
        return UserModel.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserModel userModel = (UserModel) object;
        if (!errors.hasFieldErrors("password")) {
            validatePassword(userModel.getPassword(), userModel.getPasswordConfirm(), errors);
        }
        if (!errors.hasFieldErrors("birthday")) {
            validateBirthday(userModel.getBirthday(), errors);
        }
        if (!errors.hasFieldErrors("email")) {
            validateEmail((UserModel) object, errors);
        }
    }

    protected abstract void validateEmail(UserModel userModel, Errors errors);

    protected void validatePassword(String password, String passwordConfirm, Errors errors) {
        if (!password.equals(passwordConfirm)) {
            if (!errors.hasFieldErrors(password)) {
                errors.rejectValue("password", null, "passwords do not match");
            }
        }
    }

    protected void validateBirthday(String birthday, Errors errors) {
        if (!birthday.matches("^\\d{4}-(0\\d|10|11|12)-([012]\\d|30|31)$")) {
            errors.rejectValue("birthday", null, "Birthday does not matches request format: "
                    + "bad date format");
        }
    }
}
