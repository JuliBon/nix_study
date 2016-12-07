package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.UserFieldPattern;
import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.model.ModelConvert;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.validate.UserCreateValidator;
import com.nixsolutions.bondarenko.study.validate.UserUpdateValidator;
import com.nixsolutions.bondarenko.study.validate.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    private static final String ACTION_CREATE_USER = "create_user";
    private static final String ACTION_EDIT_USER = "edit_user";
    private static final Map<String, UserFieldPattern> userFieldPatternMap = UserFieldPattern.asMap();

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin(ModelMap model) {
        try {
            List<User> userList = userDao.findAll();
            model.addAttribute("userList", userList);
            return new ModelAndView("admin", model);
        } catch (Exception e) {
            model.addAttribute("error", e);
            return new ModelAndView("admin", model);
        }
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") String id) {
        if (id != null) {
            Long id_value = new Long(id);
            try {
                userDao.remove(userDao.findById(id_value));
            } catch (Exception e) {
                ModelMap modelMap = new ModelMap("error", e);
                return new ModelAndView("error", modelMap);
            }
        }
        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value = "/admin/create", method = RequestMethod.GET)
    public ModelAndView createUser(ModelMap modelMap) {
        modelMap.put("action", ACTION_CREATE_USER);
        modelMap.put("userFieldPatternMap", userFieldPatternMap);
        try {
            List<Role> roleList = roleDao.findAll();
            modelMap.put("roleList", roleList);
            return new ModelAndView("user_form", modelMap);
        } catch (Exception e) {
            modelMap.addAttribute("error", e);
            return new ModelAndView("error", modelMap);
        }
    }

    @RequestMapping(value = "/admin/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.addAttribute("action", ACTION_EDIT_USER);
        modelMap.addAttribute("userFieldPatternMap", userFieldPatternMap);

        if (id != null) {
            Long id_value = new Long(id);
            try {
                User user = userDao.findById(id_value);
                UserModel userModel = new UserModel(user.getId().toString(),
                        user.getLogin(), "", "",
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getBirthday().toString(),
                        user.getRole().getName());

                modelMap.addAttribute("user", userModel);

                List<Role> roleList = roleDao.findAll();
                modelMap.addAttribute("roleList", roleList);
            } catch (Exception e) {
                modelMap.addAttribute("error", e);
                return new ModelAndView("error", modelMap);
            }
        }
        return new ModelAndView("user_form", modelMap);
    }

    @RequestMapping(value = "/admin/create", method = RequestMethod.POST)
    public ModelAndView createUser(@ModelAttribute("user") UserModel userModel, ModelMap modelMap) {
        modelMap.put("action", ACTION_CREATE_USER);

        try {
            UserValidator userValidator = new UserCreateValidator(userDao);
            Map<String, String> errorMap = userValidator.validate(userModel);

            if (errorMap.isEmpty()) {
                User user = ModelConvert.convertToUser(userModel, roleDao);
                userDao.create(user);
                modelMap.put("userList", userDao.findAll());
                return new ModelAndView("admin", modelMap);
            } else {
                modelMap.put("userModel", userModel);
                modelMap.put("errorMap", errorMap);

                List<Role> roleList = roleDao.findAll();
                modelMap.put("roleList", roleList);
                modelMap.put("userFieldPatternMap", userFieldPatternMap);
                return new ModelAndView("user_form", modelMap);
            }
        } catch (Exception e) {
            modelMap.addAttribute("error", e);
            return new ModelAndView("error", modelMap);
        }
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    public ModelAndView userForm(@ModelAttribute("user") UserModel userModel, ModelMap modelMap) {
        modelMap.put("action", ACTION_EDIT_USER);

        try {
            UserValidator userValidator = new UserUpdateValidator(userDao);

            Map<String, String> errorMap = userValidator.validate(userModel);

            if (errorMap.isEmpty()) {
                User user = ModelConvert.convertToUser(userModel, roleDao);
                userDao.update(user);
                modelMap.put("userList", userDao.findAll());
                return new ModelAndView("admin", modelMap);
            } else {
                modelMap.put("userModel", userModel);
                modelMap.put("errorMap", errorMap);

                List<Role> roleList = roleDao.findAll();
                modelMap.put("roleList", roleList);
                modelMap.put("userFieldPatternMap", userFieldPatternMap);
                return new ModelAndView("user_form", modelMap);
            }
        } catch (Exception e) {
            modelMap.addAttribute("error", e);
            return new ModelAndView("error", modelMap);
        }
    }
}
