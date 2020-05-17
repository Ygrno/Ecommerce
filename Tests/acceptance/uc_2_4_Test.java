package acceptance;


import ServiceLayer.GuestImp;

import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class uc_2_4_Test {

    private static GuestImp guestImp;
    private static SubscriberImp SUBImp;
    private static StoreRoleImp storeRoleImp;

    @BeforeClass
    public static void before() throws IOException {
        guestImp = new GuestImp();
        SUBImp = new SubscriberImp();
        storeRoleImp =  new StoreRoleImp();
        guestImp.login("Admin","Password"); //initiate the system
        guestImp.sign_up("manager", "password");
        guestImp.login("manager", "password");
        SUBImp.open_store("Admin","store1");
        storeRoleImp.assign_store_manager("Admin","store1","manager");
        storeRoleImp.add_store_product("Admin", "store1", "bamba", 3,10);
        storeRoleImp.add_store_product("Admin", "store1", "bisli", 4,30);

    }


    @Test
    public void run_tests(){
       successScenario();
        failScenario1();
    }


    public void successScenario(){
        assertTrue ((guestImp.view_products_information_store("store1")).length==2);
        assertTrue ((guestImp.view_products_information_store("store1"))[0][0]=="bamba");
    }


    public void failScenario1(){
        assertFalse (guestImp.view_products_information_store("store")==null);;
    }

}
