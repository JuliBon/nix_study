package com.nixsolutions.bondarenko.study.dao.hibernate;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class HibernateUserDao implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long create(User user) {
        try {
            User createdUser = (User) sessionFactory.getCurrentSession().save(user);
            return createdUser.getId();
        } catch (Exception e) {
            throw new RuntimeException("Error while creating user", e);
        }
    }


    @Override
    public void update(User user) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(user);
        } catch (Exception e) {
            throw new RuntimeException("Error while updating user", e);
        }
    }

    @Override
    public void remove(User user) {
        try {
            sessionFactory.getCurrentSession().delete(user);
        } catch (Exception e) {
            throw new RuntimeException("Error while removing user", e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return sessionFactory.getCurrentSession().createQuery("from User").list();
        } catch (HibernateException e) {
            throw new RuntimeException("Error while searching users", e);
        }
    }

    @Override
    public User findById(Long id) {
        try {
            User userById = sessionFactory.getCurrentSession().byId(User.class).load(id);
            if (userById != null) {
                return userById;
            }
            throw new UserNotFoundException("User with id " + id + " not found");
        } catch (HibernateException e) {
            throw new RuntimeException("Error while searching user", e);
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
            throw new UserNotFoundException("User with login " + login + " not found");
        } catch (HibernateException e) {
            throw new RuntimeException("Error while searching user", e);
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
            throw new UserNotFoundException("User with email " + email + " not found");
        } catch (HibernateException e) {
            throw new RuntimeException("Error while searching user", e);
        }
    }
}