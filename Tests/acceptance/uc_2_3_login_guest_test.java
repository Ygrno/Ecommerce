package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import ServiceLayer.GuestImp;
import ServiceLayer.ManagerImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class uc_2_3_login_guest_test {

    private static GuestImp admin ;
    private static GuestImp guestImp ;
    private static SubscriberImp SUBImp;
    private static ManagerImp managerImp;


    @BeforeClass
    public static void before() throws IOException {
        managerImp = new ManagerImp();
        managerImp.init_system(false);
        admin = new GuestImp();
        SUBImp = new SubscriberImp();
        guestImp = new GuestImp();
    }

    @Test
    public void success_scenario(){
        //admin.login("Admin", "Password");
        managerImp = new ManagerImp();
        managerImp.init_system(false);
        assertTrue(SystemManage_Facade.is_initialized());
        guestImp.sign_up("user1", "pass");
        guestImp.login("user1", "pass");
        assertTrue(SystemManage_Facade.find_subscriber("user1"));
        SUBImp.sign_out("user1");
        guestImp.login("user1", "pass");
        assertTrue(SystemManage_Facade.find_subscriber("user1"));
    }


    @Test
    public void failure_scenario(){
        guestImp.sign_up("user1", "pass");
        guestImp.login("user1", "pass");
        SUBImp.sign_out("user1");
        assertTrue(SystemManage_Facade.find_subscriber("user1"));
        assertFalse(guestImp.login("user1", "passWrong"));
    }
}
