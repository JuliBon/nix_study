package com.nixsolutions.bondarenko.study.validate;

import com.nixsolutions.bondarenko.study.UserFieldPattern;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserUpdateValidator extends UserAbstractValidator {
    private UserDao userDao;

    public UserUpdateValidator(UserDao userDao) {
        this.userDao = userDao;

    }

    /***
     * Validate email format and email uniqueness
     * @param userDTO instance of User data transfer object
     * @throws RuntimeException if Exception occurred while searching user in userDao
     */
    @Override
    public Map<String, String> validate(UserModel userDTO) throws RuntimeException {

        Map<String, String> errorMap = new HashMap<>();
        validatePassword(userDTO.getPassword(), userDTO.getPasswordConfirm(), errorMap);
        try {
            validateEmail(userDTO.getEmail(), errorMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        validateFirstName(userDTO.getFirstName(), errorMap);
        validateLastName(userDTO.getLastName(), errorMap);
        validateBirthday(userDTO.getBirthday(), errorMap);
        return errorMap;
    }

    private void validatePassword(String password, String passwordConfirm, Map<String, String> errorMap) {
        if (!password.matches(UserFieldPattern.PASSWORD_PATTERN.getPattern())) {
            errorMap.put("password", "Password does not matches request format: "
                    + UserFieldPattern.PASSWORD_PATTERN.getValidateTitle());
            return;
        }
        if (!password.equals(passwordConfirm)) {
            errorMap.put("password", "Passwords do not match!");
        }
    }

    /***
     * Validate email format and email uniqueness
     * @param email
     * @param errorMap
     * @throws Exception if Exception occurred while searching user in userDao
     */
    protected void validateEmail(String email, String login, Map<String, String> errorMap) throws Exception {
        super.validateEmail(email, errorMap);

        User userByEmail = userDao.findByEmail(email);
        if (userByEmail != null) {  //if user with this email was found and he is not this user
            if (!userByEmail.getLogin().equals(login)) {
                errorMap.put("password", ERROR_NOT_UNIQUE_EMAIL);
            }
        }
    }
}

