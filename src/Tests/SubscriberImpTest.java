package Tests;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
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
        System system=System.getSystem();



        Store s1=new Store("sotre1");
        Product p1=new Product("bmba",1,2,s1);
        Product p2=new Product("besli",12,2,s1);
        Product p3=new Product("twix",3,2,s1);
        s1.getProduct_list().add(p1);
        s1.getProduct_list().add(p2);
        s1.getProduct_list().add(p3);

        Store s2=new Store("sotre2");
        Product p5=new Product("bmba",11,2,s2);
        Product p4=new Product("chips",3,2,s2);
        Product p6=new Product("twix",5,2,s2);
        s2.getProduct_list().add(p1);
        s2.getProduct_list().add(p2);
        s2.getProduct_list().add(p3);

        Store s3=new Store("sotre3");
        Product p7=new Product("bmba",4,2,s3);
        Product p8=new Product("besli",4,2,s3);
        Product p9=new Product("twix",6,2,s3);
        s3.getProduct_list().add(p1);
        s3.getProduct_list().add(p2);
        s3.getProduct_list().add(p3);

        system.getStore_list().add(s1);
        system.getStore_list().add(s2);
        system.getStore_list().add(s3);
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
        assertEquals(3, SUBImp.search_products("bmba").size());
        assertEquals(2, SUBImp.search_products("besli").size());
        assertEquals(3, SUBImp.search_products("twix").size());
        assertEquals(1, SUBImp.search_products("chips").size());
        assertEquals(0, SUBImp.search_products("bueno").size());
    }

    @Test
    public void save_products() {
        SUBImp.save_products("subscriber","bmba","store1");
        SUBImp.save_products("subscriber","twix","store3");
        SUBImp.save_products("subscriber","chips","store2");
        boolean b1=false,b2=false,b3=false;
        for(ShoppingBag sb:subscriber.getShoppingCart().getShopping_bag_list()) {
            if (sb.getProducts_names().contains("bmba"))
                b1 = true;
            if(sb.getProducts_names().contains("twix"))
                b2=true;
            if(sb.getProducts_names().contains("chips"))
                b3=true;
        }

        assertTrue(b1);
        assertTrue(b2);
        assertTrue(b3);
    }

    @Test
    public void watch_products_in_cart() {
        SUBImp.save_products("subscriber","bmba","store1");
        SUBImp.save_products("subscriber","twix","store3");
        SUBImp.save_products("subscriber","chips","store2");

        assertTrue(SUBImp.watch_products_in_cart("subscriber").contains("bmba"));
        assertTrue(SUBImp.watch_products_in_cart("subscriber").contains("twix"));
        assertTrue(SUBImp.watch_products_in_cart("subscriber").contains("chips"));
        assertFalse(SUBImp.watch_products_in_cart("subscriber").contains("besli"));
    }

    @Test
    public void buy_products_in_cart() {
        assertTrue(SUBImp.buy_products_in_cart("subscriber","mahmoud","1234123412341234","11/26",999,0));
        assertFalse(SUBImp.buy_products_in_cart("subscriber1","mahmoud","1234123412341234","11/26",999,0));
        assertFalse(SUBImp.buy_products_in_cart("subscriber","mahmoud","1234123412341234","11/26",999,2));
        assertFalse(SUBImp.buy_products_in_cart("subscriber","mahmoud","12341234123412341","11/26",999,0));
        assertFalse(SUBImp.buy_products_in_cart("subscriber","mahmoud","1234123412341234","11/261",999,0));
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