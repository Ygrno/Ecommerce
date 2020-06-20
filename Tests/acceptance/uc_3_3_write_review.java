
package acceptance;
        import ServiceLayer.GuestImp;
        import ServiceLayer.StoreRoleImp;
        import ServiceLayer.SubscriberImp;
        import org.junit.BeforeClass;
        import org.junit.Test;

        import java.io.IOException;


        import static org.junit.Assert.*;

public class uc_3_3_write_review {
    private static SubscriberImp SUBImp;
    private static GuestImp guestImp;
    private static StoreRoleImp storeRoleImp;

    @BeforeClass
    public static void before() throws IOException {
        SUBImp = new SubscriberImp();
        guestImp = new GuestImp();
        storeRoleImp = new StoreRoleImp();
        guestImp.login("Admin","Password");
        guestImp.sign_up("subscriber", "subscriber");
        guestImp.login("subscriber","subscriber");
        SUBImp.open_store("subscriber","store1");
        storeRoleImp.add_store_product("subscriber", "store1", "Bamba", 3, 25);
        storeRoleImp.add_store_product("subscriber", "store1", "chips", 3, 25);
        guestImp.sign_up("buyer","buyer");
        guestImp.login("buyer","buyer");
        SUBImp.save_products("buyer","bamba","store1",5);
    }

    @Test
    public void success_scenario() {
        assertTrue(SUBImp.write_review("buyer","bamba","store1","good product",3));

    }


    @Test
    public void failure_scenario() {
        assertFalse(SUBImp.write_review("buyer","chips","store1","good product",3));


    }
}


