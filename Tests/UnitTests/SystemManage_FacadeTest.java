package UnitTests;

import DomainLayer.DealDetails;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class SystemManage_FacadeTest {
    public static System system;

    @Before
    public void setUp(){
        SystemManage_Facade.init_system();
        system = System.getSystem();
    }

    @After
    public void cleanSystem(){
        system.clearSystem();
    }

    @Test
    public void init_system() {
        SystemManage_Facade.init_system();
        assertNotNull(SystemManage_Facade.system);
    }

    @Test
    public void is_initialized() {
        assertTrue(System.initialized);
    }

    @Test
    public void find_store() {

        assertFalse(SystemManage_Facade.find_store("Banana"));
        Store a = new Store("Banana");
        system.getStore_list().add(a);
        assertTrue(SystemManage_Facade.find_store("Banana"));

    }

    @Test
    public void get_store() {

        Store a = new Store("Banana");
        system.getStore_list().add(a);

        Store b = SystemManage_Facade.get_store("Banana");
        assertEquals(a,b);

    }

    @Test
    public void get_stores() {
        Store a = new Store("Banana");
        system.getStore_list().add(a);
        List<Store> stores = SystemManage_Facade.get_stores();
        assertTrue(stores.contains(a));
    }

    @Test
    public void buy() {
        assertTrue(SystemManage_Facade.buy(new DealDetails(32,"roni","123456789123456","22/10",120)));
    }

    @Test
    public void getGuest() {
        Guest a = new Guest(1);
        system.getGuest_list().add(a);
        assertEquals(SystemManage_Facade.getGuest(1),a);
    }

    @Test
    public void addGuest() {
        SystemManage_Facade.addGuest();
        assertNotNull(SystemManage_Facade.getGuest(0));
    }

    @Test
    public void find_subscriber() {

        Subscriber a = new Subscriber("moti","123");
        system.add_subscriber(a);

        assertTrue(SystemManage_Facade.find_subscriber("moti"));

    }

    @Test
    public void get_subscriber() {
        Subscriber a = new Subscriber("moti","123");
        system.add_subscriber(a);

        Subscriber b = SystemManage_Facade.get_subscriber("moti");
        assertEquals(a,b);
    }

    @Test
    public void add_subscriber() {
        SystemManage_Facade.add_subscriber("moti","123");
        Subscriber a = system.get_subscriber("moti");
        assertTrue(system.getUser_list().contains(a));

    }



    @Test
    public void add_Query() {
    }

    @Test
    public void addProductReview() {
    }

    @Test
    public void check_password() {
    }

    @Test
    public void string_to_permission() {
    }

    @Test
    public void strings_to_permissions() {
    }

    @Test
    public void get_products_of_store() {
    }

    @Test
    public void getAllStores() {
    }
}