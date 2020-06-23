package acceptance;



import ServiceLayer.GuestImp;
import ServiceLayer.AdminImp;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

public class uc_2_3_Test {

    private static GuestImp admin ;
    private static AdminImp managerImp;
    private static GuestImp guestImp;

    @Before
    public void setUp() throws IOException {
        guestImp=new GuestImp();
//        admin=new GuestImp();
//        admin.login("Admin", "Password");

        managerImp = new AdminImp();
        managerImp.init_system(false);
        //assertTrue(SystemManage_Facade.is_initialized());
        guestImp.sign_up("user1", "pass");
    }

    @Test
    public void successScenario(){
        guestImp.sign_up("user","123");
        assertTrue(guestImp.login("user","123"));
    }
    @Test
    public void failScenario1(){
        guestImp.sign_up("user","123");
        assertFalse(guestImp.login("mahmo", "123"));
    }
    @Test
    public void failScenario2(){
        guestImp.sign_up("user","123");
        assertFalse(guestImp.login("user", "1234"));
    }

}
