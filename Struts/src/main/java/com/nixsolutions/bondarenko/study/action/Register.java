package com.nixsolutions.bondarenko.study.action;

import com.nixsolutions.bondarenko.study.model.UserModel;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class Register extends ActionSupport implements ModelDriven {
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    public Object getModel() {
        return new UserModel();
    }
}
