package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.UserFieldPattern;
import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.model.ModelConvert;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    private static final String ACTION_CREATE_USER = "create_user";
    private static final String ACTION_EDIT_USER = "edit_user";

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

    @RequestMapping(value = "/admin/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") String id) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("action", ACTION_EDIT_USER);
        modelMap.addAttribute("userFieldPatternMap", getUserFieldPatternMap());

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

                List<Role> roleList = findAllRoles();
                modelMap.addAttribute("roleList", roleList);
                return new ModelAndView("user_form", modelMap);
            } catch (Exception e) {
                modelMap.addAttribute("error", e);
                return new ModelAndView("error", modelMap);
            }
        }
        return null;
    }

    private Map<String, UserFieldPattern> getUserFieldPatternMap() {
        Map<String, UserFieldPattern> userFieldPatternMap = new HashMap<>();
        userFieldPatternMap.put("login", UserFieldPattern.LOGIN_PATTERN);
        userFieldPatternMap.put("password", UserFieldPattern.PASSWORD_PATTERN);
        userFieldPatternMap.put("email", UserFieldPattern.EMAIL_PATTERN);
        userFieldPatternMap.put("firstName", UserFieldPattern.FIRST_NAME_PATTERN);
        userFieldPatternMap.put("lastName", UserFieldPattern.LAST_NAME_PATTERN);
        userFieldPatternMap.put("birthday", UserFieldPattern.BIRTHDAY_PATTERN);
        return userFieldPatternMap;
    }

    private List<Role> findAllRoles() throws Exception {
        List<Role> roleList = new ArrayList<>();

        Role roleAdmin = roleDao.findByName(UserLibraryRole.ADMIN.getName());
        Role roleUser = roleDao.findByName(UserLibraryRole.USER.getName());
        if (roleAdmin != null) roleList.add(roleAdmin);
        if (roleUser != null) roleList.add(roleUser);

        return roleList;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ModelAndView userForm(@ModelAttribute("user") UserModel userModel, ModelMap modelMap) {
        String action = (String) modelMap.get("action");
        if (action != null) {
            modelMap.put("action", action);

            try {
                UserValidator userValidator;
                Map<String, String> errorMap = new HashMap<>();
                if (action.equals(ACTION_CREATE_USER)) {
                    userValidator = new UserCreateValidator(userDao);
                    errorMap = userValidator.validate(userModel);
                } else if (action.equals(ACTION_EDIT_USER)) {
                    userValidator = new UserUpdateValidator(userDao);
                    errorMap = userValidator.validate(userModel);
                }

                if (errorMap.isEmpty()) {
                    User user = ModelConvert.convertToUser(userModel, roleDao);
                    if (action.equals(ACTION_CREATE_USER)) {
                        userDao.create(user);
                    } else if (action.equals(ACTION_EDIT_USER)) {
                        userDao.update(user);
                    }
                    modelMap.put("userList", userDao.findAll());
                    return new ModelAndView("admin", modelMap);

                } else {
                    modelMap.put("userModel", userModel);
                    modelMap.put("errorMap", errorMap);

                    List<Role> roleList = findAllRoles();
                    modelMap.put("roleList", roleList);
                    modelMap.put("userFieldPatternMap", getUserFieldPatternMap());
                    return new ModelAndView("user_form", modelMap);

                }
            } catch (Exception e) {
                modelMap.addAttribute("error", e);
                return new ModelAndView("error", modelMap);
            }
        }
        return null;
    }
}
