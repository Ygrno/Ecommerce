package integration.tests;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.User.Subscriber;
import ServiceLayer.GuestImp;
import ServiceLayer.AdminImp;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubscriberTest {

    private static Subscriber subscriber;
    private static AdminImp managerImp;
    private static GuestImp guestImp;


    @BeforeClass
    public static void setUp() throws Exception {
        managerImp = new AdminImp();
        managerImp.init_system(false);
        guestImp = new GuestImp();
        guestImp.sign_up("test_sub", "pass");

        //subscriber = new Subscriber("test_sub", "pass");
        SubscribersManage_Facade.create_store("test_sub", "test_store");

    }

    @Test
    public void isLogged_in() {
        subscriber.setLogged_in(true);
        assert subscriber.isLogged_in();

        subscriber.setLogged_in(false);
        assert !subscriber.isLogged_in();
    }

    @Test
    public void setLogged_in() {
        subscriber.setLogged_in(true);
        assert subscriber.isLogged_in();

        subscriber.setLogged_in(false);
        assert !subscriber.isLogged_in();
    }

    @Test
    public void get_role_at_store() {
        //TODO : implement
    }
}