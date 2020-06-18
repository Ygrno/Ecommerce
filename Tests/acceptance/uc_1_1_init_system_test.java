package acceptance;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.*;
import ServiceLayer.GuestImp;
import ServiceLayer.IGuest;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;
import ServiceLayer.ManagerImp;

import java.io.IOException;

public class uc_1_1_init_system_test {


    private static GuestImp guestImp;
    private static ManagerImp managerImp;
    private static SubscriberImp subscriberImp;
    private static SubscribersManage_Facade SUB;
    private static SystemManage_Facade SYS;


    @BeforeClass
    public static void before() throws IOException {
        //SystemManage_Facade.init_system();
        managerImp = new ManagerImp();
        managerImp.init_system(false);
        guestImp = new GuestImp();
        subscriberImp = new SubscriberImp();
        guestImp.sign_up("user", "password");
        guestImp.sign_up("user1", "password1");


    }

    @Test
    public void success_scenario(){
        guestImp.login("user", "password");
        assert SystemManage_Facade.get_subscriber("user").isLogged_in();
        assert SystemManage_Facade.get_subscriber("user1").isLogged_in();

        subscriberImp.sign_out("user1");
        assert SystemManage_Facade.get_subscriber("user1").isLogged_in()==false;
    }


    @Test
    public void failure_scenario() {
        //guestImp.login("user1", "wrong_password");
        assert (SystemManage_Facade.get_subscriber("user2")==null);
    }

}
