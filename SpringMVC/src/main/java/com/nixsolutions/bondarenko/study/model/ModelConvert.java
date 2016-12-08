package com.nixsolutions.bondarenko.study.model;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.User;

import java.sql.Date;

public class ModelConvert {
    public static User convertToUser(UserCreateModel userCreateModel, RoleDao roleDao) throws Exception {
        User user = new User();
        user.setLogin(userCreateModel.getLogin());
        user.setEmail(userCreateModel.getEmail());
        user.setPassword(userCreateModel.getPassword());
        user.setFirstName(userCreateModel.getFirstName());
        user.setLastName(userCreateModel.getLastName());
        user.setBirthday(Date.valueOf(userCreateModel.getBirthday()));

        if (userCreateModel.getRoleName() != null) {
            user.setRole(roleDao.findByName(userCreateModel.roleName));
        }
        return user;
    }

    public static User convertToUser(UserUpdateModel userUpdateModel, RoleDao roleDao) throws Exception {
        User user = new User();

        user.setId(new Long(userUpdateModel.getId()));
        user.setLogin(userUpdateModel.getLogin());
        user.setEmail(userUpdateModel.getEmail());
        user.setPassword(userUpdateModel.getPassword());
        user.setFirstName(userUpdateModel.getFirstName());
        user.setLastName(userUpdateModel.getLastName());
        user.setBirthday(Date.valueOf(userUpdateModel.getBirthday()));

        if (userUpdateModel.getRoleName() != null) {
            user.setRole(roleDao.findByName(userUpdateModel.roleName));
        }
        return user;
    }


    public static User convertToUser(UserRegisterModel userRegisterModel) throws Exception {
        User user = new User();
        user.setLogin(userRegisterModel.getLogin());
        user.setEmail(userRegisterModel.getEmail());
        user.setPassword(userRegisterModel.getPassword());
        user.setFirstName(userRegisterModel.getFirstName());
        user.setLastName(userRegisterModel.getLastName());
        user.setBirthday(Date.valueOf(userRegisterModel.getBirthday()));
        return user;
    }
}
