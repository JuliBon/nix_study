package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.model.UserModel;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.interceptor.ValidationAware;
import org.apache.struts.beanvalidation.constraints.FieldMatch;

import javax.validation.Valid;
import java.util.Collection;

@FieldMatch(first = "userModel.user.password",
        second = "userModel.passwordConfirm",
        message = "passwords are not matching" )
public class Register extends ActionSupport {

    private static final long serialVersionUID = 1L;

    @Valid
    private UserModel userModel;

    public String execute() throws Exception {
        UserModel userModel = getUserModel();

        Collection<String> actionErrors = getActionErrors();
        return  INPUT;
    }

    @Override
    public void validate() {
        UserModel userModel = getUserModel();
        Collection<String> actionErrors = getActionErrors();
        super.validate();
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
