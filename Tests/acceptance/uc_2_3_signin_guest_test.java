package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import ServiceLayer.GuestImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class uc_2_3_signin_guest_test {

    private static GuestImp admin ;
    private static GuestImp guestImp ;
    private static SubscriberImp SUBImp;


    @BeforeClass
    public static void before() throws IOException {
        admin = new GuestImp();
        SUBImp = new SubscriberImp();
        guestImp = new GuestImp();
    }

    @Test
    public void success_scenario(){
        admin.login("Admin", "Password");
        assert SystemManage_Facade.is_initialized();
        assertTrue(SystemManage_Facade.find_subscriber("Admin"));
        guestImp.sign_up("user1", "pass");
        guestImp.login("user1", "pass");
        assertTrue(SystemManage_Facade.find_subscriber("user1"));
    }


    @Test
    public void failure_scenario(){
        admin.login("Admin", "wrong_password");
        assert !SystemManage_Facade.is_initialized();
    }
}
