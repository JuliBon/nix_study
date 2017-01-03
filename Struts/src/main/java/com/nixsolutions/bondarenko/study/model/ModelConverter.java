package com.nixsolutions.bondarenko.study.model;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;

import java.sql.Date;

public class ModelConverter {

    public static User convertToUser(UserModel userModel, RoleDao roleDao) {
        User user = userModel.getUser();

        user.setBirthday(Date.valueOf(userModel.getBirthdayStr()));

        if (userModel.getRoleName() == null) {
            userModel.setRoleName(UserLibraryRole.DEFAULT_ROLE.name());
        }
        user.setRole(roleDao.findByName(userModel.getRoleName()));
        return user;
    }
}
