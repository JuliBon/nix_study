package com.nixsolutions.bondarenko.study.dto;

public class UserRegisterDto extends UserDto {
    protected String oldPassword;

    public UserRegisterDto(String id, String login, String oldPassword, String password, String passwordConfirm, String email, String firstName, String lastName, String birthday, String roleName) {
        super(id, login, password, passwordConfirm, email, firstName, lastName, birthday, roleName);
    }
}
