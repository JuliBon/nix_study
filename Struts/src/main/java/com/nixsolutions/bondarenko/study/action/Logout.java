package com.nixsolutions.bondarenko.study.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class Logout extends ActionSupport {

    @Override
    public String execute() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(ServletActionContext.getRequest(), ServletActionContext.getResponse(), auth);
        }
        return SUCCESS;
    }
}
