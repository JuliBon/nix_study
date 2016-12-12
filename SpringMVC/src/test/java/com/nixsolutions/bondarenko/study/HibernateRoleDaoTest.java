package com.nixsolutions.bondarenko.study;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.Role;
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

import org.hibernate.exception.ConstraintViolationException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
public class HibernateRoleDaoTest {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    private RoleDao roleDao;

    @Test
    @Commit
    @ExpectedDatabase(value = "/test_data/RoleCreateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testCreateRole() throws Exception {
        roleDao.create(new Role(3L, "GUEST"));
    }

    @Test(expected = ConstraintViolationException.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    @Rollback
    public void testCreateRoleNotUnique() throws Exception {
        roleDao.create(new Role(4L, "ADMIN"));
    }


    @Test
    @Commit
    @ExpectedDatabase(value = "/test_data/RoleUpdateExpectedDataSet.xml")
    @Rollback
    public void testUpdateRole() throws Exception {
        roleDao.update(new Role(1L, "system-admin"));
    }

    @Test
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    @Rollback
    public void testUpdateRoleNotExisting() throws Exception {
        roleDao.update(new Role(100L, "system-admin"));
    }

    @Test(expected = Exception.class)
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testRemoveRole() throws Exception {
        roleDao.remove(new Role(2L, "USER"));
    }

    @Test
    @Commit
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Rollback
    public void testRemoveRoleNotExisting() throws Exception {
        roleDao.remove(new Role(100L, "GUEST"));
    }

    @Test
    @Commit
    @Rollback
    public void testFindExistingRole() throws Exception {
        assertNotNull(roleDao.findByName("ADMIN"));
    }

    @Test
    @Rollback
    public void testFindNotExistingRole() throws Exception {
        assertEquals(roleDao.findByName("GUEST"), null);
    }
}
