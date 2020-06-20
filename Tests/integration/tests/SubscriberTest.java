package integration.tests;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreRole;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import NetworkLayer.passiveObjects.GuestMessageProccess;
import ServiceLayer.GuestImp;
import ServiceLayer.ManagerImp;
import ServiceLayer.SubscriberImp;
import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubscriberTest {

    private static Subscriber subscriber;
    private static ManagerImp managerImp;
    private static GuestImp guestImp;


    @BeforeClass
    public static void setUp() throws Exception {
        managerImp = new ManagerImp();
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