package acceptance;

import ServiceLayer.GuestImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class uc_4_10_check_purchase_history {

    private static GuestImp guestImp;
    private static SubscriberImp SUBImp;
    private static StoreRoleImp storeRoleImp;
    private static ArrayList<String> permissions = new ArrayList<>();

    @BeforeClass
    public static void before() throws IOException, JSONException {
        permissions.add("ADD_PRODUCT");
        guestImp = new GuestImp();
        SUBImp = new SubscriberImp();
        storeRoleImp =  new StoreRoleImp();
        guestImp.login("Admin","Password");
        guestImp.sign_up("s1", "password");
        guestImp.login("s1", "password");
        SUBImp.open_store("Admin","store");
        storeRoleImp.add_store_product("Admin","store","bamba",3,2);
        SUBImp.save_products("s1","bamba","store",1);
        SUBImp.buy_products_in_cart("s1","noam","1234567891234567","22019",120);


    }

    @Test
    public void success_scenario() throws JSONException {

        String purchase1  = storeRoleImp.watch_store_history("Admin","store");
        Assert.assertEquals("\n" +
                "Customer Name: noam\n" +
                "List of products: [bamba]\n" +
                " sum: 3.0",purchase1);

        addProductsFromDifferentStores();

        String purchase2 = storeRoleImp.watch_store_history("Admin","store");

        Assert.assertEquals("\n" +
                "Customer Name: noam\n" +
                "List of products: [bamba]\n" +
                " sum: 3.0\n" +
                "Customer Name: noam\n" +
                "List of products: [bisli]\n" +
                " sum: 6.0",purchase2);

        String purchase3 = storeRoleImp.watch_store_history("Admin","Computer Store");

        Assert.assertEquals("\n" +
                "Customer Name: noam\n" +
                "List of products: [PC, mouse, keyboard]\n" +
                " sum: 4100.0",purchase3);

        //assertTrue(storeRoleImp.edit_manager_permissions("Admin","store","manager",permissions));
    }

    private void addProductsFromDifferentStores() throws JSONException {

        SUBImp.open_store("Admin","Computer Store");
        storeRoleImp.add_store_product("Admin","Computer Store","PC",1500,2);
        storeRoleImp.add_store_product("Admin","Computer Store","mouse",1250,2);
        storeRoleImp.add_store_product("Admin","Computer Store","keyboard",1350,2);

        storeRoleImp.add_store_product("Admin","store","bisli",6,8);


        SUBImp.save_products("s1","bisli","store",1);
        SUBImp.save_products("s1","PC","Computer Store",1);
        SUBImp.save_products("s1","mouse","Computer Store",1);
        SUBImp.save_products("s1","keyboard","Computer Store",1);
        SUBImp.buy_products_in_cart("s1","noam","1234567891234567","22019",120);





    }


    @Test
    public void failure_scenario() {

        String purchase1  = storeRoleImp.watch_store_history("s1","store");
        Assert.assertNull(purchase1);

    }





}
