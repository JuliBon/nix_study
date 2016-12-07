package com.nixsolutions.bondarenko.study;

import java.util.HashMap;
import java.util.Map;

public enum UserFieldPattern {
    LOGIN_PATTERN("^[a-zA-Z](([._-][a-zA-Z0-9])|[a-zA-Z0-9])*$",
            "3-15 characters, beginning with letter. Can include letters, numbers, dashes, and underscores."),
    PASSWORD_PATTERN("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$",
            "at least one number and one uppercase and lowercase letter"),
    EMAIL_PATTERN("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$",
            ""),
    FIRST_NAME_PATTERN("[A-Za-z]+",
            "one or more letters"),
    LAST_NAME_PATTERN("[A-Za-z]+",
            "one or more letters"),
    BIRTHDAY_PATTERN("^\\d{4}-(0\\d|10|11|12)-([012]\\d|30|31)$",
            "YYYY-DD-MM");

    private String pattern;
    private String validateTitle;

    UserFieldPattern(String pattern, String validateTitle) {
        this.pattern = pattern;
        this.validateTitle = validateTitle;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getValidateTitle() {
        return validateTitle;
    }

    public void setValidateTitle(String validateTitle) {
        this.validateTitle = validateTitle;
    }

    public static Map<String, UserFieldPattern> asMap() {
        Map<String, UserFieldPattern> userFieldPatternMap = new HashMap<>();
        userFieldPatternMap.put("login", UserFieldPattern.LOGIN_PATTERN);
        userFieldPatternMap.put("password", UserFieldPattern.PASSWORD_PATTERN);
        userFieldPatternMap.put("email", UserFieldPattern.EMAIL_PATTERN);
        userFieldPatternMap.put("firstName", UserFieldPattern.FIRST_NAME_PATTERN);
        userFieldPatternMap.put("lastName", UserFieldPattern.LAST_NAME_PATTERN);
        userFieldPatternMap.put("birthday", UserFieldPattern.BIRTHDAY_PATTERN);
        return userFieldPatternMap;
    }
}
