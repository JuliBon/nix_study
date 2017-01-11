package com.nixsolutions.bondarenko.study.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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

@ContextConfiguration(locations = "classpath:application-context-db-in-mem-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})

@DatabaseSetup(value = "classpath:/test_data/InitialDataSet.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL)
public class HibernateUserDaoTest {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private UserDao userDao;

    private User testUser;

    public HibernateUserDaoTest() throws ParseException {
        testUser = new User(3L, "nata", "Agent007", "nata@mail.ru",
                "nataliya", "bondarenko", formatter.parse("1991-9-19"), new Role(2L, "USER"));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testCreateUserUniqueLoginAndEmain(){
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserNotUniqueLogin() {
        testUser.setLogin("yulya");
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserNotUniqueEmail(){
        testUser.setEmail("yulya@mail.ru");
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserNotExistingRole() throws Exception {
        testUser.setRole(new Role(100L, null));
        userDao.create(testUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindUserByLogin() {
        assertNotNull(userDao.findByLogin("yulya"));
        userDao.findByLogin("nata");
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindUserByEmail() throws Exception {
        assertNotNull(userDao.findByEmail("yulya@mail.ru"));
        userDao.findByEmail("nata@mail.ru");
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindUserByIdl() {
        userDao.findById(67L);
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserRemoveExpectedDataSet.xml")
    public void testRemoveUser() {
        User user = userDao.findByLogin("yulya");
        userDao.remove(user);
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testRemoveUserNotExisting() throws Exception {
        testUser.setId(100L);
        userDao.remove(testUser);
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml")
    public void testUpdateUser() {
        User user = userDao.findByLogin("yulya");
        user.setPassword("Agent007");
        userDao.update(user);
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testUpdateUserBad() {
        User user = userDao.findByLogin("yulya");
        user.setLogin("ivan");
        userDao.update(user);
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = userDao.findAll();
        assertNotNull(users);
        assertEquals(users.size(), 2);
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserLoginNull() throws Exception {
        testUser.setLogin(null);
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserPasswordNull() {
        testUser.setPassword(null);
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserEmailNull() {
        testUser.setEmail(null);
        userDao.create(testUser);
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testCreateUserRoleNull() {
        testUser.setBirthday(null);
        userDao.create(testUser);
    }
}
