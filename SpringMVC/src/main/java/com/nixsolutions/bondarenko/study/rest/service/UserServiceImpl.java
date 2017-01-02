package com.nixsolutions.bondarenko.study.rest.service;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User getUser(String login) {
        return userDao.findByLogin(login);
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
    public Long createUser(User user) {
        return userDao.create(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }
}
