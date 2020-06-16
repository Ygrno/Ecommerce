package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import ServiceLayer.GuestImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.json.JSONException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class uc_2_7_Test {
    private static GuestImp gi;


    @BeforeClass
    public static void setUp() throws IOException {
        gi=new GuestImp();
        SubscriberImp si = new SubscriberImp();
        StoreRoleImp sri = new StoreRoleImp();
        SystemManage_Facade.init_system();
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
    public void successScenario() throws JSONException {
        gi.save_products(1,"bmba","store1",1);
        gi.save_products(1,"twix","store3",1);
        gi.save_products(1,"chips","store2",1);
        assert gi.watch_products_in_cart(1).get(0).getString("name").equals("twix");
        assert gi.watch_products_in_cart(1).get(1).getString("name").equals("chips");
        assert !gi.watch_products_in_cart(1).get(1).getString("name").equals("besli");

    }
    @Test
    public void failScenario1() throws JSONException {

        assert null == gi.watch_products_in_cart(2);
    }

}
