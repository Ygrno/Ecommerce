package acceptance;

import DomainLayer.InternalService.OwnersApproval;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Roles.StoreRole;
import ServiceLayer.GuestImp;
import ServiceLayer.AdminImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class uc_4_3_assign_store_owner {

    private static SystemManage_Facade SYS;
    private static SubscribersManage_Facade SUB;
    private static SubscriberImp SUBImp;
    private static GuestImp guestImp;
    private static StoreRoleImp storeRoleImp;
    private static AdminImp managerImp;

    // Setup
    private static List<JSONObject> resopnse;
    private static List<JSONObject> resopnse1;
    private static JSONObject O;
    @BeforeClass
    public static void before() throws Exception {
        SYS = new SystemManage_Facade();
        SUB = new SubscribersManage_Facade();
        SYS.init_system();
        SYS.is_initialized();

        managerImp = new AdminImp();
        SUBImp = new SubscriberImp();
        guestImp = new GuestImp();
        storeRoleImp = new StoreRoleImp();
        managerImp.init_system(false);

        guestImp.sign_up("subscriber", "subscriber");
        guestImp.sign_up("subscriber1", "subscriber1");
        guestImp.sign_up("subscriber2", "subscriber2");
        guestImp.sign_up("subscriber3", "subscriber3");
        guestImp.sign_up("subscriber4", "subscriber4");

        guestImp.login("subscriber","subscriber");
        guestImp.login("subscriber1","subscriber1");
        guestImp.login("subscriber2","subscriber2");
        guestImp.login("subscriber3","subscriber3");

        SUBImp.open_store("subscriber","store1");
        StoreRole R = new StoreOwner(SYS.get_subscriber("subscriber1"),SYS.get_store("store1"));
        SYS.get_subscriber("subscriber1").getRole_list().add(R);
        SYS.get_store("store1").getRoles().add(R);
    }

    @BeforeClass
    public static void SetUp() throws JSONException {
        resopnse =((StoreOwner)SYS.get_subscriber("subscriber").get_role_at_store("store1")).getResponse();
        resopnse1 =((StoreOwner)SYS.get_subscriber("subscriber1").get_role_at_store("store1")).getResponse();
        O= new JSONObject();
        O.put("username","subscriber2");
        O.put("response","yes");
        resopnse.add(O);
        resopnse1.add(O);
    }

    //first owner request to store1
    @Test
    public void OwnerStoreReq() throws Exception {
        assertTrue(storeRoleImp.assign_store_owner("store1","subscriber2"));
        //seceond owner request to store1
        assertTrue(storeRoleImp.assign_store_owner("store1","subscriber3"));
    }

    //trying to request without logging in(in theory should work)
    @Test
    public void OwnerStoreReqNoLog() throws Exception {
      assertTrue(storeRoleImp.assign_store_owner("store1","subscriber4"));
    }

    //some of the owners did not responed yet
    @Test
    public void OwnerStoreReqNotAll() throws Exception {
        resopnse.remove(O);
        assertFalse(storeRoleImp.assign_store_owner("store1","subscriber2"));
    }

    //requesting to the same subscriber on the same store twice while he is already an owner
    @Test
    public void OwnerStoreReqTwice() throws Exception {
        storeRoleImp.assign_store_owner("store1","subscriber2");
        assertFalse(storeRoleImp.assign_store_owner("store1","subscriber2"));

    }

    //some of the owners accept and some not
    @Test
    public void OwnerStoreReqSomeNot() throws Exception {
        resopnse1.remove(O);
        JSONObject O1 = new JSONObject();
        O1.put("username","subscriber2");
        O1.put("response","no");
        resopnse1.add(O1);
        assertFalse(storeRoleImp.assign_store_owner("store1","subscriber2"));
    }

}
