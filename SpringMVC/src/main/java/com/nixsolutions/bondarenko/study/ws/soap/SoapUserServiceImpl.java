package com.nixsolutions.bondarenko.study.ws.soap;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;
import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import javax.validation.ValidationException;
import java.util.List;

@WebService(endpointInterface = "com.nixsolutions.bondarenko.study.ws.soap.SoapUserService",
        serviceName = "soapUserService")
public class SoapUserServiceImpl implements SoapUserService {
    @Autowired
    private UserService userService;

    @Override
    public User getUser(Long id) throws UserNotFoundException {
        return userService.getUser(id);
    }

    @Override
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        userService.deleteUser(id);
    }

    @Override
    public Long createUser(User user) throws NotUniqueLoginException, NotUniqueEmailException, ValidationException {
        return userService.createUser(user);
    }

    @Override
    public void updateUser(User user) throws NotUniqueEmailException, ValidationException {
        userService.updateUser(user);
    }
}