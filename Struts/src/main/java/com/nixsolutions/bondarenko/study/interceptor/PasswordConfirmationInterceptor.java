package com.nixsolutions.bondarenko.study.interceptor;

import com.nixsolutions.bondarenko.study.action.Register;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class PasswordConfirmationInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        UserModel userModel = ((Register) actionInvocation.getAction()).getUserModel();
        if (!userModel.getUser().getPassword().equals(userModel.getPasswordConfirm())) {
            ((Register) actionInvocation.getAction()).addFieldError("userModel.passwordConfirm", "passwords are not matching");
        }
        return actionInvocation.invoke();
    }
}