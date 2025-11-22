package com.example.JasonShaw.employee.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            // Đọc cấu hình từ hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactory = configuration.buildSessionFactory();
            log.info("Hibernate SessionFactory initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize Hibernate SessionFactory", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            log.info("Hibernate SessionFactory closed");
        }
    }
}