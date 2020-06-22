package DAL;

import DomainLayer.User.Subscriber;
import Logs.LogErrors;
import com.fasterxml.classmate.AnnotationConfiguration;
//import com.mysql.cj.xdevapi.SessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import java.sql.*;
import java.util.List;

import org.hibernate.cfg.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DBAccess {

    private Connection conn;
    private final SessionFactory sessionFactory = buildSessionFactory();
    //private final EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Session session ;
    private static DBAccess instance= new DBAccess();
    LogErrors my_logError = LogErrors.getLogger();


    /**
     * constructor
     */
    private DBAccess() {
        // Configure the session factory
        //entityManagerFactory= Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        try {
            //entityManager=entityManagerFactory.createEntityManager();
            session = sessionFactory.openSession();
            session.getTransaction().begin();
        }
        catch (Exception e){
            System.out.println("Unable to open session");
        }
    }

    /**
     * builds the session factory
     * @return
     */
    private SessionFactory buildSessionFactory() {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("DAL/resources/hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     *  update the changes in the db
     * @param o
     */
    public boolean updateAndCommit(Object o){
        try {
            if (!(session.getTransaction().isActive())) {
                session.getTransaction().begin();
            }
            session.saveOrUpdate(o);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            my_logError.logger.severe("Failed To Connect To The DataBase!");
            return false;
        }
    }


    public boolean deleteAndCommit(Object o){
        try {
            if (!(session.getTransaction().isActive())) {
                session.getTransaction().begin();
            }
            session.delete(o);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            my_logError.logger.severe("Failed To Connect To The DataBase!");
            return false;
        }
    }


    /**
     * Close the connection
     * @return true if the connection is closed properly
     */
    public boolean closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                closeSession();
                conn.close();
                conn = null;
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public Session getSession() {
        return session;
    }

    public boolean closeSession() {
        if(session != null) {
            session.close();
            return true;
        }
        return false;
    }

    public static DBAccess getInstance() {
        return instance;
    }

    public boolean isActive(){
        if(!session.isConnected()){
            return false;
        }
        return session.getTransaction().isActive();
    }

    public <T> List<T> select(Class<T> c){
        return session.createCriteria(c).list();

    }
}