package integration.tests;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.Permission;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SystemManage_FacadeTest {


    static SystemManage_Facade facade;
    static System system;

    @BeforeClass
    public static void setUp() throws Exception {
        SystemManage_Facade.init_system();
        system = System.getSystem();
    }


    @Test
    public void addGuest() {
        Guest g1=SystemManage_Facade.addGuest();
        assertTrue(system.getGuest_list().contains(g1));
        assertEquals(g1.getId(), system.getNextGuestId()-1);
        Guest g2=SystemManage_Facade.addGuest();
        assertTrue(system.getGuest_list().contains(g2));
        assertEquals(g2.getId(), system.getNextGuestId()-1);
    }

    @Test
    public void add_subscriber() {
       SystemManage_Facade.add_subscriber("mahmoud","123");
        assertNotNull(system.get_subscriber("mahmoud"));

    }

    @Test
    public void view_purchase() throws JSONException {
        Subscriber s1=new Subscriber("mahmoud","123");
        Subscriber s2=new Subscriber("ahmd","123");
        Store st1=new Store("store1");
        ShoppingBag sb=new ShoppingBag(new ArrayList<>());
        PurchaseProcess pp1=new PurchaseProcess(s1,st1,sb);
        PurchaseProcess pp2=new PurchaseProcess(s1,st1,sb);
        s1.getPurchaseProcesslist().add(pp1);
        s2.getPurchaseProcesslist().add(pp2);
        system.getUser_list().add(s1);
        system.getUser_list().add(s2);
        assertTrue(SystemManage_Facade.View_purchase("mahmoud").contains(pp1));
        assertTrue(SystemManage_Facade.View_purchase("ahmd").contains(pp2));
    }

    @Test
    public void add_Query() {
        Subscriber s1=new Subscriber("mahmoud","123");
        system.getUser_list().add(s1);
        SystemManage_Facade.Add_Query("mahmoud","asdf");
        assertTrue(s1.getQueries().contains("asdf"));
    }

    @Test
    public void check_password() {
        Subscriber s1=new Subscriber("mahmoud","123");
        system.getUser_list().add(s1);
        assertTrue(SystemManage_Facade.check_password("mahmoud","123"));
        assertFalse(SystemManage_Facade.check_password("mahmoud","1234"));
    }

    @Test
    public void string_to_permission() {
        String s="VIEW_AND_RESPOND_TO_USERS";
        assertEquals(SystemManage_Facade.string_to_permission(s),Permission.VIEW_AND_RESPOND_TO_USERS);
        String s1="???";
        assertNull(SystemManage_Facade.string_to_permission(s1));
    }

    @Test
    public void get_products_of_store() {
        Store s1=new Store("store1");
        Store s2=new Store("store2");
        s1.setProduct_list(new ArrayList<>());
        s2.setProduct_list(new ArrayList<>());
        system.getStore_list().add(s1);
        system.getStore_list().add(s2);
        Product p1=new Product("bmba",2,14,s1);
        Product p2=new Product("bmba1",2,14,s1);
        Product p3=new Product("bmba2",2,14,s2);
        s1.getProduct_list().add(p1);
        s1.getProduct_list().add(p2);
        s2.getProduct_list().add(p3);

        assertTrue((SystemManage_Facade.get_products_of_store("store1")).length==2);
        assertTrue(SystemManage_Facade.get_products_of_store("store1")[0][0].equals("bmba"));
        assertTrue(SystemManage_Facade.get_products_of_store("store1")[1][0].equals("bmba1"));
        assertTrue(SystemManage_Facade.get_products_of_store("store2")[0][0].equals("bmba2"));


    }
}