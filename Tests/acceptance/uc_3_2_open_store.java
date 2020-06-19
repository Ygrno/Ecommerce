package acceptance;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Roles.StoreOwner;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class uc_3_2_open_store {

    private static SystemManage_Facade SYS;
    private static SubscribersManage_Facade SUB;

    @BeforeClass
    public static void before(){
        SYS= new SystemManage_Facade();
        SYS.init_system();
        SYS.is_initialized();
        SYS.add_subscriber("subscriber","subscriber");
    }

    @Test
    public void success_scenario(){
        SUB.subscriber_login_state("subscriber",true);
        assertTrue(SUB.openstore("subscriber","test"));
        assertTrue(SYS.get_store("test")!=null);
        SubscribersManage_Facade.StoreOwner("subscriber","test");
        assertNotNull(SUB.GetRoles("test").get(0));
        assertTrue(SUB.GetRoles("test").get(0) instanceof StoreOwner);
    }


    @Test
    public void failure_scenario() {
        SUB.subscriber_login_state("subscriber",false);
        assertFalse(SUB.openstore("subscriber","test"));
    }
}
