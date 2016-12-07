package com.nixsolutions.bondarenko.study.dao.hibernate;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HibernateUserDao implements UserDao {
    private final Logger logger;

    @Autowired
    private SessionFactory sessionFactory;

    public HibernateUserDao() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void create(User user) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while creating user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(User user) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while updating user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(User user) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while removing user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> findAll() throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<User> list = session.createCriteria(User.class).list();
            session.getTransaction().commit();
            return list;
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while searching users", e);
        } finally {
            session.close();
        }
    }

    @Override
    public User findById(Long id) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("id", id))
                    .uniqueResult();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while searching user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public User findByLogin(String login) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("login", login))
                    .uniqueResult();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while searching user", e);
        } finally {
            session.close();
        }
    }

    @Override
    public User findByEmail(String email) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("email", email))
                    .uniqueResult();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (TransactionException trEx) {
                logger.error("Couldn’t roll back transaction", trEx);
            }
            throw new Exception("Error while searching user", e);
        } finally {
            session.close();
        }
    }
}