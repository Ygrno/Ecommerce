package Tests;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import DomainLayer.User.User;
import ServiceLayer.ISubscriber;
import ServiceLayer.SubscriberImp;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class SubscriberImpTest {
    private static SubscriberImp SUBImp;
    private static SystemManage_Facade SYS;
    private static SubscribersManage_Facade SUB;
    private static Subscriber subscriber;
    private static Store store;
    @BeforeClass
    public static void init_func(){
        SUBImp= new SubscriberImp();
        SUB= new SubscribersManage_Facade();
        SYS= new SystemManage_Facade();
        SYS.init_system();
        SYS.is_initialized();
        SYS.add_subscriber("subscriber","subscriber");
        subscriber= System.getSystem().get_subscriber("subscriber");
        subscriber.setLogged_in(true);
    }
    @After
    public void login_true(){
        subscriber.setLogged_in(true);
    }

    @Test
    public void view_products_information_store() {
    }

    @Test
    public void search_products() {
    }

    @Test
    public void save_products() {
    }

    @Test
    public void watch_products_in_cart() {
    }

    @Test
    public void buy_products_in_cart() {
    }

    @Test
    public void sign_out() {//3.1
        assertTrue(SUBImp.sign_out("subscriber"));//test if the signout works as expected
        assertFalse(SubscribersManage_Facade.check_if_logged_in("subscriber"));//check the status of the user after signing out, if he is not logged in then he is a guest
    }

    @Test
    public void open_store() { //3.2
        assertTrue(SUBImp.open_store("subscriber","test"));//test if the store func works as expected
        assertTrue(SYS.get_store("test")!=null);//test if the store is actually added to the database
        store=SYS.get_store("test");
        List<Role> role = (List<Role>) store.getRoles();
        StoreOwner storeOwner = new StoreOwner(subscriber, store);
        assertNotNull(role.get(0));//test if the role is actually added to the database
        assertTrue(role.get(0) instanceof StoreOwner);//test if the role is type of storeowner
    }

    @Test
    public void write_review() {
    }

    @Test
    public void rank_product() {
    }

    @Test
    public void rank_store() {
    }

    @Test
    public void send_query_to_store() {
        store=SYS.get_store("test");
        SUBImp.send_query_to_store("subscriber","test");
        List<String> query = (List<String>) subscriber.getQuries();
        assertNotNull(query);
        PrintStream myout =  new PrintStream(new FileOutputStream(FileDescriptor.out));
        myout.print(query);
        assertTrue(query.get(0)=="test");//test if the store is actually added to the database
    }


    @Test
    public void fill_complaint() {
    }

    @Test
    public void view_purchase_history() {
        store=SYS.get_store("test");
        List<String> strings = new ArrayList<String>();
        PurchaseProcess purchaseProcess = new PurchaseProcess(subscriber,store,new ShoppingBag(strings));
        subscriber.getPurchaseProcesslist().add(purchaseProcess);
        List<PurchaseProcess> purchase = SUBImp.view_purchase_history("subscriber");
        assertNotNull(purchase);//the purchase added successfully
        assertEquals(purchase.get(0),purchaseProcess);
    }

    @Test
    public void edit_account() {
    }
}