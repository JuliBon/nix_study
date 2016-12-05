package com.nixsolutions.bondarenko.study.jsp.dao.hibernate;

import com.nixsolutions.bondarenko.study.jsp.user.library.User;
import com.nixsolutions.bondarenko.study.jsp.dao.UserDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;

import java.util.List;


public class HibernateUserDao implements UserDao {
    private SessionFactory sessionFactory;

    public HibernateUserDao() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Override
    public void create(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Error while creating user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(User user){
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Error while updating user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(User user) throws HibernateException {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Error while removing user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<User> list = session.createCriteria(User.class).list();
            session.getTransaction().commit();
            return list;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            throw new HibernateException("Error while searching user user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public User findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("id", id))
                    .uniqueResult();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Error while searching user user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public User findByLogin(String login) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("login", login))
                    .uniqueResult();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Error while searching user user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public User findByEmail(String email) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("email", email))
                    .uniqueResult();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Error while searching user user", e);
        } finally {
            session.close();
        }
    }
}