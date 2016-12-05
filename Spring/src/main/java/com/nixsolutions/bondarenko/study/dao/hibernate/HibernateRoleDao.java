package com.nixsolutions.bondarenko.study.dao.hibernate;

import com.nixsolutions.bondarenko.study.HibernateUtil;
import com.nixsolutions.bondarenko.study.user.library.Role;
import com.nixsolutions.bondarenko.study.dao.RoleDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class HibernateRoleDao implements RoleDao {
    private SessionFactory sessionFactory;

    public HibernateRoleDao() {
        sessionFactory = HibernateUtil.getSessioFactory();
    }

    @Override
    public void create(Role role)  throws Exception{
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error while creating role", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Role role)  throws Exception{
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error while updating role", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(Role role)  throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error while removing role", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Role findByName(String name) throws Exception{
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Role.class);
            return  (Role) criteria.add(Restrictions.eq("name", name))
                    .uniqueResult();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error while searching role", e);
        } finally {
            session.close();
        }
    }
}
