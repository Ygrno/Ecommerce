package acceptance;
import ServiceLayer.GuestImp;
import ServiceLayer.ManagerImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;


import static org.junit.Assert.*;

public class uc_4_1_add_edit_remove {
    private static SubscriberImp SUBImp;
    private static GuestImp guestImp;
    private static StoreRoleImp storeRoleImp;
    private static ManagerImp managerImp;

    @BeforeClass
    public static void before() throws IOException {
        managerImp = new ManagerImp();
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
    public void success_scenario() {
        assertTrue(storeRoleImp.add_store_product("subscriber", "store1", "Bamba", 3, 25));
        assertTrue(storeRoleImp.edit_store_product("subscriber", "store1", "Bamba","new", 5, 17));
        assertTrue(storeRoleImp.remove_store_product("subscriber","store1","new"));
    }


    @Test
    public void failure_scenario() {
        assertFalse(storeRoleImp.remove_store_product("subscriber","store1","new"));
        assertFalse(storeRoleImp.edit_store_product("subscriber", "store1", "Bamba","new", 5, 17));

    }
}
