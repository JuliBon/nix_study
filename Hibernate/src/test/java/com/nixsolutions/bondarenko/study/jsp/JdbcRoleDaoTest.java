package com.nixsolutions.bondarenko.study.jsp;

import com.nixsolutions.bondarenko.study.jsp.user.library.DBConnectionPool;
import com.nixsolutions.bondarenko.study.jsp.user.library.dao.RoleDao;
import com.nixsolutions.bondarenko.study.jsp.user.library.dao.jdbc.JdbcRoleDao;
import com.nixsolutions.bondarenko.study.jsp.user.library.PropertySource;
import com.nixsolutions.bondarenko.study.jsp.user.library.Role;
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
import java.sql.SQLException;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yulya Bondarenko
 */
public class JdbcRoleDaoTest {
    private RoleDao roleDao = new JdbcRoleDao();
    private static String dataSetsDir = "src/test/resources/test_data/";

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
        return new FlatXmlDataSet( new File(dataSetsDir + "InitialDataSet" + ".xml"),
                false);
    }

    private void checkRoleActualEqualsToExpected(String expectedDataSetName)
            throws Exception {
        checkRoleActualEqualsToExpected(expectedDataSetName, null);
    }

    private void checkRoleActualEqualsToExpected(String expectedDataSetName,
                                                 ITable actualTable) throws Exception {
        IDataSet databaseDataSet = getConnection().createDataSet();
        if (actualTable == null) {
            actualTable = databaseDataSet.getTable("Role");
        }
        IDataSet expectedDataSet = new FlatXmlDataSet(
                new File(dataSetsDir + expectedDataSetName + ".xml"));

        ITable expectedTable = expectedDataSet.getTable("Role");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    private void checkUserAndRoleActualEqualsToExpected(String expectedDataSetName
    ) throws Exception {
        IDataSet databaseDataSet = getConnection().createDataSet();

        IDataSet expectedDataSet = new FlatXmlDataSet(
                new File(dataSetsDir + expectedDataSetName + ".xml"));

        ITable expectedTableRole = expectedDataSet.getTable("Role");
        ITable expectedTableUser = expectedDataSet.getTable("User");

        ITable actualTableRole = databaseDataSet.getTable("Role");
        ITable actualTableUser = databaseDataSet.getTable("User");
        ITable filteredActualTableUser = DefaultColumnFilter
                .includedColumnsTable(actualTableUser,
                        expectedTableUser.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTableRole, actualTableRole);
        Assertion.assertEquals(expectedTableUser, filteredActualTableUser);
    }

    @Test(expected = SQLException.class)
    public void testCreateRole() throws Exception {
        roleDao.create(new Role(3L, "guest"));
        checkRoleActualEqualsToExpected("RoleCreateExpectedDataSet");

        // try to create role with not unique name
        roleDao.create(new Role(4L, "guest"));
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

    @Test
    public void testRemoveRole() throws Exception {
        roleDao.remove(new Role(2L, "user"));
        checkUserAndRoleActualEqualsToExpected("RoleRemoveExpectedDataSet");
    }

    @Test
    public void testRemoveRoleNotExisting() throws Exception {
        roleDao.remove(new Role(100L, "guest"));
        checkUserAndRoleActualEqualsToExpected("InitialDataSet");
    }

    @Test
    public void testFindExistingRole() throws Exception {
        assertNotNull(roleDao.findByName("admin"));
        ITable actualTable = getConnection().createQueryTable("FindRoleResult",
                "SELECT id, name FROM Role WHERE name = 'admin'");
        checkRoleActualEqualsToExpected("RoleFindExpectedDataSet", actualTable);
    }

    @Test
    public void testFindNotExistingRole() throws Exception {
        assertEquals(roleDao.findByName("guest"), null);

        ITable actualTable = getConnection().createQueryTable("FindRoleResult",
                "SELECT id, name FROM Role WHERE name = 'guest'");
        assertEquals(0, actualTable.getRowCount());
    }
}