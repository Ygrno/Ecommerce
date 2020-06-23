package acceptance;

import ServiceLayer.GuestImp;
import ServiceLayer.ManagerImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class uc_2_8_Test {
    private static GuestImp gi;
    private static SubscriberImp si;
    private static ManagerImp managerImp;


    @BeforeClass
    public static  void setUp() throws Exception {
        gi=new GuestImp();
        si= new SubscriberImp();
        managerImp = new ManagerImp();
        StoreRoleImp sri = new StoreRoleImp();
        managerImp.init_system(false);
        gi.sign_up("mhmod","123");
        gi.login("mhmod","123");
        si.open_store("mhmod","store1");
        sri.add_store_product("mhmod","store1","bmba",1,2);
        sri.add_store_product("mhmod","store1","besli",12,2);
        sri.add_store_product("mhmod","store1","twix",3,2);

        si.open_store("mhmod","store2");
        sri.add_store_product("mhmod","store2","bmba",11,2);
        sri.add_store_product("mhmod","store2","chips",3,2);
        sri.add_store_product("mhmod","store2","twix",5,2);

        si.open_store("mhmod","store3");
        sri.add_store_product("mhmod","store3","bmba",4,2);
        sri.add_store_product("mhmod","store3","besli",4,2);
        sri.add_store_product("mhmod","store3","twix",6,2);


    }
    @Test
    public void successScenario() throws Exception {
        gi.save_products(0,"bmba","store3",1);
        assertTrue(gi.buy_products_in_cart(0,"mahmoud","1234123412341234","11/26",999));

    }
    @Test
    public void failScenario1() throws Exception {
        assertFalse(gi.buy_products_in_cart(0,"mahmoud","12341234123412341","11/26",999));
        assertFalse(gi.buy_products_in_cart(0,"mahmoud","1234123412341234","11/261",999));
    }
}
