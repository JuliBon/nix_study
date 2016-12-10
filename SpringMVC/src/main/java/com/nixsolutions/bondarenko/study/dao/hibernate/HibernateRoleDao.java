package com.nixsolutions.bondarenko.study.dao.hibernate;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class HibernateRoleDao implements RoleDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

    public HibernateRoleDao() {
    }

    @Override
    public void create(Role role) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.save(role);
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
        try (Session session = sessionFactory.openSession()) {
            session.update(role);
        } catch (Exception e) {
            String message = "Error while updating role";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Override
    public void remove(Role role) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.delete(role);
        } catch (Exception e) {
            String message = "Error while removing role";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByName(String name) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.bySimpleNaturalId(Role.class).load(name);
        } catch (Exception e) {
            String message = "Error while searching role";
            logger.error(message, e);
            throw new Exception(message, e);
        }
    }
}
