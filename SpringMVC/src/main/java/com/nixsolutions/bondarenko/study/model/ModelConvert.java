package com.nixsolutions.bondarenko.study.model;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ModelConvert {
    public static User convertToUser(UserModel userModel, RoleDao roleDao) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        User user = userModel.getUser();

        try {
            user.setBirthday(formatter.parse(userModel.getBirthdayStr()));
        } catch (ParseException e) {
            throw  new RuntimeException("Cant't parse date", e);
        }

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
