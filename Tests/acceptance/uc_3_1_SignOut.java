package acceptance;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import ServiceLayer.GuestImp;
import ServiceLayer.IGuest;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class uc_3_1_SignOut {

    private static SubscriberImp SUBImp;
    private static SystemManage_Facade SYS;
    private static SubscribersManage_Facade SUB;
    private static Subscriber subscriber;

    @BeforeClass
    public static void before(){
        SUBImp= new SubscriberImp();
        SYS= new SystemManage_Facade();
        SYS.init_system();
        SYS.is_initialized();
        SYS.add_subscriber("subscriber","subscriber");
        subscriber= System.getSystem().get_subscriber("subscriber");
        subscriber.setLogged_in(true);
    }

    @Test
    public void success_scenario(){
        assertTrue(SUBImp.sign_out("subscriber"));
        assertFalse(SubscribersManage_Facade.check_if_logged_in("subscriber"));
    }


    @Test
    public void failure_scenario() {
        assertTrue(SubscribersManage_Facade.check_if_logged_in("subscriber"));
    }

}
