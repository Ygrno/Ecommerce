package acceptance;


import DomainLayer.InternalService.SystemManage_Facade;
import ServiceLayer.GuestImp;
import ServiceLayer.ManagerImp;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

public class uc_2_3_Test {

    private static GuestImp guestimp;
    private static GuestImp admin ;
    private static ManagerImp managerImp;
    private static GuestImp guestImp;

    @Before
    public void setUp() throws IOException {
        guestimp=new GuestImp();
//        admin=new GuestImp();
//        admin.login("Admin", "Password");

        managerImp = new ManagerImp();
        managerImp.init_system(false);
        assertTrue(SystemManage_Facade.is_initialized());
        guestImp.sign_up("user1", "pass");
    }

    @Test
    public void successScenario(){
        guestimp.sign_up("user","123");
        assertTrue(guestimp.login("user","123"));
    }
    @Test
    public void failScenario1(){
        guestimp.sign_up("user","123");
        assertFalse(guestimp.login("mahmo", "123"));
    }
    @Test
    public void failScenario2(){
        guestimp.sign_up("user","123");
        assertFalse(guestimp.login("user", "1234"));
    }

}
