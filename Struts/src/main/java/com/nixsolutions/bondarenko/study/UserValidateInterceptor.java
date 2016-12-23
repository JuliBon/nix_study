package com.nixsolutions.bondarenko.study;

import com.nixsolutions.bondarenko.study.action.Register;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.util.List;
import java.util.Map;

public class UserValidateInterceptor extends AbstractInterceptor {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        UserModel userModel = ((Register) actionInvocation.getAction()).getUserModel();
        Map<String, List<String>> fieldErrors = ((Register) actionInvocation.getAction()).getFieldErrors();
        ((Register) actionInvocation.getAction()).addFieldError("userModel.user.login", "already attached!");

        return actionInvocation.invoke();
    }
}
