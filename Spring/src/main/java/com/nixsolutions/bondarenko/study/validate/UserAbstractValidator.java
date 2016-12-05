package com.nixsolutions.bondarenko.study.validate;

import com.nixsolutions.bondarenko.study.UserFieldPattern;

import java.util.Map;

public abstract class UserAbstractValidator implements UserValidator {
    protected void validateFirstName(String firstName, Map<String, String> errorMap) {
        if (!firstName.matches(UserFieldPattern.FIRST_NAME_PATTERN.getPattern())) {
            errorMap.put("firstName", "First name does not matches request format: "
                    + UserFieldPattern.FIRST_NAME_PATTERN.getValidateTitle());
        }
    }

    protected void validateLastName(String lastName, Map<String, String> errorMap) {
        if (!lastName.matches(UserFieldPattern.LAST_NAME_PATTERN.getPattern())) {
            errorMap.put("lastName", "Last name does not matches request format: "
                    + UserFieldPattern.LAST_NAME_PATTERN.getValidateTitle());
        }
    }

    protected void validateBirthday(String birthday, Map<String, String> errorMap) {
        if (!birthday.matches(UserFieldPattern.BIRTHDAY_PATTERN.getPattern())) {
            errorMap.put("birthday", "Birthday does not matches request format: "
                    + UserFieldPattern.BIRTHDAY_PATTERN.getValidateTitle());
        }
    }

    protected void validateEmail(String email, Map<String, String> errorMap) throws Exception{
        if (!email.matches(UserFieldPattern.EMAIL_PATTERN.getPattern())) {
            errorMap.put("email", "Wrong email format"
                    + UserFieldPattern.EMAIL_PATTERN.getValidateTitle());
        }
    }
}
