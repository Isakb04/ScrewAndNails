package org.nailsandscrews;

import org.hibernate.*;
import org.hibernate.cfg.*;

import java.util.List;

public class DatabaseConnection {
    static Session databaseSession = null;
    static SessionFactory sessionFactory = null;

    public static void openDBSession() {
        System.out.println("Opening");
        sessionFactory = new Configuration().configure().buildSessionFactory();
        databaseSession = sessionFactory.openSession();
        System.out.println("Opened");
    }

    public static void closeDBSession() {
        System.out.println("Closing");
        sessionFactory.close();
        databaseSession.close();
        sessionFactory = null;
        databaseSession = null;
        System.out.println("Closed");
    }

    public static String authenticateUser(String username, String password) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            List<User> resultList = session.createQuery("from User where username = :username and password = :password", User.class).setParameter("username", username).setParameter("password", password).getResultList();

            session.getTransaction().commit();

            if (!resultList.isEmpty()) {
                return resultList.get(0).getType();
            }
        }

        return null;
    }

    public static List<Stock> getAllStocks() {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            List<Stock> resultList = session.createQuery("from Stock", Stock.class).getResultList();

            session.getTransaction().commit();

            return resultList;
        }
    }

    public static List<User> getAllUsers() {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            List<User> resultList = session.createQuery("from User", User.class).getResultList();

            session.getTransaction().commit();

            return resultList;
        }
    }

    public static void setMock(DatabaseConnection mockDatabaseConnection) {
        databaseSession = mockDatabaseConnection.databaseSession;
        sessionFactory = mockDatabaseConnection.sessionFactory;
    }
}