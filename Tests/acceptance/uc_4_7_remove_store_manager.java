package acceptance;

import ServiceLayer.GuestImp;
import ServiceLayer.ManagerImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class uc_4_7_remove_store_manager {

    private static GuestImp guestImp;
    private static SubscriberImp SUBImp;
    private static StoreRoleImp storeRoleImp;
    private static ManagerImp managerImp;



    @BeforeClass
    public static void before() throws IOException {
        guestImp = new GuestImp();
        SUBImp = new SubscriberImp();
        storeRoleImp =  new StoreRoleImp();
        managerImp = new ManagerImp();
        managerImp.init_system(false);
        guestImp.login("Admin","Password");
        guestImp.sign_up("manager", "password");
        guestImp.login("manager", "password");
        SUBImp.open_store("Admin","store4");
        storeRoleImp.assign_store_manager("Admin","store4","manager");

    }

    @Test
    public void run_tests(){
        success_scenario();
        failure_scenario();
    }


    public void success_scenario(){
        assertTrue(storeRoleImp.remove_store_manager("Admin","store4","manager"));
    }



    public void failure_scenario() {
        assertFalse(storeRoleImp.remove_store_manager("Admin","store4","manager"));
    }


}
