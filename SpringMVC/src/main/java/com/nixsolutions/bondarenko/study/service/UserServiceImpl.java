package com.nixsolutions.bondarenko.study.service;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;
import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.validation.user.UserCreateValidator;
import com.nixsolutions.bondarenko.study.validation.user.UserUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "com.nixsolutions.bondarenko.study.service.UserService", serviceName = "userService")
@Service("userService")
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userDao.remove(userDao.findById(id));
    }

    @Override
    public void createUser(User user) {
        UserCreateValidator userCreateValidator = new UserCreateValidator(userDao);
        if (!userCreateValidator.isLoginUnique(user)) {
            throw new NotUniqueLoginException();
        }
        if (!userCreateValidator.isEmailUnique(user)) {
            throw new NotUniqueEmailException();
        }
        userDao.create(user);
    }

    @Override
    public void updateUser(User user) {
        if (!new UserUpdateValidator(userDao).isEmailUnique(user)) {
            throw new NotUniqueEmailException();
        }
        userDao.update(user);
    }

    @Override
    public boolean verifyUserExistence(Long id) {
        try {
            return userDao.findById(id) != null;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
}
