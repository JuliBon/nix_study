package com.nixsolutions.bondarenko.study.validate;

import com.nixsolutions.bondarenko.study.UserFieldPattern;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.dto.UserDto;
import com.nixsolutions.bondarenko.study.user.library.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yulya Bondarenko
 */
public class UserCreateValidator extends UserAbstractValidator {
    private UserDao userDao;

    public UserCreateValidator(UserDao userDao) {
        this.userDao = userDao;
    }


    /***
     * Validate email format and email uniqueness
     * @param userDTO instance of User data transfer object
     * @throws RuntimeException if Exception occurred while searching user in userDao
     */
    @Override
    public Map<String, String> validate(UserDto userDTO) {
        UserDto userCreateDTO = userDTO;

        Map<String, String> errorMap = new HashMap<>();
        validateLogin(userCreateDTO.getLogin(), errorMap);
        validatePassword(userCreateDTO.getPassword(), userCreateDTO.getPasswordConfirm(), errorMap);
        try {
            validateEmail(userCreateDTO.getEmail(), errorMap);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        validateFirstName(userCreateDTO.getFirstName(), errorMap);
        validateLastName(userCreateDTO.getLastName(), errorMap);
        validateBirthday(userCreateDTO.getBirthday(), errorMap);
        return errorMap;
    }

    private void validateLogin(String login, Map<String, String> errorMap) throws RuntimeException {
        //validate login format
        if (!login.matches(UserFieldPattern.LOGIN_PATTERN.getPattern())) {
            errorMap.put("login", "Login does not matches request format: "
                    + UserFieldPattern.LOGIN_PATTERN.getValidateTitle());
            return;
        }
        try {
            //whether login is not unique
            if (userDao.findByLogin(login) != null) {
                errorMap.put("login", ERROR_NOT_UNIQUE_LOGIN);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    protected void validateEmail(String email, Map<String, String> errorMap) throws Exception {
        super.validateEmail(email, errorMap);

        User userByEmail = userDao.findByEmail(email);
        if (userByEmail != null) {  //if there is already a user with this email
            errorMap.put("email", ERROR_NOT_UNIQUE_EMAIL);
        }
    }
}
