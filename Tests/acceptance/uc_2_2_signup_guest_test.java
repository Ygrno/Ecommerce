package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import ServiceLayer.GuestImp;
import ServiceLayer.ManagerImp;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;



public class uc_2_2_signup_guest_test {


    private static GuestImp guestImp ;
    private static GuestImp guestImp2 ;
    private static ManagerImp managerImp;

    @BeforeClass
    public static void before() throws IOException {
        guestImp = new GuestImp();
        guestImp2 = new GuestImp();
        managerImp = new ManagerImp();
        managerImp.init_system(false);
        guestImp.login("Admin","Password"); //initiate the system
        guestImp.sign_up("manager", "password");
        guestImp2.sign_up("user2", "password");
        guestImp2.sign_up("user2", "another_password");
        guestImp2.login("user2", "password");

    }


    @Test
    public void success_scenario(){
        assertTrue(SystemManage_Facade.find_subscriber("manager"));
        assertTrue(SystemManage_Facade.find_subscriber("user2"));
    }


    @Test
    public void failure_scenario(){
        assertFalse (SystemManage_Facade.find_subscriber("user2")==false);
    }
}
