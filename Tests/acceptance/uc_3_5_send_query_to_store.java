package acceptance;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class uc_3_5_send_query_to_store {
    private  static SubscribersManage_Facade SUB;
    private static SystemManage_Facade SYS;
    private static SubscriberImp SUBImp;
    @BeforeClass
    public static void before() throws Exception {
        SYS = new SystemManage_Facade();
        SUBImp = new SubscriberImp();
        SYS.init_system();
        SYS.is_initialized();
        SYS.add_subscriber("subscriber", "subscriber");
        SUB.subscriber_login_state("subscriber",true);
        SUB.create_store("subscriber","test");
    }

    @Test
    public void success_scenario() throws Exception {
        assertTrue(SUBImp.send_query_to_store("subscriber", "check"));
        assertTrue(SUBImp.send_query_to_store("subscriber", "check2"));
    }


    @Test
    public void failure_scenario() throws Exception {
        assertFalse(SUBImp.send_query_to_store("subscriber1","test"));
    }
}
