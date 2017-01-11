package com.nixsolutions.bondarenko.study.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserRole;
import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;
import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private User user1;
    private User user2;
    private User newUser;

    public UserServiceTest() throws ParseException {
        Role roleAdmin = new Role(1L, UserRole.ADMIN.name());
        Role roleUser = new Role(2L, UserRole.USER.name());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        user1 = new User(1L, "yulya", "Pass123", "yulya@mail.ru",
                "yuliya", "bondarenko", formatter.parse("1993-01-10"), roleAdmin);
        user2 = new User(2L, "ivan", "Pass123", "ivan@mail.ru",
                "ivan", "grozniy", formatter.parse("1530-09-03"), roleUser);
        newUser = new User(3L, "nata", "Agent007", "nata@mail.ru",
                "nataliya", "bondarenko", formatter.parse("1991-09-19"), roleUser);
    }

    @Test
    public void getUserByLogin() {
        User user = userService.getUser(user1.getId());
        Assert.assertEquals(user1, user);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByLoginNotExisting() {
        userService.getUser(newUser.getId());
    }

    @Test
    public void getUsers() throws IOException {
        List<User> users = userService.getUsers();
        Assert.assertEquals(users.size(), 2);
        Assert.assertTrue(users.contains(user1));
        Assert.assertTrue(users.contains(user2));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createUser() {
        userService.createUser(newUser);
    }

    @Test(expected = NotUniqueLoginException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueLogin() {
        newUser.setLogin(user1.getLogin());
        userService.createUser(newUser);
    }

    @Test(expected = NotUniqueEmailException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueEmail() {
        newUser.setEmail(user1.getEmail());
        userService.createUser(newUser);
    }
    @Test(expected = NotUniqueEmailException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserNotUniqueEmail() {
        user1.setEmail(user2.getEmail());
        userService.updateUser(user1);
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserRemoveExpectedDataSet.xml")
    public void deleteUser() {
        userService.deleteUser(user1.getId());
    }

    @Test(expected = UserNotFoundException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void deleteUserNotExisting() {
        userService.deleteUser(newUser.getId());
    }

    @Test
    public void verifyUserExistence(){
        Assert.assertTrue(userService.verifyUserExistence(user1.getId()));
        Assert.assertFalse(userService.verifyUserExistence(newUser.getId()));
    }
}
