package com.nixsolutions.bondarenko.study;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.dao.hibernate.HibernateRoleDao;
import com.nixsolutions.bondarenko.study.user.library.Role;
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

import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class HibernateRoleDaoTest {
    private static final String dataSetsDir = "test_data/";

    private RoleDao roleDao;
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

        roleDao = new HibernateRoleDao(sessionFactory);

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

    private void checkRoleActualEqualsToExpected(String expectedDataSetName)
            throws Exception {
        checkRoleActualEqualsToExpected(expectedDataSetName, null);
    }

    private void checkRoleActualEqualsToExpected(String expectedDataSetName,
                                                 ITable actualTable) throws Exception {

        if (actualTable == null) {
            actualTable = databaseTester.getConnection().createTable("Role");
        }
        ITable expectedTable = readDataSet(expectedDataSetName).getTable("Role");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    private void checkUserAndRoleActualEqualsToExpected(String expectedDataSetName
    ) throws Exception {
        IDataSet expectedDataSet = readDataSet(expectedDataSetName);
        ITable expectedTableRole = expectedDataSet.getTable("Role");
        ITable expectedTableUser = expectedDataSet.getTable("User");

        ITable actualTableRole = databaseTester.getConnection().createTable("Role");
        ITable actualTableUser = databaseTester.getConnection().createTable("User");
        ITable filteredActualTableUser = DefaultColumnFilter
                .includedColumnsTable(actualTableUser,
                        expectedTableUser.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTableRole, actualTableRole);
        Assertion.assertEquals(expectedTableUser, filteredActualTableUser);
    }

    @Test
    public void testCreateRole() throws Exception {
        roleDao.create(new Role(3L, "guest"));
        checkRoleActualEqualsToExpected("RoleCreateExpectedDataSet");
    }

    @Test  (expected = Exception.class)
    public void testCreateRoleBad() throws Exception {
        // try to create role with not unique name
        roleDao.create(new Role(4L, "admin"));
    }


    @Test
    public void testUpdateRole() throws Exception {
        roleDao.update(new Role(1L, "system-admin"));
        checkUserAndRoleActualEqualsToExpected("RoleUpdateExpectedDataSet");
    }

    @Test (expected = Exception.class)
    public void testUpdateRoleNotExisting() throws Exception {
        roleDao.update(new Role(100L, "system-admin"));
    }

    @Test  (expected = Exception.class)
    public void testRemoveRole() throws Exception {
        roleDao.remove(new Role(2L, "user"));
    }

    @Test  (expected = Exception.class)
    public void testRemoveRoleNotExisting() throws Exception {
        roleDao.remove(new Role(100L, "guest"));
    }

    @Test
    public void testFindExistingRole() throws Exception {
        assertNotNull(roleDao.findByName("admin"));
        ITable actualTable = databaseTester.getConnection().createQueryTable("FindRoleResult",
                "SELECT id, name FROM Role WHERE name = 'admin'");
        checkRoleActualEqualsToExpected("RoleFindExpectedDataSet", actualTable);
    }



    @Test
    public void testFindNotExistingRole() throws Exception {
        assertEquals(roleDao.findByName("guest"), null);

        ITable actualTable = databaseTester.getConnection().createQueryTable("FindRoleResult",
                "SELECT id, name FROM Role WHERE name = 'guest'");
        assertEquals(0, actualTable.getRowCount());
    }
}
