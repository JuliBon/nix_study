package com.nixsolutions.bondarenko.study;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.hibernate.exception.ConstraintViolationException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})

@DatabaseSetup("/test_data/InitialDataSet.xml")
public class HibernateRoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    @ExpectedDatabase("/test_data/RoleCreateExpectedDataSet.xml")
    public void testCreateRole() throws Exception {
        roleDao.create(new Role(3L, "GUEST"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateRoleNotUnique() throws Exception {
        roleDao.create(new Role(4L, "ADMIN"));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/RoleUpdateExpectedDataSet.xml")
    public void testUpdateRole() throws Exception {
        roleDao.update(new Role(1L, "system-admin"));
    }

    @Test(expected = Exception.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testUpdateRoleNotExisting() throws Exception {
        roleDao.update(new Role(100L, "system-admin"));
    }

    @Test(expected = Exception.class)
    @ExpectedDatabase("/test_data/InitialDataSet.xml")
    public void testRemoveRole() throws Exception {
        roleDao.remove(new Role(2L, "USER"));
    }

    @Test(expected = Exception.class)
    @ExpectedDatabase("/test_data/InitialDataSet.xml")
    public void testRemoveRoleNotExisting() throws Exception {
        roleDao.remove(new Role(100L, "GUEST"));
    }

    @Test
    public void testFindExistingRole() throws Exception {
        assertNotNull(roleDao.findByName("ADMIN"));
    }

    @Test
    public void testFindNotExistingRole() throws Exception {
        assertNull(roleDao.findByName("GUEST"));
    }
}
