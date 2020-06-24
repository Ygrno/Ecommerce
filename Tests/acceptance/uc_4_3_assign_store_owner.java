package acceptance;

import ServiceLayer.GuestImp;
import ServiceLayer.AdminImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class uc_4_3_assign_store_owner {

    private static SubscriberImp SUBImp;
    private static GuestImp guestImp;
    private static StoreRoleImp storeRoleImp;
    private static AdminImp managerImp;

    @BeforeClass
    public static void before() throws Exception {
        managerImp = new AdminImp();
        SUBImp = new SubscriberImp();
        guestImp = new GuestImp();
        storeRoleImp = new StoreRoleImp();
        managerImp.init_system(false);
        guestImp.login("Admin","Password");
        guestImp.sign_up("subscriber", "subscriber");
        guestImp.login("subscriber","subscriber");
        SUBImp.open_store("subscriber","store1");
    }

    @Test
    public void a_success_scenario() throws Exception {
        assertTrue(storeRoleImp.assign_store_owner("subscriber","store1","Admin"));
    }


    @Test
    public void b_failure_scenario() throws Exception {
        assertFalse(storeRoleImp.assign_store_owner("subscriber","store1","Admin"));
        assertFalse(storeRoleImp.assign_store_owner("Moshe","store1","Admin"));
    }
}
