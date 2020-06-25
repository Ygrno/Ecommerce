package acceptance;
import ServiceLayer.GuestImp;
import ServiceLayer.AdminImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class uc_4_6_edit_manager_permessions {

    private static GuestImp guestImp;
    private static SubscriberImp SUBImp;
    private static StoreRoleImp storeRoleImp;
    private static ArrayList<String> permissions = new ArrayList<>();
    private static AdminImp managerImp;

    @BeforeClass
    public static void before() throws Exception {
        permissions.add("ADD_PRODUCT");
        guestImp = new GuestImp();
        SUBImp = new SubscriberImp();
        storeRoleImp =  new StoreRoleImp();
        managerImp = new AdminImp();
        managerImp.init_system(false);
        guestImp.login("A1","Password");
        guestImp.sign_up("manager", "password");
        guestImp.login("manager", "password");
        SUBImp.open_store("A1","store");
        storeRoleImp.assign_store_manager("A1","store","manager");
    }

    @Test
    public void success_scenario() throws Exception {
        assertTrue(storeRoleImp.edit_manager_permissions("A1","store","manager",permissions));
    }


    @Test
    public void failure_scenario() throws Exception {
        assertFalse(storeRoleImp.edit_manager_permissions("A1","store1","moti",permissions));
    }


}
