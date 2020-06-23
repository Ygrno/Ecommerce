package acceptance;
import ServiceLayer.GuestImp;
import ServiceLayer.ManagerImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class uc_3_2_open_store {

    private static ManagerImp managerImp;
    private static StoreRoleImp storeRoleImp;
    private static GuestImp guestImp;
    private static SubscriberImp subscriberImp;



    @BeforeClass
    public static void before() throws Exception {

        managerImp = new ManagerImp();

        managerImp.init_system(false);

        guestImp  = new GuestImp();

        subscriberImp = new SubscriberImp();

        guestImp.sign_up("subscriber","subscriber");

        guestImp.login("subscriber","subscriber");
    }

    @Test
    public void a_success_scenario() throws Exception {



        assertTrue(subscriberImp.open_store("subscriber","test"));

    }


    @Test
    public void b_failure_scenario() throws Exception {

        subscriberImp.sign_out("subscriber");

        assertFalse(subscriberImp.open_store("subscriber","test"));
    }
}
