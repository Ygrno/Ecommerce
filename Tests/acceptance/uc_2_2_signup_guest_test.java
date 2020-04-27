package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import ServiceLayer.GuestImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class uc_2_2_signup_guest_test {


    private static GuestImp guestImp ;

    @BeforeClass
    public static void before() throws IOException {
        SystemManage_Facade.init_system();
        guestImp = new GuestImp();
    }


    @Test
    public void success_scenario(){
        Subscriber s = new Subscriber("user", "password");
        guestImp.sign_up(s.getName(), s.getPassword());
        assert System.getSystem().getUser_list().contains(s);
    }


    @Test
    public void failure_scenario(){
        Subscriber s1 = new Subscriber("user", "password");
        Subscriber s2 = new Subscriber("user", "another_password");
        guestImp.sign_up(s1.getName(), s1.getPassword());
        guestImp.sign_up(s2.getName(), s2.getPassword());

        assert !System.getSystem().getUser_list().contains(s2);

    }
}
