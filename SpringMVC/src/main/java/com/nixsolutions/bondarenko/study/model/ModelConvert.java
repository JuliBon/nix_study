package com.nixsolutions.bondarenko.study.model;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.User;

import java.sql.Date;

public class ModelConvert {
    public static User convertToUser(UserModel userModel, RoleDao roleDao) throws Exception{
        User user = new User();

        if(!userModel.getId().isEmpty()){
            user.setId(new Long(userModel.getId()));
        }

        user.setLogin(userModel.getLogin());
        user.setEmail(userModel.getEmail());
        user.setPassword(userModel.getPassword());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setBirthday(Date.valueOf(userModel.getBirthday()));
        user.setRole(roleDao.findByName(userModel.roleName));

        return user;
    }
}
