package com.auca.library.util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import com.auca.library.model.*;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration conf = new Configuration();
            Properties settings = new Properties();

            settings.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/auca_library_db");
            settings.setProperty(Environment.USER, "root");
            settings.setProperty(Environment.PASS, ""); 
            settings.setProperty(Environment.HBM2DDL_AUTO, "update");
            settings.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.setProperty(Environment.SHOW_SQL, "true");
            settings.setProperty(Environment.FORMAT_SQL, "true");

            conf.setProperties(settings);
            conf.addAnnotatedClass(User.class);
            conf.addAnnotatedClass(Province.class);
            conf.addAnnotatedClass(District.class);
            conf.addAnnotatedClass(Sector.class);
            conf.addAnnotatedClass(Cell.class);
            conf.addAnnotatedClass(Village.class);
            conf.addAnnotatedClass(MembershipRequest.class);
            conf.addAnnotatedClass(MembershipType.class);
            conf.addAnnotatedClass(Member.class);
            conf.addAnnotatedClass(Location.class);
            

            try {
                System.out.println("Attempting to build SessionFactory...");
                sessionFactory = conf.buildSessionFactory();
                System.out.println("SessionFactory built successfully.");
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed.");
                ex.printStackTrace(); // Log full stack trace
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
