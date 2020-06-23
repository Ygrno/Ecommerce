package acceptance;

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
    public static void before() throws Exception {
        managerImp = new ManagerImp();
        managerImp.init_system(false);
        admin = new GuestImp();
        SUBImp = new SubscriberImp();
        guestImp = new GuestImp();
    }

    @Test
    public void success_scenario() throws Exception {
        //admin.login("Admin", "Password");
        managerImp = new ManagerImp();
        managerImp.init_system(false);
        guestImp.sign_up("user1", "pass");
        assertTrue(guestImp.login("user1", "pass"));
        SUBImp.sign_out("user1");
        assertTrue(guestImp.login("user1", "pass"));
    }


    @Test
    public void failure_scenario() throws Exception {
        assertFalse(guestImp.login("user1", "passWrong"));
    }
}
