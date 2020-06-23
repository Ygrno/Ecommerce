package acceptance;

import ServiceLayer.GuestImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;
import ServiceLayer.AdminImp;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class uc_1_1_init_system_test {


    private static GuestImp guestImp;
    private static AdminImp managerImp;
    private static SubscriberImp subscriberImp;




    @BeforeClass
    public static void before() throws IOException {
        //SystemManage_Facade.init_system();
        managerImp = new AdminImp();
        managerImp.init_system(false);
        guestImp = new GuestImp();
        subscriberImp = new SubscriberImp();
        guestImp.sign_up("user", "password");
        guestImp.sign_up("user1", "password1");


    }

    @Test
    public void success_scenario(){
        assertTrue(guestImp.login("user", "password"));
        assertTrue(subscriberImp.sign_out("user1"));
    }


    @Test
    public void failure_scenario() {
        //guestImp.login("user1", "wrong_password");
        assertFalse(guestImp.login("user2","hi"));
    }


}
