package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
import com.nixsolutions.bondarenko.study.model.ModelConvert;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.recaptcha.VerifyUtils;
import com.nixsolutions.bondarenko.study.validate.model.UserModelCreateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterController {
    public static final String ACTION_REGISTER_USER = "register_user";

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private VerifyUtils verifyUtils;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(ModelMap modelMap) {
        modelMap.put("action", ACTION_REGISTER_USER);

        modelMap.addAttribute("userModel", new UserModel());
        return new ModelAndView("user_form", modelMap);

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("userModel") @Valid UserModel userModel,
                                 BindingResult bindingResult,
                                 ModelMap modelMap,
                                 HttpServletRequest request) {
        modelMap.put("action", ACTION_REGISTER_USER);

        new UserModelCreateValidator(userDao).validate(userModel, bindingResult);
        boolean valid = !bindingResult.hasErrors();
        if (valid) {
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            valid = verifyUtils.verify(gRecaptchaResponse);
            if (!valid) {
                modelMap.put("captchaError", "Captcha invalid!");
            }
        }
        if (valid) {
            User user = ModelConvert.convertToUser(userModel);
            user.setRole(roleDao.findByName(UserLibraryRole.USER.name()));
            userDao.create(user);

            modelMap.put("registered", true);
            return new ModelAndView("login", modelMap);
        } else {
            modelMap.put("userModel", userModel);

            if (bindingResult.hasErrors()) {
                modelMap.put(BindingResult.class.getName() + ".user", bindingResult);
            }
            return new ModelAndView("user_form", modelMap);
        }
    }
}
