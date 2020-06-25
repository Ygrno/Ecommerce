package Unit.tests.Concurrency;

import DomainLayer.User.Subscriber;
import ServiceLayer.AdminImp;
import ServiceLayer.GuestImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

public class BuyThings {

    private static AdminImp managerImp;
    private static GuestImp guestImp;
    private static SubscriberImp subscriberImp;
    private static StoreRoleImp storeRoleImp;

    @BeforeClass
    public static void setUp() throws Exception {
        guestImp = new GuestImp();
        managerImp = new AdminImp();
        managerImp.init_system(false);
        subscriberImp = new SubscriberImp();
        storeRoleImp = new StoreRoleImp();

        guestImp.sign_up("test", "****");
        subscriberImp.open_store("test","test_store");
        storeRoleImp.add_store_product("test","test_store", "test_product", 100, 1);

    }

    @Test
    public void buyThings() {

        final boolean[] s = {false, false};
        guestImp.save_products(0, "test_product", "test_store", 1);
        guestImp.save_products(1, "test_product", "test_store", 1);

        Thread t1 = new Thread(() -> {
            try {
                s[0] = guestImp.buy_products_in_cart(0, "guest1", "1111111111111111", "4/2021", 111, "206666666","","","","");
            } catch (Exception e) {

            }
        });

        Thread t2 = new Thread(() -> {
            try {
                s[1] = guestImp.buy_products_in_cart(1, "guest2", "1111111111111111", "4/2021", 111, "206666666","","","","");
            } catch (Exception e) {

            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {

        }


        boolean success = s[0] ^ s[1];
        assert success;
    }







}
