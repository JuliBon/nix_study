package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.UserFieldPattern;
import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
import com.nixsolutions.bondarenko.study.model.ModelConvert;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.recaptcha.VerifyUtils;
import com.nixsolutions.bondarenko.study.validate.UserCreateValidator;
import com.nixsolutions.bondarenko.study.validate.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class RegisterController {
    private static final String ACTION_REGISTER_USER = "register_user";
    private static final Map<String, UserFieldPattern> userFieldPatternMap = UserFieldPattern.asMap();

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(ModelMap modelMap) {
        modelMap.put("action", ACTION_REGISTER_USER);
        modelMap.put("userFieldPatternMap", userFieldPatternMap);
        try {
            return new ModelAndView("user_form", modelMap);
        } catch (Exception e) {
            modelMap.addAttribute("error", e);
            return new ModelAndView("error", modelMap);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("user") UserModel userModel, ModelMap modelMap, HttpServletRequest request) {
        modelMap.put("action", ACTION_REGISTER_USER);
        try {
            UserValidator userValidator = new UserCreateValidator(userDao);
            Map<String, String> errorMap = userValidator.validate(userModel);

            boolean valid = errorMap.isEmpty();
            if (valid) {
                String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
                valid = VerifyUtils.verify(gRecaptchaResponse);
                if (!valid) {
                    modelMap.put("captchaError", "Captcha invalid!");
                }
            }
            if (valid) {
                User user = ModelConvert.convertToUser(userModel, roleDao);
                user.setRole(roleDao.findByName(UserLibraryRole.USER.getName()));
                userDao.create(user);
                modelMap.put("currentUser", user);
                return new ModelAndView("home", modelMap);
            } else {
                modelMap.put("userModel", userModel);
                modelMap.put("errorMap", errorMap);

                modelMap.put("userFieldPatternMap", userFieldPatternMap);
                return new ModelAndView("user_form", modelMap);
            }
        } catch (Exception e) {
            modelMap.addAttribute("error", e);
            return new ModelAndView("error", modelMap);
        }
    }
}
