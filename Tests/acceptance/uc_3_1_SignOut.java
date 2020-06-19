package acceptance;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class uc_3_1_SignOut {

    private static SystemManage_Facade SYS;
    private static SubscribersManage_Facade SUB;

    @BeforeClass
    public static void before(){
        SYS= new SystemManage_Facade();
        SYS.init_system();
        SYS.is_initialized();
        SYS.add_subscriber("subscriber","subscriber");
        SUB.subscriber_login_state("subscriber",true);
    }

    @Test
    public void success_scenario(){
        assertTrue(SubscribersManage_Facade.check_if_logged_in("subscriber"));
        assertTrue(SUB.signout("subscriber"));
    }


    @Test
    public void failure_scenario() {
        assertTrue(SubscribersManage_Facade.check_if_logged_in("subscriber"));
    }

}
