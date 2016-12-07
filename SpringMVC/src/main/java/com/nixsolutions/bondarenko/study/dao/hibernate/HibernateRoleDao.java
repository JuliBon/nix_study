package com.nixsolutions.bondarenko.study.dao.hibernate;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateRoleDao implements RoleDao {
    private final Logger logger;

    private SessionFactory sessionFactory;

    public HibernateRoleDao(SessionFactory sessionFactory) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Role role) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while creating role", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Role role) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while updating role", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(Role role) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while removing role", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Role findByName(String name) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Role.class);
            return (Role) criteria.add(Restrictions.eq("name", name))
                    .uniqueResult();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while searching role", e);
        } finally {
            session.close();
        }
    }
}
