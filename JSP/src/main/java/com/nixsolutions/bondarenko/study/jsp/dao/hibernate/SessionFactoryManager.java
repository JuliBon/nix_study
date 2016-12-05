package com.nixsolutions.bondarenko.study.jsp.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryManager {
    private static SessionFactoryManager manager;
    private SessionFactory sessionFactory;


    private SessionFactoryManager(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static SessionFactoryManager getInstance(){
        if(manager == null){
            manager = new SessionFactoryManager();
        }
        return manager;
    }

    public SessionFactory getSessioFactory(){
        return sessionFactory;
    }
}
