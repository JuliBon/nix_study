package com.nixsolutions.bondarenko.study.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/index";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String roleName;
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                roleName = authority.getAuthority();
                if (roleName.equals("ROLE_ADMIN")) {
                    return "redirect:/admin";
                }
                if (roleName.equals("ROLE_USER")) {
                    modelMap.addAttribute("userName", authentication.getName());
                    return "home";
                }
            }
        }
        return "redirect:/login";
    }
}
