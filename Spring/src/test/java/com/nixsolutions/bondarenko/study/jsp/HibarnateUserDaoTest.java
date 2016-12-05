package com.nixsolutions.bondarenko.study.jsp;

import com.nixsolutions.bondarenko.study.DBConnectionPool;
import com.nixsolutions.bondarenko.study.PropertySource;
import com.nixsolutions.bondarenko.study.library.*;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.dao.hibernate.HibernateUserDao;
import com.nixsolutions.bondarenko.study.user.library.Role;
import com.nixsolutions.bondarenko.study.user.library.User;
import com.nixsolutions.bondarenko.study.user.library.UserLibraryRole;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Date;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yulya Bondarenko
 */
public class HibarnateUserDaoTest {
    private UserDao userDao = new HibernateUserDao();
    private static String dataSetsDir = "src/test/resources/test_data/";

    private User testUser = new User("nata", "54321", "nata@mail.ru",
            "nataliya", "bondarenko", Date.valueOf("1991-9-19"), new Role(UserLibraryRole.USER.getId()));

    @Before
    public void init() throws Exception {
        IDatabaseConnection connection = getConnection();
        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
        } finally {
            connection.close();
        }
    }

    @After
    public void after() throws Exception {
        IDatabaseConnection connection = getConnection();
        try {
            DatabaseOperation.DELETE_ALL.execute(connection, getDataSet());
        } finally {
            connection.close();
        }
    }

    private IDatabaseConnection getConnection() throws Exception {
        Properties properties = PropertySource.getDbProperties();
        return new DatabaseConnection(
                DBConnectionPool.getInstance(properties).getConnection(true),
                null, true);
    }

    private IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSet(new File(dataSetsDir + "InitialDataSet" + ".xml"),
                false);
    }

    private void checkActualEqualsToExpected(String expectedDataSetName)
            throws Exception {
        checkActualEqualsToExpected(expectedDataSetName, null);
    }

    private void checkActualEqualsToExpected(String expectedDataSetName,
                                             ITable actualTable) throws Exception {
        IDataSet databaseDataSet = getConnection().createDataSet();
        if (actualTable == null) {
            actualTable = databaseDataSet.getTable("User");
        }

        IDataSet expectedDataSet = new FlatXmlDataSet(
                new File(dataSetsDir + expectedDataSetName + ".xml"));

        ITable expectedTable = expectedDataSet.getTable("User");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(
                actualTable, expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testCreateUserUniqueLoginAndEmain() throws Exception {
        User user = new User("nata", "54321", "nata@mail.ru", "nataliya",
                "bondarenko", Date.valueOf("1991-9-19"), new Role(UserLibraryRole.USER.getId()));
        userDao.create(user);

        checkActualEqualsToExpected("UserCreateExpectedDataSet");
    }

    @Test(expected = Exception.class)
    public void testCreateUserNotUniqueLogin() throws Exception {
        testUser.setLogin("yulya");
        userDao.create(testUser);
        checkActualEqualsToExpected("InitialDataSet");
    }

    @Test(expected = Exception.class)
    public void testCreateUserNotUniqueEmail() throws Exception {
        testUser.setEmail("yulya@mail.ru");
        userDao.create(testUser);
        checkActualEqualsToExpected("InitialDataSet");
    }

    @Test(expected = Exception.class)
    public void testCreateUserNotExistingRole() throws Exception {
        testUser.setRole(new Role(100L));
        userDao.create(testUser);
        checkActualEqualsToExpected("InitialDataSet");
    }

    @Test
    public void testFindUserByLogin() throws Exception {
        assertNotNull(userDao.findByLogin("yulya"));
        ITable actualTable = getConnection().createQueryTable("FindUserResult",
                "SELECT login, password, email, firstName, lastName, birthday, id_role FROM User WHERE login = 'yulya'");
        checkActualEqualsToExpected("UserFindExpectedDataSet", actualTable);

        assertEquals(userDao.findByLogin("nata"), null);
    }

    @Test
    public void testFindUserByEmail() throws Exception {
        assertNotNull(userDao.findByEmail("yulya@mail.ru"));

        ITable actualTable = getConnection().createQueryTable("FindUserResult",
                "SELECT login, password, email, firstName, lastName, birthday, id_role FROM User WHERE email = 'yulya@mail.ru'");
        checkActualEqualsToExpected("UserFindExpectedDataSet", actualTable);

        assertEquals(userDao.findByEmail("nata@mail.ru"), null);
    }

    @Test
    public void testRemoveUser() throws Exception {
        User user = userDao.findByLogin("yulya");
        userDao.remove(user);

        checkActualEqualsToExpected("UserRemoveExpectedDataSet");
    }

    @Test(expected = Exception.class)
    public void testRemoveUserNotExisting() throws Exception {
        testUser.setId(100L);
        userDao.remove(testUser);
        checkActualEqualsToExpected("InitialDataSet");
    }

    @Test(expected = Exception.class)
    public void testUpdateUser() throws Exception {
        User user = userDao.findByLogin("yulya");
        user.setPassword("9999");
        userDao.update(user);

        checkActualEqualsToExpected("UserUpdateExpectedDataSet");

        user.setLogin("ivan");
        userDao.update(user);
    }

    @Test
    public void testFindAllUsers() throws Exception {
        assertNotNull(userDao.findAll());

        ITable actualTable = getConnection().createQueryTable("FindUserResult",
                "SELECT login, password, email, firstName, lastName, birthday, id_role FROM User");

        checkActualEqualsToExpected("InitialDataSet", actualTable);
    }

    @Test(expected = Exception.class)
    public void testCreateUserLoginNull() throws Exception {
        testUser.setLogin(null);
        userDao.create(testUser);
        checkActualEqualsToExpected("InitialDataSet");
    }

    @Test(expected = Exception.class)
    public void testCreateUsetPasswordNull() throws Exception {
        testUser.setPassword(null);
        userDao.create(testUser);
        checkActualEqualsToExpected("InitialDataSet");
    }

    @Test(expected = Exception.class)
    public void testCreateUserEmailNull() throws Exception {
        testUser.setEmail(null);
        userDao.create(testUser);
        checkActualEqualsToExpected("InitialDataSet");
    }

    @Test(expected = Exception.class)
    public void testCreateUserRoleNull() throws Exception {
        testUser.setBirthday(null);
        userDao.create(testUser);
        checkActualEqualsToExpected("InitialDataSet");
    }
}