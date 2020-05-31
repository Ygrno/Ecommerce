package acceptance;

import ServiceLayer.GuestImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class uc_2_6_Test {

    private GuestImp gi;


    @Before
    public void setUp() throws IOException {
        gi=new GuestImp();
        SubscriberImp si = new SubscriberImp();
        StoreRoleImp sri = new StoreRoleImp();

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
    public void successScenario(){
        gi.save_products(1,"bmba","store1");
        gi.save_products(1,"twix","store3");
        gi.save_products(1,"chips","store2");

        try {
            assert gi.watch_products_in_cart(1).contains("bmba");
            assert gi.watch_products_in_cart(1).contains("twix");
            assert gi.watch_products_in_cart(1).contains("chips");
        }catch (Exception e){

        }
    }

    @Test
    public void failScenario1(){
        assert !gi.save_products(1,"bmba","store2");
    }

}
