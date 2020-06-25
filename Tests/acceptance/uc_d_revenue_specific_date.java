package acceptance;

import ServiceLayer.AdminImp;
import ServiceLayer.GuestImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class uc_d_revenue_specific_date {

    private static GuestImp guestImp;
    private static SubscriberImp SUBImp;
    private static StoreRoleImp storeRoleImp;
    private static AdminImp adminImp;
    private static ArrayList<String> permissions = new ArrayList<>();
    private static AdminImp managerImp;

    @BeforeClass
    public static void before() throws Exception {
        permissions.add("ADD_PRODUCT");
        adminImp = new AdminImp();
        guestImp = new GuestImp();
        SUBImp = new SubscriberImp();
        storeRoleImp =  new StoreRoleImp();
        managerImp = new AdminImp();
        managerImp.init_system(false);
        guestImp.login("Admin","Password");
        guestImp.sign_up("s1", "password");
        guestImp.login("s1", "password");
        SUBImp.open_store("Admin","store");
        storeRoleImp.add_store_product("Admin","store","bamba",3,2);
        SUBImp.save_products("s1","bamba","store",1);
        SUBImp.buy_products_in_cart("s1","noam","1234567891234567","2/2019",120,"123456789","","","","");
        guestImp.sign_up("s2", "password");
        guestImp.login("s2", "password");
        SUBImp.save_products("s2","bamba","store",1);
        SUBImp.buy_products_in_cart("s2","ben","1234567891234567","2/2019",120,"123456789","","","","");

    }

    @Test
    public void success_scenario() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String dateString = format.format( new Date());
        String date_revenue= adminImp.date_revenue(dateString);
        assertEquals(date_revenue,"0.0");
    }




    @Test
    public void failure_scenario() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String dateString = format.format( new Date());
        String today_revenue= adminImp.date_revenue(dateString);
        assertNotEquals(today_revenue,"5.0");

    }





}
