package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import ServiceLayer.GuestImp;
import ServiceLayer.IGuest;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class uc_2_5_Test {
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
        HashMap<String,Integer> l=gi.search_products("bmba");
        assert l.containsKey("store1");
        assert l.containsKey("store2");
        assert l.containsKey("store3");

    }
    @Test
    public void failScenario1(){
        HashMap<String,Integer> l=gi.search_products("aa");
        assert l.size()==0;
    }

}