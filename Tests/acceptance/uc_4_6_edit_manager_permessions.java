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
    public static void before() throws IOException {
        permissions.add("ADD_PRODUCT");
        guestImp = new GuestImp();
        SUBImp = new SubscriberImp();
        storeRoleImp =  new StoreRoleImp();
        managerImp = new AdminImp();
        managerImp.init_system(false);
        guestImp.login("Admin","Password");
        guestImp.sign_up("manager", "password");
        guestImp.login("manager", "password");
        SUBImp.open_store("Admin","store");
        storeRoleImp.assign_store_manager("Admin","store","manager");
    }

    @Test
    public void success_scenario(){
        assertTrue(storeRoleImp.edit_manager_permissions("Admin","store","manager",permissions));
    }


    @Test
    public void failure_scenario() {
        assertFalse(storeRoleImp.edit_manager_permissions("Admin","store1","moti",permissions));
    }


}
