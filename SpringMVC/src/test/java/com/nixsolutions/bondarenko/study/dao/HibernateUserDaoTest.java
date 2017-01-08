package com.nixsolutions.bondarenko.study.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = "classpath:database-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class HibernateUserDaoTest {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private UserDao userDao;

    private User testUser;

    public HibernateUserDaoTest() throws ParseException {
        testUser = new User(3L, "nata", "Agent007", "nata@mail.ru",
                "nataliya", "bondarenko", formatter.parse("1991-9-19"), new Role(2L, "USER"));
    }

    //!!! user id after creation must be 3, but actually 5
    @Test
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml")
    public void testCreateUserUniqueLoginAndEmain(){
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserNotUniqueLogin() {
        testUser.setLogin("yulya");
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserNotUniqueEmail(){
        testUser.setEmail("yulya@mail.ru");
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserNotExistingRole() throws Exception {
        testUser.setRole(new Role(100L, null));
        userDao.create(testUser);
    }

    @Test(expected = UserNotFoundException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    public void testFindUserByLogin() {
        assertNotNull(userDao.findByLogin("yulya"));
        userDao.findByLogin("nata");
    }

    @Test(expected = UserNotFoundException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    public void testFindUserByEmail() throws Exception {
        assertNotNull(userDao.findByEmail("yulya@mail.ru"));
        userDao.findByEmail("nata@mail.ru");
    }

    @Test(expected = UserNotFoundException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    public void testFindUserByIdl() {
        userDao.findById(67L);
    }

    @Test
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/UserRemoveExpectedDataSet.xml")
    public void testRemoveUser() {
        User user = userDao.findByLogin("yulya");
        userDao.remove(user);
    }

    @Test(expected = RuntimeException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testRemoveUserNotExisting() throws Exception {
        testUser.setId(100L);
        userDao.remove(testUser);
    }

    @Test
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml")
    public void testUpdateUser() {
        User user = userDao.findByLogin("yulya");
        user.setPassword("Agent007");
        userDao.update(user);
    }

    @Test(expected = RuntimeException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testUpdateUserBad() {
        User user = userDao.findByLogin("yulya");
        user.setLogin("ivan");
        userDao.update(user);
    }

    @Test
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    public void testFindAllUsers() {
        List<User> users = userDao.findAll();
        assertNotNull(users);
        assertEquals(users.size(), 2);
    }

    @Test(expected = RuntimeException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserLoginNull() throws Exception {
        testUser.setLogin(null);
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserPasswordNull() {
        testUser.setPassword(null);
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserEmailNull() {
        testUser.setEmail(null);
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserRoleNull() {
        testUser.setBirthday(null);
        userDao.create(testUser);
    }
}
