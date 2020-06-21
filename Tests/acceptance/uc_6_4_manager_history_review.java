
package acceptance;
        import ServiceLayer.GuestImp;
        import ServiceLayer.AdminImp;
        import ServiceLayer.StoreRoleImp;
        import ServiceLayer.SubscriberImp;
        import org.junit.BeforeClass;
        import org.junit.Test;

        import java.io.IOException;


        import static org.junit.Assert.*;

public class uc_6_4_manager_history_review {

    //TODO: Fix This Test

    private static SubscriberImp SUBImp;
    private static GuestImp guestImp;
    private static StoreRoleImp storeRoleImp;
    private static AdminImp managerImp;

    @BeforeClass
    public static void before() throws IOException {
        SUBImp = new SubscriberImp();
        guestImp = new GuestImp();
        storeRoleImp = new StoreRoleImp();
        managerImp = new AdminImp();
        guestImp.login("Admin","Password");
        guestImp.sign_up("subscriber", "subscriber");
        guestImp.login("subscriber","subscriber");
        SUBImp.open_store("subscriber","store1");
        storeRoleImp.add_store_product("subscriber", "store1", "Bamba", 3, 25);
        storeRoleImp.add_store_product("subscriber", "store1", "chips", 3, 25);
        guestImp.sign_up("buyer","buyer");
        guestImp.login("buyer","buyer");
        SUBImp.save_products("buyer","bamba","store1",5);
        guestImp.login("Admin","Password");

    }

    @Test
    public void success_scenario() {
        try {
            assertEquals(managerImp.view_history_store("store1").size(), 0);
            assertEquals(managerImp.view_history_costumer("buyer"), "");
        }
        catch (Exception e){

        }

    }


    @Test
    public void failure_scenario() {
        try {
            assertEquals(managerImp.view_history_store("store2").size(), 0);
            assertEquals(managerImp.view_history_costumer("buyer1").size(),0);

        }catch (Exception e){

        }


    }
}


