package integration.tests;

import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SystemTest {

    private static Subscriber subscriber;
    private static Store store;


    @BeforeClass
    public static void setUp() throws Exception {
        subscriber = new Subscriber("test_sub", "pass");
        store = new Store("test_store");
    }

    @Test
    public void get_subscriber() throws Exception {
        assert System.getSystem().get_subscriber("test_sub") == null;

        System.getSystem().add_subscriber(subscriber);
        assert System.getSystem().get_subscriber("test_sub").equals(subscriber);
    }

    @Test
    public void get_store() throws Exception {
        assert System.getSystem().get_store("test_store") == null;

        System.getSystem().getStore_list().add(store);
        assert System.getSystem().get_store("test_store").equals(store);
    }
}