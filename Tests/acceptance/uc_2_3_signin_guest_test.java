package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.System;
import ServiceLayer.GuestImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class uc_2_3_signin_guest_test {

    private static GuestImp admin ;


    @BeforeClass
    public static void before() throws IOException {
        admin = new GuestImp();
    }

    @Test
    public void success_scenario(){
        admin.login("Admin", "Password");
        assert SystemManage_Facade.is_initialized();
    }


    @Test
    public void failure_scenario(){
        admin.login("Admin", "wrong_password");
        assert !SystemManage_Facade.is_initialized();
    }
}
