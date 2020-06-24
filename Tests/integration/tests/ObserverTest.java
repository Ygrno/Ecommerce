package integration.tests;

import DomainLayer.InternalService.SystemManage_Facade;
import Observer.Observer;
import ServiceLayer.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

public class ObserverTest {
    private static SystemManage_Facade SYS;
    private static SubscriberImp SUBImp;
    private static GuestImp guestImp;
    private static StoreRoleImp storeRoleImp;
    private static IAdmin managerImp;
    private static Observer _Observer;
    @Before
    public void setUp() throws Exception {
        SYS = new SystemManage_Facade();
        SYS.init_system();
        SYS.is_initialized();
        _Observer= Observer.GetObserver();
        managerImp = new AdminImp();
        SUBImp = new SubscriberImp();
        guestImp = new GuestImp();
        storeRoleImp = new StoreRoleImp();
        managerImp.init_system(false);
        guestImp.login("Admin","Password");
        guestImp.sign_up("subscriber", "subscriber");
        guestImp.login("subscriber","subscriber");
        SUBImp.open_store("Admin","test");
    }

    @Test
    public void CheckNotification() throws Exception {
        assertTrue(_Observer.CheckNotification("Admin"));
        assertFalse(_Observer.CheckNotification("subscriber"));
    }

    @Test
    public void update() throws Exception {
        JSONObject o = new JSONObject();
        o.put("username","Admin");
        o.put("message", "message");
        _Observer.update(o);
        assertEquals(_Observer.getObservers().get(0).getString("username"),"Admin");
    }


}