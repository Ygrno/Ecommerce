package AcceptanceTests;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import ServiceLayer.GuestImp;
import ServiceLayer.StoreRoleImp;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StoreRoleImpTest {

     static System system;
     static StoreRoleImp sri;
     static GuestImp gi;



    @BeforeClass
    public static void setUp() throws IOException {
        sri = new StoreRoleImp();
        gi = new GuestImp();
        gi.login("Admin", "Password");
        system =  System.getSystem();
        Store store = new Store("Dominator");
        Subscriber subscriber = system.get_subscriber("Admin");
        StoreOwner storeOwner = new StoreOwner(subscriber,store);
        subscriber.getRole_list().add(storeOwner);
        store.getRoles().add(storeOwner);

        Subscriber no_store = new Subscriber("Roni","123");
        system.add_subscriber(no_store);
        system.getStore_list().add(store);

    }


    @Test
    public void add_store_product() {
        assertTrue (sri.add_store_product("Admin","Dominator","Corsair Keyboard",690,25));
        assertFalse (sri.add_store_product("Admin","Dominator","Corsair Keyboard",690,0));
        assertFalse(sri.add_store_product("admin","Dominator","Corsair Keyboard",690,25));
        assertFalse(sri.add_store_product("Roni","123","Corsair Keyboard",690,25));
    }

    @Test
    public void edit_store_product() {
        assertTrue (sri.add_store_product("Admin","Dominator","Corsair Keyboard",690,25));
        assertTrue (sri.edit_store_product("Admin","Dominator","Corsair Keyboard","LG Mouse",690,25));
        assertFalse (sri.edit_store_product("Admin","Dominator","LG Mouse","Corsair Keyboard",690,0));
        assertFalse(sri.edit_store_product("admin","Dominator","LG Mouse","Corsair Keyboard",690,25));
        assertFalse(sri.edit_store_product("Roni","123","LG Mouse","Corsair Keyboard",690,25));
    }

    @Test
    public void remove_store_product() {
        assertTrue (sri.add_store_product("Admin","Dominator","Corsair Keyboard",690,25));
        assertFalse (sri.remove_store_product("Admin","Dominator","LG mouse"));
        assertTrue (sri.remove_store_product("Admin","Dominator","Corsair Keyboard"));
    }

    @Test
    public void edit_store_policy() {

    }

    @Test
    public void assign_store_owner() {
        assertTrue(sri.assign_store_owner("Admin","Dominator","Roni"));
        assertFalse(sri.assign_store_owner("Admin","Dominator","Dani"));
        assertFalse(sri.assign_store_owner("Admin","Dominator","Roni"));
        assertFalse(sri.assign_store_owner("Roni","Dominator","Admin"));
    }

    @Test
    public void remove_store_owner() {
        assertFalse(sri.remove_store_owner("Admin","Dominator","Dani"));
        assertFalse(sri.remove_store_owner("Roni","Dominator","Admin"));
        assertFalse(sri.remove_store_owner("admin","Dominator","Roni"));
        assertTrue(sri.remove_store_owner("Admin","Dominator","Roni"));

    }

    @Test
    public void assign_store_manager() {

        assertTrue(sri.assign_store_manager("Admin","Dominator","Roni"));
        assertFalse(sri.assign_store_manager("Admin","Dominator","Dani"));
        assertFalse(sri.assign_store_manager("Roni","Dominator","Admin"));
        assertFalse(sri.assign_store_manager("admin","Dominator","Roni"));
        assertTrue(sri.assign_store_manager("Admin","Dominator","Roni"));



    }

    @Test
    public void edit_manager_permissions() {
        assertTrue(sri.assign_store_manager("Admin","Dominator","Roni"));
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("ADD_PRODUCT");
        permissions.add("EDIT_PRODUCT");
        assertTrue(sri.edit_manager_permissions("Admin","Dominator","Roni",permissions));

    }

    @Test
    public void remove_store_manager() {
        assertTrue(sri.assign_store_manager("Admin","Dominator","Roni"));
        assertFalse(sri.remove_store_manager("admin","Dominator","Roni"));
        assertFalse(sri.remove_store_manager("Admin","Dominator","Dani"));
        assertTrue(sri.remove_store_manager("Admin","Dominator","Roni"));
    }

    @Test
    public void close_store() {

    }

    @Test
    public void view_and_respond_to_questions() {
    }

    @Test
    public void watch_store_history() {

    }
}