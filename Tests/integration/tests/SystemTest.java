package integration.tests;

import DomainLayer.System;
import DomainLayer.User.Subscriber;
import integration.stubs.SubscriberStub;
import junit.framework.TestCase;
import org.junit.Test;

public class SystemTest extends TestCase {


    private System system;

    @Override
    protected void setUp() throws Exception {
        system = System.getSystem();
        system.add_subscriber(new SubscriberStub());
    }

    @Test
    public void get_subscriber_test(){
        Subscriber s = system.get_subscriber("Sub_stub");
        assertEquals(s.getName(), "Sub_stub");
    }
}
