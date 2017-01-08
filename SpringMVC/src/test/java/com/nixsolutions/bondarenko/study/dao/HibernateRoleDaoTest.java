package com.nixsolutions.bondarenko.study.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.nixsolutions.bondarenko.study.entity.Role;
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

@ContextConfiguration(locations = "classpath:database-config.xml")
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
    public void testCreateRole() {
        roleDao.create(new Role(3L, "GUEST"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateRoleNotUnique() {
        roleDao.create(new Role(4L, "ADMIN"));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/RoleUpdateExpectedDataSet.xml")
    public void testUpdateRole() {
        roleDao.update(new Role(1L, "system-admin"));
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void testUpdateRoleNotExisting() {
        roleDao.update(new Role(100L, "system-admin"));
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase("/test_data/InitialDataSet.xml")
    public void testRemoveRole() {
        roleDao.remove(new Role(2L, "USER"));
    }

    @Test(expected = RuntimeException.class)
    @ExpectedDatabase("/test_data/InitialDataSet.xml")
    public void testRemoveRoleNotExisting() {
        roleDao.remove(new Role(100L, "GUEST"));
    }

    @Test
    public void testFindExistingRole(){
        assertNotNull(roleDao.findByName("ADMIN"));
    }

    @Test
    public void testFindNotExistingRole() {
        assertNull(roleDao.findByName("GUEST"));
    }
}
