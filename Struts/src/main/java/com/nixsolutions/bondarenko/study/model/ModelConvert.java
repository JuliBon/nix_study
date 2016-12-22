package com.nixsolutions.bondarenko.study.model;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.User;

import java.sql.Date;

public class ModelConvert {
    public static User convertToUser(UserModel userModel, RoleDao roleDao){
        User user = userModel.getUser();

        user.setBirthday(Date.valueOf(userModel.getBirthdayStr()));

        if (roleDao != null) {
            if (userModel.getRoleName() != null) {
                user.setRole(roleDao.findByName(userModel.getRoleName()));
            }
        }
        return user;
    }

    public static User convertToUser(UserModel userModel) {
        return convertToUser(userModel, null);
    }
}
