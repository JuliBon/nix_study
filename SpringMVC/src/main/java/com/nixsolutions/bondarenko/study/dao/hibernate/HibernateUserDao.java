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
        User userById = sessionFactory.getCurrentSession().byId(User.class).load(id);
        if (userById != null) {
            return userById;
        }
        throw new UserNotFoundException("User with id " + id + "not found");
    }

    @Override
    public User findByLogin(String login) throws Exception {
        try {
            User userByLogin = (User) sessionFactory.getCurrentSession().createCriteria(User.class)
                    .add(Restrictions.naturalId()
                            .set("login", login)
                    ).uniqueResult();
            return userByLogin;
        } catch (Exception e) {
            String message = "Error while searching user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Override
    public User findByEmail(String email) throws Exception {
        try {
            return (User) sessionFactory.getCurrentSession().createCriteria(User.class)
                    .add(Restrictions.naturalId()
                            .set("email", email)
                    ).uniqueResult();
        } catch (Exception e) {
            String message = "Error while searching user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

}