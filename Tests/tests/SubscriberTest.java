package tests;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreRole;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubscriberTest {

    private static Subscriber subscriber;


    @BeforeClass
    public static void setUp() throws Exception {
        subscriber = new Subscriber("test_sub", "pass");
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
        StoreRole r = new StoreRole() {};
        r.setUser(subscriber);
        ((StoreRole) r).setStore(System.getSystem().get_store("test_store"));

        assert subscriber.get_role_at_store("test_store") == null;

        subscriber.getRole_list().add(r);
        assert subscriber.get_role_at_store("test_store") != null;

    }
}