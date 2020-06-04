package DataAccessLayer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import java.sql.*;
import java.util.List;

public class DBAccess {

    private Connection conn;
    private final SessionFactory sessionFactory = buildSessionFactory();
    private Session session ;
    private static DBAccess instance= new DBAccess();

    /**
     * constructor
     */
    private DBAccess() {
        // Configure the session factory
        try {
            session = sessionFactory.openSession();
            // User user = new User("master","123","sdf","sdf",111);
            session.getTransaction().begin();
            // session.saveOrUpdate(user);
            session.getTransaction().commit();
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
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
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
        if ( !(session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE))) {
            session.getTransaction().begin();
        }
        session.saveOrUpdate(o);
        session.getTransaction().commit();
    }


    public void deleteAndCommit(Object o){
        if ( !(session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE))) {
            session.getTransaction().begin();
        }
        session.delete(o);
        session.getTransaction().commit();
    }

    public Session getSession() {
        return session;
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