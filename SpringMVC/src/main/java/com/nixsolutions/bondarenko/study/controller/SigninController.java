package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class SigninController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "redirect: signin";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(ModelMap model) {
        model.addAttribute("user", new User());
        return "signin";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ModelAndView signin(@ModelAttribute("user") User user, Model model) {
        try {
            boolean incorrectLoginOrPassword = false;
            User userByLogin = userDao.findByLogin(user.getLogin());
            if (userByLogin != null) {
                if (userByLogin.getPassword().equals(user.getPassword())) {
                    model.addAttribute("currentUser", userByLogin);
                    if (userByLogin.getRole().getName().equals(UserLibraryRole.USER.getName())) {
                        return new ModelAndView("home", model.asMap());
                    } else if (userByLogin.getRole().getName().equals(UserLibraryRole.ADMIN.getName())) {
                        return new ModelAndView("redirect:/admin");
                    }
                } else {
                    incorrectLoginOrPassword = true;
                }
            } else {
                incorrectLoginOrPassword = true;
            }
            if (incorrectLoginOrPassword) {
                model.addAttribute("user", user);
                model.addAttribute("errorMessage", "Incorrect login or password!");
            }
        } catch (Exception e) {
            model.addAttribute("error", e);
            return new ModelAndView("error", model.asMap());
        }
        return new ModelAndView("signin", model.asMap());
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {

        model.addAttribute("currentUser", new User());
        return "signin";
    }
}


