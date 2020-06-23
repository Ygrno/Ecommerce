package acceptance;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class uc_3_1_SignOut {

    private static SystemManage_Facade SYS;
    private static SubscribersManage_Facade SUB;
    private static SubscriberImp Subimp;

    @BeforeClass
    public static void before() throws Exception {
        SYS= new SystemManage_Facade();
        Subimp = new SubscriberImp();
        SYS.init_system();
        SYS.is_initialized();
        SYS.add_subscriber("subscriber","subscriber");
        SUB.subscriber_login_state("subscriber",true);
    }

    @Test
    public void success_scenario() throws Exception {
        SUB.subscriber_login_state("subscriber",true);
        assertTrue(SubscribersManage_Facade.check_if_logged_in("subscriber"));
        assertTrue(Subimp.sign_out("subscriber"));
        assertFalse(Subimp.sign_out("subscriber"));
    }


    @Test
    public void failure_scenario() throws Exception {
        assertTrue(Subimp.sign_out("subscriber"));
        assertFalse(SubscribersManage_Facade.check_if_logged_in("subscriber"));
        assertFalse(Subimp.sign_out("subscriber"));
    }

}
