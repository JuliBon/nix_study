package com.nixsolutions.bondarenko.study.model;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ModelConvert {
    public static User convertToUser(UserModel userModel, RoleDao roleDao) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        User user = userModel.getUser();

        user.setBirthday(formatter.parse(userModel.getBirthdayStr()));

        if (roleDao != null) {
            if (userModel.getRoleName() != null) {
                user.setRole(roleDao.findByName(userModel.getRoleName()));
            }
        }
        return user;
    }

    public static User convertToUser(UserModel userModel) throws ParseException {
        return convertToUser(userModel, null);
    }
}
