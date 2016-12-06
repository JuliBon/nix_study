package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.HibernateUtil;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.dao.hibernate.HibernateUserDao;
import com.nixsolutions.bondarenko.study.user.library.User;
import com.nixsolutions.bondarenko.study.user.library.UserLibraryRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SigninController {
    private UserDao userDao = new HibernateUserDao(HibernateUtil.getSessionFactory());

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(ModelMap model) {
        model.addAttribute("user", new User());
        return "signin";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String singnin(@ModelAttribute("user") User user, Model model) {
/*        try {
            boolean incorrectLoginOrPassword = false;
            User userByLogin = userDao.findByLogin(user.getLogin());
            if (userByLogin != null) {
                if (userByLogin.getPassword().equals(user.getLogin())) {
                    //request.getSession().setAttribute("currentUser", userByLogin);

                    if (userByLogin.getRole().getName().equals(UserLibraryRole.USER.getName())) {
                        return "home";
                    } else if (userByLogin.getRole().getName().equals(UserLibraryRole.ADMIN.getName())) {
                        //response.sendRedirect("/admin");
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
                return "signin";
            }
        } catch (Exception e) {
            model.addAttribute("error", e);
            return "error";
        }*/
        return "signin";
    }
}


