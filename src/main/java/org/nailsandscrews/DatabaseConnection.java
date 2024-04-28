package org.nailsandscrews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

   public static String authenticateUser(String username, String password) {
        openDBSession();
        databaseSession.beginTransaction();

        List resultList = databaseSession.createQuery("from User where username = :username and password = :password")
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();

        databaseSession.getTransaction().commit();
        closeDBSession();

        if (!resultList.isEmpty()) {
            User user = (User) resultList.get(0);
            return user.getType(); // assuming getType() method exists in User class
        }

        return null;
    }

    public static List<Stock> getAllStocks() {
    openDBSession();
    databaseSession.beginTransaction();

    List<Stock> resultList = databaseSession.createQuery("from Stock").getResultList();

    databaseSession.getTransaction().commit();
    closeDBSession();
    return resultList;
    }

    public static List<User> getAllUsers() {
    openDBSession();
    databaseSession.beginTransaction();

    List<User> resultList = databaseSession.createQuery("from User").getResultList();

    databaseSession.getTransaction().commit();
    closeDBSession();
    return resultList;
    }}