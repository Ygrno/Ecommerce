package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class uc_4_1_add_edit_remove {
    private static SubscriberImp SUBImp;
    private static SystemManage_Facade SYS;
    private static Subscriber subscriber;
    private static Store store;


    @BeforeClass
    public static void before() throws IOException {
        SUBImp = new SubscriberImp();
        SYS = new SystemManage_Facade();
        SYS.init_system();
        SYS.is_initialized();
        SYS.add_subscriber("subscriber", "subscriber");
        subscriber = System.getSystem().get_subscriber("subscriber");
        System system=System.getSystem();
        subscriber.setLogged_in(true);
        Store s1=new Store("sotre1");
        system.getStore_list().add(s1);
    }

//    @Test
//    public void success_scenario() {
//        asssertTrue(add_store_product("subscriber", "store1", p1.getName(), p1.getPrice(), p1.getAmount()));
//        add_store_product("subscriber", "store1", p1.getName(), p1.getPrice(), p1.getAmount());
//        edit_store_product("subscriber", "store1", p1.getName(),"new", p1.getPrice(), p1.getAmount());
//        StoreRole store_role = subscriber.get_role_at_store("store1");
//        Product product = store_role.store.getProduct("new");
//        assertNotNull(product);
//    }
//
//
//    @Test
//    public void failure_scenario() {
//        asssertTrue(add_store_product("subscriber", "store1", p1.getName(), p1.getPrice(), p1.getAmount()));
//        Product product = store_role.store.getProduct("new");
//        assertNull(purchase);
//    }
}
