package com.example.JasonShaw.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility class để quản lý Hibernate SessionFactory
 * Singleton pattern đảm bảo chỉ có một SessionFactory trong toàn bộ ứng dụng
 */
@Slf4j
public class ConnectionUtil {

    private static SessionFactory sessionFactory;

    // Private constructor để ngăn khởi tạo từ bên ngoài
    private ConnectionUtil() {
    }

    /**
     * Lấy SessionFactory instance (Lazy Initialization)
     * @return SessionFactory instance
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (ConnectionUtil.class) {
                if (sessionFactory == null) {
                    try {
                        log.info("Initializing Hibernate SessionFactory...");

                        // Đọc cấu hình từ hibernate.cfg.xml
                        Configuration configuration = new Configuration();
                        configuration.configure("hibernate.cfg.xml");

                        // Tạo SessionFactory
                        sessionFactory = configuration.buildSessionFactory();

                        log.info("Hibernate SessionFactory initialized successfully!");

                        // Đăng ký shutdown hook để đóng SessionFactory khi ứng dụng tắt
                        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                            log.info("Closing Hibernate SessionFactory...");
                            closeSessionFactory();
                        }));

                    } catch (Exception e) {
                        log.error("Failed to initialize Hibernate SessionFactory", e);
                        throw new ExceptionInInitializerError(e);
                    }
                }
            }
        }
        return sessionFactory;
    }

    /**
     * Đóng SessionFactory
     */
    public static void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            try {
                sessionFactory.close();
                log.info("Hibernate SessionFactory closed successfully");
            } catch (Exception e) {
                log.error("Error closing SessionFactory", e);
            }
        }
    }

    /**
     * Kiểm tra SessionFactory có đang hoạt động không
     * @return true nếu SessionFactory đang mở
     */
    public static boolean isSessionFactoryOpen() {
        return sessionFactory != null && !sessionFactory.isClosed();
    }
}