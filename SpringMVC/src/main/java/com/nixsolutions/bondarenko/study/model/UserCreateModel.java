package com.nixsolutions.bondarenko.study.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserCreateModel extends UserModel {
    @NotNull
    @NotEmpty
    protected String roleName;

    public UserCreateModel(){

    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
