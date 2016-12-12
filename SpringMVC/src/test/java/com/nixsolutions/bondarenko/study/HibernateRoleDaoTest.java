package com.nixsolutions.bondarenko.study;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.Role;
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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.exception.ConstraintViolationException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@Transactional
public class HibernateRoleDaoTest {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    private RoleDao roleDao;

    private static final String dataSetsDir = "test_data/";


    private static IDatabaseTester databaseTester;

    @Before
    public void initialize() throws Exception {
        String JDBC_DRIVER = "org.h2.Driver";
        String JDBC_URL = "jdbc:h2:tcp://localhost/./db/db_user_library";
//        String JDBC_URL = "jdbc:h2:mem:db_user_library";
        String USER = "sa";
        String PASSWORD = "";

        databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);

        importDataSet();
    }


    private void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet("InitialDataSet");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    private IDataSet readDataSet(String dataSetName) throws Exception {
        return new FlatXmlDataSetBuilder().build(
                ClassLoader.getSystemClassLoader().getResourceAsStream(dataSetsDir + dataSetName + ".xml"));

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

    @Test(expected = ConstraintViolationException.class)
    public void testCreateRoleNotUnique() throws Exception {
        roleDao.create(new Role(4L, "ADMIN"));
    }


    @Test
    public void testUpdateRole() throws Exception {
        roleDao.update(new Role(1L, "system-admin"));
        checkUserAndRoleActualEqualsToExpected("RoleUpdateExpectedDataSet");
    }

    @Test
    public void testUpdateRoleNotExisting() throws Exception {
        roleDao.update(new Role(100L, "system-admin"));
        checkUserAndRoleActualEqualsToExpected("InitialDataSet");
    }

    @Test(expected = Exception.class)
    public void testRemoveRole() throws Exception {
        roleDao.remove(new Role(2L, "USER"));
        checkUserAndRoleActualEqualsToExpected("InitialDataSet");
    }

    @Test
    public void testRemoveRoleNotExisting() throws Exception {
        roleDao.remove(new Role(100L, "guest"));
        checkUserAndRoleActualEqualsToExpected("InitialDataSet");
    }

    @Test
    public void testFindExistingRole() throws Exception {
        assertNotNull(roleDao.findByName("ADMIN"));
        ITable actualTable = databaseTester.getConnection().createQueryTable("FindRoleResult",
                "SELECT id, name FROM Role WHERE name = 'ADMIN'");
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
