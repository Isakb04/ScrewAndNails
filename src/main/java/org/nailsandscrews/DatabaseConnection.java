package org.nailsandscrews;

import org.hibernate.*;
import org.hibernate.cfg.*;

import java.util.List;

public class DatabaseConnection {
    static Session databaseSession = null;
    static SessionFactory sessionFactory = null;
    public static void openDBSession()
    {
        System.out.println("Opening");
        sessionFactory = new Configuration().configure().buildSessionFactory();
        databaseSession = sessionFactory.openSession();
        System.out.println("Opened");
    }
    public static void closeDBSession()
    {
        System.out.println("Closing");
        sessionFactory.close();
        databaseSession.close();
        sessionFactory = null;
        databaseSession = null;
        System.out.println("Closed");
    }

    public static boolean authenticateUser(String username, String password) {
        openDBSession();
        databaseSession.beginTransaction();

        List resultList = databaseSession.createQuery("from User where username = :username and password = :password")
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();

        databaseSession.getTransaction().commit();
        closeDBSession();
        return !resultList.isEmpty();
    }

    public static List<Stock> getAllStocks() {
    openDBSession();
    databaseSession.beginTransaction();

    List<Stock> resultList = databaseSession.createQuery("from Stock").getResultList();

    databaseSession.getTransaction().commit();
    closeDBSession();
    return resultList;
    }


}