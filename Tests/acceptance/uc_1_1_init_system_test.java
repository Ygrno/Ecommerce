package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.System;
import ServiceLayer.GuestImp;
import ServiceLayer.IGuest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class uc_1_1_init_system_test {


    private static GuestImp guestImp;



    @BeforeClass
    public static void before() throws IOException {
        SystemManage_Facade.init_system();
        guestImp = new GuestImp();
        guestImp.sign_up("user", "password");
        guestImp.sign_up("user1", "password");
    }

    @Test
    public void success_scenario(){
        guestImp.login("user", "password");
        assert SystemManage_Facade.get_subscriber("user").isLogged_in();
    }


    @Test
    public void failure_scenario() {
        guestImp.login("user1", "wrong_password");
        assert !SystemManage_Facade.get_subscriber("user1").isLogged_in();
    }

}
