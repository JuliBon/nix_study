package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.model.ModelConverter;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.recaptcha.VerifyUtils;
import com.nixsolutions.bondarenko.study.validation.UserCreateValidator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;
import org.apache.struts2.ServletActionContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Register extends ActionSupport implements ModelDriven<UserModel> {
    private UserDao userDao;
    private RoleDao roleDao;
    private UserModel userModel = new UserModel();

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
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

    @Override
    public String execute() throws Exception {
        User user = ModelConverter.convertToUser(userModel, roleDao);
        userDao.create(user);
        return SUCCESS;
    }

    @Override
    public void validate() {
        Map<String, List<String>> fieldErrors = getFieldErrors();
        new UserCreateValidator(userDao).validate(userModel, fieldErrors);
        setFieldErrors(fieldErrors);

        if (fieldErrors.isEmpty()) {
            String gRecaptchaResponse = ServletActionContext.getRequest().getParameter("g-recaptcha-response");
            if (!VerifyUtils.verify(gRecaptchaResponse)) {
                setActionErrors(Arrays.asList("Captcha invalid!"));
            }
        }
    }
}
