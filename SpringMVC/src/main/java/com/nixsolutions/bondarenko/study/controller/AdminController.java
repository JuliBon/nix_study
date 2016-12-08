package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
import com.nixsolutions.bondarenko.study.model.*;
import com.nixsolutions.bondarenko.study.validate.UserCreateValidator;
import com.nixsolutions.bondarenko.study.validate.UserUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private static final String ACTION_CREATE_USER = "create_user";
    private static final String ACTION_EDIT_USER = "edit_user";
    private List<String> roleNameList;

    {
        roleNameList = new ArrayList<>();
        roleNameList.add(UserLibraryRole.USER.getName());
        roleNameList.add(UserLibraryRole.ADMIN.getName());
    }

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin(ModelMap modelMap, Authentication authentication) {
        modelMap.addAttribute("userName", authentication.getName());
        try {
            List<User> userList = userDao.findAll();
            modelMap.addAttribute("userList", userList);
            return new ModelAndView("admin", modelMap);
        } catch (Exception e) {
            modelMap.addAttribute("error", e);
            return new ModelAndView("admin", modelMap);
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
    public ModelAndView create(ModelMap modelMap, Authentication authentication) {
        modelMap.addAttribute("userName", authentication.getName());
        modelMap.put("action", ACTION_CREATE_USER);
        try {
            modelMap.put("roleNameList", roleNameList);
            modelMap.put("user", new UserCreateModel());
            return new ModelAndView("user_form", modelMap);
        } catch (Exception e) {
            modelMap.addAttribute("error", e);
            return new ModelAndView("error", modelMap);
        }
    }

    @RequestMapping(value = "/admin/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") String id, ModelMap modelMap, Authentication authentication) {
        modelMap.addAttribute("userName", ((UserDetails) authentication.getPrincipal()).getUsername());
        modelMap.addAttribute("action", ACTION_EDIT_USER);

        if (id != null) {
            Long id_value = new Long(id);
            try {
                User user = userDao.findById(id_value);
                UserUpdateModel userModel = new UserUpdateModel(user.getId().toString(),
                        user.getLogin(), "", "",
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getBirthday().toString(),
                        user.getRole().getName());

                modelMap.addAttribute("user", userModel);
                modelMap.addAttribute("roleNameList", roleNameList);
            } catch (Exception e) {
                modelMap.addAttribute("error", e);
                return new ModelAndView("error", modelMap);
            }
        }
        return new ModelAndView("user_form", modelMap);
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("user") @Valid UserUpdateModel userModel,
                             BindingResult bindingResult,
                             ModelMap modelMap) {
        modelMap.put("action", ACTION_EDIT_USER);

        try {
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
        } catch (Exception e) {
            modelMap.addAttribute("error", e);
            return new ModelAndView("error", modelMap);
        }
    }


    @RequestMapping(value = "/admin/create", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("user") @Valid UserCreateModel userModel,
                               BindingResult bindingResult,
                               ModelMap modelMap) {
        modelMap.put("action", ACTION_CREATE_USER);

        try {
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
        } catch (Exception e) {
            modelMap.addAttribute("error", e);
            return new ModelAndView("error", modelMap);
        }
    }
}
