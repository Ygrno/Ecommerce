package acceptance;

import DAL.DBAccess;
import DomainLayer.InternalService.SystemManage_Facade;
import ServiceLayer.GuestImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class uc_2_5_Test {
    private static GuestImp gi;
    private DBAccess db;
    @BeforeClass
    public static void setUp() throws IOException {
        gi=new GuestImp();
        SubscriberImp si = new SubscriberImp();
        StoreRoleImp sri = new StoreRoleImp();
        SystemManage_Facade.init_system(false);
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
        HashMap<String, Double> l=gi.search_products("bmba");
        assert l.containsKey("store1");
        assert l.containsKey("store2");
        assert l.containsKey("store3");

    }
    @Test
    public void failScenario1(){
        HashMap<String, Double> l=gi.search_products("aa");
        assert l.size()==0;
    }

}
