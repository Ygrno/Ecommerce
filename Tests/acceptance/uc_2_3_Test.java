package acceptance;

import DomainLayer.InternalService.SubscribersManage_Facade;

import ServiceLayer.GuestImp;
import ServiceLayer.IGuest;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class uc_2_3_Test {


    IGuest guestimp;
    @Before
    public void setUp() throws IOException {
        guestimp=new GuestImp();
    }

    public void successScenario(){
        guestimp.sign_up("mahmod","123");
        assert  guestimp.login("mahmod","123");;
    }

    public void failScenario1(){
        guestimp.sign_up("mahmod","123");

        assert !guestimp.login("mahmo", "123");
    }

    public void failScenario2(){
        guestimp.sign_up("mahmod","123");

        assert !guestimp.login("mahmod", "1234");
    }

}
