package acceptance;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import ServiceLayer.SubscriberImp;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class uc_3_7_view_purchase_history {

    //  TODO: 3.7 test update is needed

    private  static SubscribersManage_Facade SUB;
    private static SystemManage_Facade SYS;


    @BeforeClass
    public static void before() {
        SYS = new SystemManage_Facade();
        SYS.init_system(false);
        SYS.is_initialized();
        SYS.add_subscriber("subscriber", "subscriber");
        SUB.subscriber_login_state("subscriber",true);
    }

    @Test
    public void success_scenario() throws JSONException {
        SUB.purchaseListAdd("subscriber" ,"test",new ArrayList<String>());
        assertNotNull(SYS.View_purchase("subscriber"));
    }


    @Test
    public void failure_scenario() throws JSONException {
        System.getSystem().get_subscriber("subscriber").setLogged_in(false);
        assertNotNull(SYS.View_purchase("subscriber"));
    }
}
