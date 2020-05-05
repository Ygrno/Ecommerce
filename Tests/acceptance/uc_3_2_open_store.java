package acceptance;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class uc_3_2_open_store {

    private static SubscriberImp SUBImp;
    private static SystemManage_Facade SYS;
    private static Subscriber subscriber;
    private static Store store;



    @BeforeClass
    public static void before(){
        SUBImp= new SubscriberImp();
        SYS= new SystemManage_Facade();
        SYS.init_system();
        SYS.is_initialized();
        SYS.add_subscriber("subscriber","subscriber");
        subscriber= System.getSystem().get_subscriber("subscriber");
    }

    @Test
    public void success_scenario(){
        subscriber.setLogged_in(true);
        assertTrue(SUBImp.open_store("subscriber","test"));
        assertTrue(SYS.get_store("test")!=null);
        store=SYS.get_store("test");
        List<Role> role = (List<Role>) store.getRoles();
        StoreOwner storeOwner = new StoreOwner(subscriber, store);
        assertNotNull(role.get(0));
        assertTrue(role.get(0) instanceof StoreOwner);
    }


    @Test
    public void failure_scenario() {
        subscriber.setLogged_in(false);
        assertFalse(SUBImp.open_store("subscriber","test"));
    }
}
