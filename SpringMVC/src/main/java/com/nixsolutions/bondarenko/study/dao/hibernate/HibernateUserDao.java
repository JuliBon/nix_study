package com.nixsolutions.bondarenko.study.dao.hibernate;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class HibernateUserDao implements UserDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

    public HibernateUserDao() {

    }

    @Override
    public void create(User user) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        } catch (Exception e) {
            String message = "Error while creating user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }


    @Override
    public void update(User user) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.saveOrUpdate(user);
        } catch (Exception e) {
            String message = "Error while updating user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Override
    public void remove(User user) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            User userS = session.get(User.class, user.getId());
            session.delete(userS);
        } catch (Exception e) {
            String message = "Error while removing user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User").list();
        } catch (Exception e) {
            String message = "Error while searching users";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.byId(User.class).load(id);
        } catch (Exception e) {
            String message = "Error while searching user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findByLogin(String login) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.bySimpleNaturalId(User.class).load(login);
        } catch (Exception e) {
            String message = "Error while searching user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findByEmail(String email) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from User u where u.email= :email");
            List<User> users = query.setParameter("email", email).list();
            if (!users.isEmpty()) {
                return (User) query.list().get(0);
            }
            return null;
        } catch (Exception e) {
            String message = "Error while searching user";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

}