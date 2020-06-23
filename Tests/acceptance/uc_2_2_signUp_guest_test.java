package acceptance;

import ServiceLayer.GuestImp;
import ServiceLayer.AdminImp;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class uc_2_2_signUp_guest_test {


    private static GuestImp guestImp ;
    private static GuestImp guestImp2 ;
    private static AdminImp managerImp;

    @BeforeClass
    public static void before() throws IOException {
        guestImp = new GuestImp();
        guestImp2 = new GuestImp();
        managerImp = new AdminImp();
        managerImp.init_system(false);
    }


    @Test
    public void a_success_scenario(){
        assertTrue(guestImp.sign_up("manager", "password"));
        assertTrue(guestImp2.sign_up("user2", "password"));
        //guestImp2.login("user2", "password");
    }


    @Test
    public void b_failure_scenario()
    {
        assertFalse (guestImp2.sign_up("user2", "another_password"));
    }
}
