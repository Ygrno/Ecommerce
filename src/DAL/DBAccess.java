package DAL;

import DomainLayer.Product;
import DomainLayer.Roles.StoreManger;
import DomainLayer.User.Subscriber;
import com.fasterxml.classmate.AnnotationConfiguration;
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
            //session.getTransaction().commit();
        }
        catch (Exception e){
            System.out.println("Unable to open session");
        }
//        Product s =new Product("qw",12,0,null);
//        session.saveOrUpdate(s);
//        session.getTransaction().commit();
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
    public void updateAndCommit(Object o){
        if ( !(session.getTransaction().isActive())) {
            session.getTransaction().begin();
        }
        session.saveOrUpdate(o);
//        entityManager.persist(o);
//        entityManager.getTransaction().commit();
        session.getTransaction().commit();
    }


    public void deleteAndCommit(Object o){
        if ( !(session.getTransaction().isActive())) {
            session.getTransaction().begin();
        }
        session.delete(o);
//        entityManager.persist(o);
//        entityManager.getTransaction().commit();
        session.getTransaction().commit();
    }


    /**
     * Close the connection
     * @return true if the connection is closed properly
     */
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                closeSession();
                conn.close();
                conn = null;
            }
        } catch (Exception e) {
        }
    }

    public void closeSession() {
        if(session != null) {
            session.close();
        }
    }

    public static DBAccess getInstance() {
        return instance;
    }


    public <T> List<T> select(Class<T> c){
        return session.createCriteria(c).list();

    }
}