package com.nixsolutions.bondarenko.study;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.dao.hibernate.HibernateUserDao;
import com.nixsolutions.bondarenko.study.user.library.Role;
import com.nixsolutions.bondarenko.study.user.library.User;
import com.nixsolutions.bondarenko.study.user.library.UserLibraryRole;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class HibernateUserDaoTest {
    private static final String dataSetsDir = "test_data/";
    private User testUser = new User("nata", "54321", "nata@mail.ru",
            "nataliya", "bondarenko", Date.valueOf("1991-9-19"), new Role(UserLibraryRole.USER.getId()));

    private UserDao userDao;
    private static IDatabaseTester databaseTester;

    @Before
    public void initialize() throws Exception {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Map<String, Object> properties = sessionFactory.getProperties();
        String JDBC_DRIVER = (String) properties.get("connection.driver_class");
        String JDBC_URL = (String) properties.get("connection.url");
        String USER = (String) properties.get("connection.username");
        String PASSWORD = (String) properties.get("connection.password");

        databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);

        userDao = new HibernateUserDao(sessionFactory);

        importDataSet();
    }


    private void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet("InitialDataSet");
        cleanlyInsert(dataSet);
    }

    private IDataSet readDataSet(String dataSetName) throws Exception {
        return new FlatXmlDataSetBuilder().build(
                ClassLoader.getSystemClassLoader().getResourceAsStream(dataSetsDir + dataSetName + ".xml"));

    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    private void checkActualEqualsToExpected(String expectedDataSetName)
            throws Exception {
        checkActualEqualsToExpected(expectedDataSetName, null);
    }

    private void checkActualEqualsToExpected(String expectedDataSetName,
                                             ITable actualTable) throws Exception {
        if (actualTable == null) {
            actualTable = databaseTester.getConnection().createTable("User");
        }
        IDataSet expectedDataSet = readDataSet(expectedDataSetName);

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
        ITable actualTable = databaseTester.getConnection().createQueryTable("FindUserResult",
                "SELECT login, password, email, firstName, lastName, birthday, id_role FROM User WHERE login = 'yulya'");
        checkActualEqualsToExpected("UserFindExpectedDataSet", actualTable);

        assertEquals(userDao.findByLogin("nata"), null);
    }

    @Test
    public void testFindUserByEmail() throws Exception {
        assertNotNull(userDao.findByEmail("yulya@mail.ru"));

        ITable actualTable = databaseTester.getConnection().createQueryTable("FindUserResult",
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

        ITable actualTable = databaseTester.getConnection().createQueryTable("FindUserResult",
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
