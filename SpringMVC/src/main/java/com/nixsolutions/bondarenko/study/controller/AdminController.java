package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.RoleUtils;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.model.ModelConvert;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.validate.UserCreateValidator;
import com.nixsolutions.bondarenko.study.validate.UserUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {
    public static final String ACTION_CREATE_USER = "create_user";
    public static final String ACTION_EDIT_USER = "edit_user";
    private List<String> roleNameList = RoleUtils.getRoleNames();

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelMap.addAttribute("userName", authentication.getName());

        List<User> userList = userDao.findAll();
        modelMap.addAttribute("userList", userList);
        return new ModelAndView("admin", modelMap);
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") String id) throws Exception {
        userDao.remove(userDao.findById(Long.valueOf(id)));
        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value = "/admin/create", method = RequestMethod.GET)
    public ModelAndView create(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelMap.addAttribute("userName", authentication.getName());
        modelMap.put("action", ACTION_CREATE_USER);

        modelMap.put("roleNameList", roleNameList);
        modelMap.put("userModel", new UserModel());
        return new ModelAndView("user_form", modelMap);
    }

    @RequestMapping(value = "/admin/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") String id, ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelMap.addAttribute("userName", authentication.getName());
        modelMap.addAttribute("action", ACTION_EDIT_USER);

        User user = userDao.findById(Long.valueOf(id));
        UserModel userModel = new UserModel(user);
        userModel.getUser().setPassword(null);

        modelMap.addAttribute("userModel", userModel);
        modelMap.addAttribute("roleNameList", roleNameList);
        return new ModelAndView("user_form", modelMap);
    }

    @RequestMapping(value = "/admin/create", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("userModel") @Valid UserModel userModel,
                               BindingResult bindingResult,
                               ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelMap.addAttribute("userName", authentication.getName());
        modelMap.put("action", ACTION_CREATE_USER);

        new UserCreateValidator(userDao).validate(userModel, bindingResult);
        if (!bindingResult.hasErrors()) {
            User user = ModelConvert.convertToUser(userModel, roleDao);
            userDao.create(user);
            modelMap.put("userList", userDao.findAll());
            return new ModelAndView("admin", modelMap);
        } else {
            modelMap.put("userModel", userModel);
            if (bindingResult.hasErrors()) {
                modelMap.put(BindingResult.class.getName() + ".user", bindingResult);
            }
            modelMap.put("roleNameList", roleNameList);
            return new ModelAndView("user_form", modelMap);
        }
    }


    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("userModel") @Valid UserModel userModel,
                             BindingResult bindingResult,
                             ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelMap.addAttribute("userName", authentication.getName());
        modelMap.put("action", ACTION_EDIT_USER);

        new UserUpdateValidator(userDao).validate(userModel, bindingResult);
        if (!bindingResult.hasErrors()) {
            User user = ModelConvert.convertToUser(userModel, roleDao);
            userDao.update(user);
            modelMap.put("userList", userDao.findAll());
            return new ModelAndView("admin", modelMap);
        } else {
            modelMap.put("userModel", userModel);
            modelMap.put("roleNameList", roleNameList);
            if (bindingResult.hasErrors()) {
                modelMap.put(BindingResult.class.getName() + ".user", bindingResult);
            }
            return new ModelAndView("user_form", modelMap);
        }
    }
}
