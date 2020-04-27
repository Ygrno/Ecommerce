package acceptance;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreOwner;
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

public class uc_3_7_view_purchase_history {

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
    }

    @Test
    public void success_scenario() {
        store=SYS.get_store("test");
        List<String> strings = new ArrayList<String>();
        PurchaseProcess purchaseProcess = new PurchaseProcess(subscriber,store,new ShoppingBag(strings));
        subscriber.getPurchaseProcesslist().add(purchaseProcess);
        List<PurchaseProcess> purchase = SUBImp.view_purchase_history("subscriber");
        assertNotNull(purchase);
        assertEquals(purchase.get(0),purchaseProcess);
    }


    @Test
    public void failure_scenario() {
        subscriber.setLogged_in(false);
        List<PurchaseProcess> purchase = SUBImp.view_purchase_history("subscriber");
        assertNull(purchase);
    }
}
