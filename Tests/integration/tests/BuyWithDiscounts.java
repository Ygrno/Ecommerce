package integration.tests;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.User.Subscriber;
import ServiceLayer.AdminImp;
import ServiceLayer.GuestImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuyWithDiscounts {


    @BeforeClass
    public static void before() throws Exception {

        AdminImp adminImp = new AdminImp();
        adminImp.init_system(false);

        GuestImp guestImp = new GuestImp();
        guestImp.sign_up("s1","123");
        SubscriberImp subscriberImp = new SubscriberImp();
        subscriberImp.open_store("s1","computers");

        StoreRoleImp storeRoleImp = new StoreRoleImp();
        storeRoleImp.add_store_product("s1","computers","pc",1500,50);

        guestImp.sign_up("s2","123");
        storeRoleImp.add_conditioned_discount("s1","computers","pc","D1",1,12122104,2,-1);

        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);

        subscriberImp.buy_products_in_cart("s2","moti","1234567891234567","12/24",123,"123456789");

    }


    @Test
    public void test1(){
        Subscriber s2 = SystemManage_Facade.get_subscriber("s2");
        assertEquals(0, s2.getPurchaseProcesslist().get(0).getShoppingBag().getProducts().get(2).getPrice(), 0.0);
        assertEquals(0, s2.getPurchaseProcesslist().get(0).getShoppingBag().getProducts().get(5).getPrice(), 0.0);

    }


}
