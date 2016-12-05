package com.nixsolutions.bondarenko.study.jsp.dao.hibernate;

import com.nixsolutions.bondarenko.study.jsp.user.library.Role;
import com.nixsolutions.bondarenko.study.jsp.dao.RoleDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;

public class HibernateRoleDao implements RoleDao {
    private SessionFactory sessionFactory;

    public HibernateRoleDao() {
        sessionFactory = SessionFactoryManager.getInstance().getSessioFactory();
    }

    @Override
    public void create(Role role) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Error while creating role", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Role role) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Error while updating role", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(Role role) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Error while removing role", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Role findByName(String name) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Role.class);
            Role role = (Role) criteria.add(Restrictions.eq("name", name))
                    .uniqueResult();
            return role;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Error while searching role", e);
        } finally {
            session.close();
        }
    }
}
