package com.nixsolutions.bondarenko.study.dao.hibernate;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class HibernateUserDao implements UserDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) throws Exception {
        try {
            sessionFactory.getCurrentSession().save(user);
        } catch (Exception e) {
            String message = "Error while creating user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }


    @Override
    public void update(User user) throws Exception {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(user);
        } catch (Exception e) {
            String message = "Error while updating user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Override
    public void remove(User user) throws Exception {
        try {
            sessionFactory.getCurrentSession().delete(user);
        } catch (Exception e) {
            String message = "Error while removing user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Override
    public List<User> findAll() throws Exception {
        try {
            return sessionFactory.getCurrentSession().createQuery("from User").list();
        } catch (Exception e) {
            String message = "Error while searching users";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Override
    public User findById(Long id) {
        try {
            User userById = sessionFactory.getCurrentSession().byId(User.class).load(id);
            if (userById != null) {
                return userById;
            }
            throw new UserNotFoundException("User with id " + id + "not found");
        }  catch (HibernateException e) {
            logger.error("Error while searching user", e);
            throw e;
        }
    }

    @Override
    public User findByLogin(String login) {
        try {
            User user = (User) sessionFactory.getCurrentSession().createCriteria(User.class)
                    .add(Restrictions.naturalId()
                            .set("login", login)
                    ).uniqueResult();
            if (user != null) {
                return user;
            }
            throw new UserNotFoundException("User with login " + login + "not found");
        } catch (HibernateException e) {
            logger.error("Error while searching user", e);
            throw e;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            User user = (User) sessionFactory.getCurrentSession().createCriteria(User.class)
                    .add(Restrictions.naturalId()
                            .set("email", email)
                    ).uniqueResult();
            if (user != null) {
                return user;
            }
            throw new UserNotFoundException("User with email " + email + "not found");
        } catch (HibernateException e) {
            logger.error("Error while searching user", e);
            throw e;
        }
    }
}