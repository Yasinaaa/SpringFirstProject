package com.kzn.itis.db.util;

import org.hibernate.ejb.HibernatePersistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import java.util.HashMap;
import java.util.Map;

public class SessionUtil {

    private static EntityManagerFactory entityManagerFactory;

    private static final ThreadLocal<EntityManager> sessionThreadLocal = new ThreadLocal<EntityManager>();

    private static final ThreadLocal<Integer> currentUserId = new ThreadLocal<Integer>();

    private SessionUtil() {}

    public static EntityManager getSession() {
        if (sessionThreadLocal.get() == null) {
            synchronized (sessionThreadLocal) {
                if (sessionThreadLocal.get() == null) {
                    EntityManager entityManager = getEntityManagerFactory().createEntityManager();
                    sessionThreadLocal.set(entityManager);
                }
            }
        }
        return sessionThreadLocal.get();
    }

    public static void close() {
        if (sessionThreadLocal.get() != null) {
            sessionThreadLocal.get().close();
            sessionThreadLocal.set(null);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return getEntityManagerFactory(new HashMap<String, Object>());
    }

    public static EntityManagerFactory getEntityManagerFactory(Map<String, Object> configOverrides) {
        if (entityManagerFactory == null) {
            synchronized (SessionUtil.class) {
                if (entityManagerFactory == null) {
                    PersistenceProvider persistenceProvider = new HibernatePersistence();
                    entityManagerFactory =
                            persistenceProvider.createEntityManagerFactory("NewPersistenceUnit", configOverrides);
                }
            }
        }
        return entityManagerFactory;
    }

    public static void rememberUser(int userId) {
        currentUserId.set(userId);
    }

    public static Integer getCurrentUserId() {
        return currentUserId.get();
    }
}
