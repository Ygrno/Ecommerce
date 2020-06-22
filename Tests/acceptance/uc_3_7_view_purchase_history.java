package acceptance;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.System;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class uc_3_7_view_purchase_history {

    //  TODO: 3.7 test update is needed

    private  static SubscribersManage_Facade SUB;
    private static SystemManage_Facade SYS;
    private static ArrayList<String> bamba ;


    @BeforeClass
    public static void before() {
        SYS = new SystemManage_Facade();
        SUB = new SubscribersManage_Facade();
        SYS.init_system();
        SYS.is_initialized();
        SYS.add_subscriber("subscriber", "subscriber");
        SUB.subscriber_login_state("subscriber",true);
        SUB.create_store("subscriber","test");
        bamba= new ArrayList<>();
        bamba.add("bamba");
    }

    @Test
    public void success_scenario() throws JSONException {
        SUB.purchaseListAdd("subscriber" ,"test",bamba);
        assertNotNull(SYS.View_purchase("subscriber"));
    }


    @Test
    public void failure_scenario() throws JSONException {
        System.getSystem().get_subscriber("subscriber").setLogged_in(false);
        assertNotNull(SYS.View_purchase("subscriber"));
    }
}
