package UnitTests;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.Product;
import DomainLayer.Roles.StoreManger;
import DomainLayer.Roles.StoreRole;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SubscribersManage_FacadeTest {

    private static System system;

    @Before
    public void setUp(){
        system = System.getSystem();
    }

    @After
    public void cleanSystem(){
        system.clearSystem();
    }

    @Test
    public void subscriber_login_state() {
        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);
        SubscribersManage_Facade.subscriber_login_state("hello",true);
        assertTrue(a.isLogged_in());
        SubscribersManage_Facade.subscriber_login_state("hello",false);
        assertFalse(a.isLogged_in());
    }

    @Test
    public void check_if_logged_in() {
        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);
        assertFalse(SubscribersManage_Facade.check_if_logged_in("hello"));
    }

    @Test
    public void create_store() {

        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);

        SubscribersManage_Facade.create_store("hello","Banana");

        assertNotNull(system.get_store("Banana"));
        assertNotNull(a.get_role_at_store("Banana"));
    }

    @Test
    public void add_product_to_store() {

        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);
        SubscribersManage_Facade.create_store("hello","Banana");
        Store s = system.get_store("Banana");
        Product p = new Product("apple",15,1,s);
        SubscribersManage_Facade.add_product_to_store("hello","Banana","apple",15,1);

        assertEquals(p.getName(), s.getProduct("apple").getName());
        assertEquals(p.getPrice(), s.getProduct("apple").getPrice());
        assertEquals(p.getAmount(), s.getProduct("apple").getAmount());

    }

    @Test
    public void change_product_in_store() {

        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);
        SubscribersManage_Facade.create_store("hello","Banana");
        SubscribersManage_Facade.add_product_to_store("hello","Banana","apple",15,1);
        SubscribersManage_Facade.change_product_in_store("hello","Banana","apple","banana",20,1);
        Store s = system.get_store("Banana");

        assertEquals("Banana", s.getProduct("banana").getName());
        assertEquals(20, s.getProduct("banana").getPrice());
        assertEquals(1, s.getProduct("banana").getAmount());
    }

    @Test
    public void remove_product_in_store() {
        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);
        SubscribersManage_Facade.create_store("hello","Banana");
        SubscribersManage_Facade.add_product_to_store("hello","Banana","apple",15,1);
        Store s = system.get_store("Banana");
        SubscribersManage_Facade.remove_product_in_store("hello","Banana","apple");
        Product p = s.getProduct("apple");
        assertNull(p);
    }

    @Test
    public void add_owner_to_store() {


        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);
        Subscriber b = new Subscriber("bye","123");
        system.add_subscriber(b);
        SubscribersManage_Facade.create_store("hello","Banana");
        SubscribersManage_Facade.add_owner_to_store("hello","Banana","bye");

        Store s = system.get_store("Banana");
        assertNotNull(s.find_store_owner_by_name("bye"));

    }

    @Test
    public void remove_owner_from_store() {

        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);
        Subscriber b = new Subscriber("bye","123");
        system.add_subscriber(b);
        SubscribersManage_Facade.create_store("hello","Banana");
        SubscribersManage_Facade.add_owner_to_store("hello","Banana","bye");
        SubscribersManage_Facade.remove_owner_from_store("hello","Banana","bye");
        Store s = system.get_store("Banana");
        assertNull(s.find_store_owner_by_name("bye"));

    }

    @Test
    public void add_manager_to_store() {

        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);
        Subscriber b = new Subscriber("bye","123");
        system.add_subscriber(b);

        SubscribersManage_Facade.create_store("hello","Banana");
        SubscribersManage_Facade.add_manager_to_store("hello","Banana","bye");

        Store s = system.get_store("Banana");
        assertNotNull(s.find_store_manager_by_name("bye"));



    }

    @Test
    public void remove_manager_from_store() {

        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);
        Subscriber b = new Subscriber("bye","123");
        system.add_subscriber(b);

        SubscribersManage_Facade.create_store("hello","Banana");
        SubscribersManage_Facade.add_manager_to_store("hello","Banana","bye");
        SubscribersManage_Facade.remove_manager_from_store("hello","Banana","bye");

        Store s = system.get_store("Banana");
        assertNull(s.find_store_manager_by_name("bye"));


    }

    @Test
    public void change_permissions_of_manager() {
        Subscriber a = new Subscriber("hello","123");
        system.add_subscriber(a);
        Subscriber b = new Subscriber("bye","123");
        system.add_subscriber(b);

        SubscribersManage_Facade.create_store("hello","Banana");
        SubscribersManage_Facade.add_manager_to_store("hello","Banana","bye");

        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("ADD_PRODUCT");
        SubscribersManage_Facade.change_permissions_of_manager("hello","Banana","bye",permissions);

        assertTrue(((StoreManger)b.get_role_at_store("Banana")).havePermission("ADD_PRODUCT"));




    }

    @Test
    public void store_purchase_history() {




    }
}