package com.nixsolutions.bondarenko.study.dao.hibernate;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HibernateRoleDao implements RoleDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Role role) throws Exception {
        try {
            sessionFactory.getCurrentSession().save(role);
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            String message = "Error while creating role";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Override
    public void update(Role role) throws Exception {
        try {
            sessionFactory.getCurrentSession().update(role);
        } catch (Exception e) {
            String message = "Error while updating role";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Override
    public void remove(Role role) throws Exception {
        try {
            sessionFactory.getCurrentSession().delete(role);
        } catch (Exception e) {
            String message = "Error while removing role";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Override
    public Role findByName(String name) throws Exception {
        try {
            return (Role) sessionFactory.getCurrentSession().createCriteria(Role.class)
                    .add(Restrictions.naturalId()
                            .set("name", name)
                    ).uniqueResult();
        } catch (Exception e) {
            String message = "Error while searching role";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }
}
