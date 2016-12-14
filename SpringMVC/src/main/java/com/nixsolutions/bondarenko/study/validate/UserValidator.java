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
        if (!errors.hasFieldErrors("user.password")) {
            validatePassword(userModel, errors);
        }
        if (!errors.hasFieldErrors("birthdayStr")) {
            validateBirthday(userModel, errors);
        }
        if (!errors.hasFieldErrors("user.email")) {
            validateEmail((UserModel) object, errors);
        }
    }

    protected abstract void validateEmail(UserModel userModel, Errors errors);

    private void validatePassword(UserModel userModel, Errors errors) {
        if (!userModel.getUser().getPassword().equals(userModel.getPasswordConfirm())) {
            errors.rejectValue("user.password", null, "passwords do not match");
        }
    }

    private void validateBirthday(UserModel userModel, Errors errors) {
        if (!userModel.getBirthdayStr().matches("^\\d{4}-(0\\d|10|11|12)-([012]\\d|30|31)$")) {
            errors.rejectValue("birthdayStr", null, "Birthday does not matches request format: "
                    + "bad date format");
        }
    }
}
