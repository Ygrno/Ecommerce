package acceptance;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.User.Subscriber;
import DomainLayer.System;
import ServiceLayer.GuestImp;
import ServiceLayer.IGuest;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class uc_2_3_Test {


    IGuest guestimp;
    System system;
    @Before
    public void setUp() throws IOException {
        SystemManage_Facade.init_system();
        guestimp=new GuestImp();
        system=System.getSystem();
    }

    @Test
    public void successScenario(){
        Subscriber s =new Subscriber("mahmod","123");
        system.getUser_list().add(s);
        assert  guestimp.login("mahmod","123");;
    }

    @Test
    public void failScenario1(){
        Subscriber s =new Subscriber("mahmod","123");
        system.getUser_list().add(s);

        assert !guestimp.login("mahmo", "123");
    }

    @Test
    public void failScenario2(){
        Subscriber s =new Subscriber("mahmod","123");
        system.getUser_list().add(s);

        assert !guestimp.login("mahmo", "123");
    }

}
