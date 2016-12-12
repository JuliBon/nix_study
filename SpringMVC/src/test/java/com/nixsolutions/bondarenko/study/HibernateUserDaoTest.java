package com.nixsolutions.bondarenko.study;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.sql.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
public class HibernateUserDaoTest {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    private UserDao userDao;

    private static final String dataSetsDir = "test_data/";
    private User testUser = new User("nata", "54321", "nata@mail.ru",
            "nataliya", "bondarenko", Date.valueOf("1991-9-19"), new Role(UserLibraryRole.USER.getId()));


    @Test
    @Commit
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testCreateUserUniqueLoginAndEmain() throws Exception {
        userDao.create(testUser);
    }

    @Test(expected = Exception.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testCreateUserNotUniqueLogin() throws Exception {
        testUser.setLogin("yulya");
        userDao.create(testUser);
    }

    @Test(expected = Exception.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testCreateUserNotUniqueEmail() throws Exception {
        testUser.setEmail("yulya@mail.ru");
        userDao.create(testUser);
    }

    @Test(expected = Exception.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testCreateUserNotExistingRole() throws Exception {
        testUser.setRole(new Role(100L));
        userDao.create(testUser);
    }

    @Test
    public void testFindUserByLogin() throws Exception {
        assertNotNull(userDao.findByLogin("yulya"));
        assertEquals(userDao.findByLogin("nata"), null);
    }

    @Test
    public void testFindUserByEmail() throws Exception {
        assertNotNull(userDao.findByEmail("yulya@mail.ru"));
        assertEquals(userDao.findByEmail("nata@mail.ru"), null);
    }

    @Test
    @Commit
    @ExpectedDatabase(value = "/test_data/UserRemoveExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testRemoveUser() throws Exception {
        User user = userDao.findByLogin("yulya");
        userDao.remove(user);
    }

    @Test(expected = Exception.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testRemoveUserNotExisting() throws Exception {
        testUser.setId(100L);
        userDao.remove(testUser);
    }

    @Test
    @Commit
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testUpdateUser() throws Exception {
        User user = userDao.findByLogin("yulya");
        user.setPassword("9999");
        userDao.update(user);
    }

    @Test(expected = Exception.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testUpdateUserBad() throws Exception {
        User user = userDao.findByLogin("yulya");
        user.setLogin("ivan");
        userDao.update(user);
    }

    @Test
    public void testFindAllUsers() throws Exception {
        List<User> users = userDao.findAll();
        assertNotNull(users);
        assertEquals(users.size(), 2);
    }

    @Test(expected = Exception.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testCreateUserLoginNull() throws Exception {
        testUser.setLogin(null);
        userDao.create(testUser);
    }

    @Test(expected = Exception.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testCreateUsetPasswordNull() throws Exception {
        testUser.setPassword(null);
        userDao.create(testUser);
    }

    @Test(expected = Exception.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testCreateUserEmailNull() throws Exception {
        testUser.setEmail(null);
        userDao.create(testUser);
    }

    @Test(expected = Exception.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testCreateUserRoleNull() throws Exception {
        testUser.setBirthday(null);
        userDao.create(testUser);
    }
}
