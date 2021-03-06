
package acceptance;
        import ServiceLayer.GuestImp;
        import ServiceLayer.AdminImp;
        import ServiceLayer.StoreRoleImp;
        import ServiceLayer.SubscriberImp;
        import org.junit.BeforeClass;
        import org.junit.Test;


        import static org.junit.Assert.*;

public class uc_3_3_write_review {
    private static SubscriberImp SUBImp;
    private static GuestImp guestImp;
    private static StoreRoleImp storeRoleImp;
    private static AdminImp managerImp;

    @BeforeClass
    public static void before() throws Exception {
        SUBImp = new SubscriberImp();
        guestImp = new GuestImp();
        storeRoleImp = new StoreRoleImp();
        managerImp = new AdminImp();
        managerImp.init_system(false);
        guestImp.login("Admin","Password");
        guestImp.sign_up("subscriber", "subscriber");
        guestImp.login("subscriber","subscriber");
        SUBImp.open_store("subscriber","store1");
        storeRoleImp.add_store_product("subscriber", "store1", "Bamba", 3, 25);
        storeRoleImp.add_store_product("subscriber", "store1", "chips", 3, 25);
        guestImp.sign_up("buyer","buyer");
        guestImp.login("buyer","buyer");
        SUBImp.save_products("buyer","Bamba","store1",5);

        SUBImp.buy_products_in_cart("buyer","Noam","1234567891234567","2/2019",120, "12345567","","","",""); //was missing
    }

    @Test
    public void success_scenario() throws Exception {
        assertTrue(SUBImp.write_review("buyer","Bamba","store1","good product",3));

    }


    @Test
    public void failure_scenario() throws Exception {
        assertFalse(SUBImp.write_review("buyer","chips","store1","good product",3));


    }
}


