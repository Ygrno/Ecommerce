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

public class uc_2_4_Test {

    private static GuestImp guestImp;
    private static SubscriberImp SUBImp;
    private static StoreRoleImp storeRoleImp;
    private static ManagerImp managerImp;

    @BeforeClass
    public static void before() throws Exception {
        guestImp = new GuestImp();
        SUBImp = new SubscriberImp();
        storeRoleImp =  new StoreRoleImp();
        managerImp = new ManagerImp();
        managerImp.init_system(false);

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
        assertTrue ((guestImp.view_products_information_store("store1"))[1][0]=="bisli");
        assertTrue (guestImp.view_products_information_store("store1")[0][1].equals("3.0"));
        assertTrue (guestImp.view_products_information_store("store1")[0][2].equals("10"));
        assertTrue (guestImp.view_products_information_store("store1")[0][3].equals("store1"));


    }


    public void failScenario1(){
        assertFalse (guestImp.view_products_information_store("store")==null);;
    }

}
