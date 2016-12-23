package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
import com.nixsolutions.bondarenko.study.model.ModelConvert;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.validation.UserCreateValidator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import java.util.List;
import java.util.Map;

public class Register extends ActionSupport implements ModelDriven<UserModel> {

    private UserDao userDao;
    private RoleDao roleDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    private UserModel userModel = new UserModel();

    @Override
    public String execute() throws Exception {
        User user = ModelConvert.convertToUser(userModel);
        user.setRole(roleDao.findByName(UserLibraryRole.USER.name()));
        userDao.create(user);
        return SUCCESS;
    }

    @Override
    public void validate() {
        Map<String, List<String>> fieldErrors = getFieldErrors();
        new UserCreateValidator(userDao).validate(userModel, fieldErrors);
        setFieldErrors(fieldErrors);
    }

    public UserModel getUserModel() {
        return userModel;
    }

    @VisitorFieldValidator
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public UserModel getModel() {
        return userModel;
    }
}
