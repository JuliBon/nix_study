package com.nixsolutions.bondarenko.study.jsp.dto;

import com.nixsolutions.bondarenko.study.jsp.dao.RoleDao;
import com.nixsolutions.bondarenko.study.jsp.user.library.Role;
import com.nixsolutions.bondarenko.study.jsp.user.library.User;

import java.sql.Date;

public class DtoConvert {
    public static User convertToUser(UserDto userDto, RoleDao roleDao) throws Exception{
        User user = new User();

        if(!userDto.getId().isEmpty()){
            user.setId(new Long(userDto.getId()));
        }

        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthday(Date.valueOf(userDto.getBirthday()));
        user.setRole(roleDao.findByName(userDto.roleName));

        return user;
    }
}
