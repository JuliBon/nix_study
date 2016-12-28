package com.nixsolutions.bondarenko.study.dao.hibernate;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HibernateRoleDao implements RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Role role) {
        try {
            sessionFactory.getCurrentSession().save(role);
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (HibernateException e) {
            throw new RuntimeException("Error while creating role", e);
        }
    }

    @Override
    public void update(Role role) {
        try {
            sessionFactory.getCurrentSession().update(role);
        } catch (HibernateException e) {
            throw new RuntimeException("Error while updating role", e);
        }
    }

    @Override
    public void remove(Role role) {
        try {
            sessionFactory.getCurrentSession().delete(role);
        } catch (HibernateException e) {
            throw new RuntimeException("Error while removing role", e);
        }
    }

    @Override
    public Role findByName(String name) {
        try {
            return (Role) sessionFactory.getCurrentSession().createCriteria(Role.class)
                    .add(Restrictions.naturalId()
                            .set("name", name)
                    ).uniqueResult();
        } catch (HibernateException e) {
            throw new RuntimeException("Error while searching role", e);
        }
    }
}
