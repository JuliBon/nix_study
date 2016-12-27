package com.nixsolutions.bondarenko.study.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Home extends ActionSupport {
    private String userName;

    public String getUserName() {
        return userName;
    }

    @Override
    public String execute() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            userName = authentication.getName();
        }
        return SUCCESS;
    }
}
