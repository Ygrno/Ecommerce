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

    public static StoreRoleImp storeRoleImp;
    public static SubscriberImp subscriberImp;


    @BeforeClass
    public static void before() throws Exception {

        AdminImp adminImp = new AdminImp();
        adminImp.init_system(false);

        GuestImp guestImp = new GuestImp();
        guestImp.sign_up("s1","123");
        subscriberImp = new SubscriberImp();
        subscriberImp.open_store("s1","computers");

        storeRoleImp = new StoreRoleImp();
        storeRoleImp.add_store_product("s1","computers","pc",1500,100);

        guestImp.sign_up("s2","123");
        storeRoleImp.add_conditioned_discount("s1","computers","pc","D1",1,12122104,2,-1);

        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);

        subscriberImp.buy_products_in_cart("s2","moti","1234567891234567","12/24",123,"123456789","","","","");




    }


    @Test
    public void test1() throws Exception {
        Subscriber s2 = SystemManage_Facade.get_subscriber("s2");
        assertEquals(0, s2.getPurchaseProcesslist().get(0).getShoppingBag().getProducts().get(2).getPrice(), 0.0);
        assertEquals(0, s2.getPurchaseProcesslist().get(0).getShoppingBag().getProducts().get(5).getPrice(), 0.0);

        storeRoleImp.add_visible_discount("s1","computers","D2",0.5,12122104,"pc");

        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);

        subscriberImp.buy_products_in_cart("s2","moti","1234567891234567","12/24",123,"123456789","","","","");
        assertEquals(0, s2.getPurchaseProcesslist().get(1).getShoppingBag().getProducts().get(2).getPrice(), 0.0);
        assertEquals(0, s2.getPurchaseProcesslist().get(1).getShoppingBag().getProducts().get(5).getPrice(), 0.0);
        assertEquals(0, s2.getPurchaseProcesslist().get(1).getShoppingBag().getProducts().get(0).getPrice(), 750);

        storeRoleImp.delete_discount("s1","computers","D1");
        storeRoleImp.delete_discount("s1","computers","D2");

        storeRoleImp.add_conditioned_discount("s1","computers","pc","D1",0.5,12122104,0,100);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","pc","computers",1);

        subscriberImp.buy_products_in_cart("s2","moti","1234567891234567","12/24",123,"123456789","","","","");
        assertEquals(1500,s2.getPurchaseProcesslist().get(2).getShoppingBag().getDiscounted_bag_price(), 0.05);

        storeRoleImp.delete_discount("s1","computers","D1");

        storeRoleImp.add_store_product("s1","computers","apple",100,100);
        storeRoleImp.add_store_product("s1","computers","orange",100,100);

        storeRoleImp.add_visible_discount("s1","computers","D1",0.5,12122104,"pc");
        storeRoleImp.add_visible_discount("s1","computers","D2",0.5,12122104,"apple");
        String[] array = new String[2];
        array[0] = "D1";
        array[1] = "D2";
        storeRoleImp.add_complex_discount("s1","computers","D3",array,"and",12122104);
        storeRoleImp.delete_discount("s1","computers","D1");
        storeRoleImp.delete_discount("s1","computers","D2");

        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","apple","computers",1);

        subscriberImp.buy_products_in_cart("s2","moti","1234567891234567","12/24",123,"123456789","","","","");
        assertEquals(800, s2.getPurchaseProcesslist().get(3).getShoppingBag().getDiscounted_bag_price(), 0.05);

        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","orange","computers",1);

        subscriberImp.buy_products_in_cart("s2","moti","1234567891234567","12/24",123,"123456789","","","","");

        assertEquals(1600, s2.getPurchaseProcesslist().get(4).getShoppingBag().getDiscounted_bag_price(), 0.05);

        storeRoleImp.delete_discount("s1","computers","D3");

        storeRoleImp.add_visible_discount("s1","computers","D1",0.5,12122104,"pc");
        storeRoleImp.add_conditioned_discount("s1","computers","pc","D2",0.5,12122104,0,100);
        subscriberImp.save_products("s2","pc","computers",1);
        subscriberImp.save_products("s2","orange","computers",1);
        subscriberImp.save_products("s2","apple","computers",1);

        subscriberImp.buy_products_in_cart("s2","moti","1234567891234567","12/24",123,"123456789","","","","");

        assertEquals(475, s2.getPurchaseProcesslist().get(5).getShoppingBag().getDiscounted_bag_price(), 0.05);
    }


}
