package acceptance;

import ServiceLayer.GuestImp;
import ServiceLayer.AdminImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class uc_4_4_remove_store_owner {

    private static SubscriberImp SUBImp;
    private static GuestImp guestImp;
    private static StoreRoleImp storeRoleImp;
    private static AdminImp managerImp;

    @BeforeClass
    public static void before() throws Exception {
        SUBImp = new SubscriberImp();
        guestImp = new GuestImp();
        storeRoleImp = new StoreRoleImp();
        managerImp = new AdminImp();
        managerImp.init_system(false);
        guestImp.login("A1", "Password");
        guestImp.sign_up("subscriber", "subscriber");
        guestImp.login("subscriber", "subscriber");
        SUBImp.open_store("subscriber", "store1");

        storeRoleImp.assign_store_owner_approval( "store1", "A1");

        guestImp.sign_up("s2", "123");
        guestImp.sign_up("s3", "123");
        guestImp.sign_up("s4", "123");

        storeRoleImp.assign_store_owner_approval( "store1", "s2");
        storeRoleImp.assign_store_owner_approval( "store1", "s3");
        storeRoleImp.assign_store_manager( "A1","store1", "s4");

    }

    private void assign_users() throws Exception {
        storeRoleImp.assign_store_owner_approval( "store1", "A1");
        storeRoleImp.assign_store_owner_approval( "store1", "s2");
        storeRoleImp.assign_store_owner_approval( "store1", "s3");
        storeRoleImp.assign_store_manager("A1","store1", "s4");

    }

    private void add_moshe() throws Exception {
        guestImp.sign_up("Moshe", "123");
        guestImp.login("Moshe", "123");
    }

    private void assign_moshe() throws Exception {
        storeRoleImp.assign_store_manager("subscriber", "store1", "Moshe");


    }


    @Test
    public void a_success_scenario() throws Exception {
        //Valid remove s3 store owner assigned by Admin.
        assertTrue(storeRoleImp.remove_store_owner("A1", "store1", "s3"));

        //Valid remove Admin store owner assigned by subscriber.
        assertTrue(storeRoleImp.remove_store_owner("subscriber", "store1", "A1"));
    }


    @Test
    public void b_failure_scenario() throws Exception {

        //Trying to remove Admin after he was removed.
        assertFalse(storeRoleImp.remove_store_owner("subscriber", "store1", "A1"));

        //Trying to remove Admin by a user that doesn't even exist
        assertFalse(storeRoleImp.remove_store_owner("Moshe", "store1", "A1"));

        add_moshe();

        //Now moshe does exists in the system, but he isn't related to the store in any way.
        assertFalse(storeRoleImp.remove_store_owner("Moshe", "store1", "A1"));

        assign_moshe();

        //Now moshe does exits in the system and a related to the store, but he can't remove store owner - Admin because he is a manager. (Managers can't remove Store Owners)
        assertFalse(storeRoleImp.remove_store_owner("Moshe", "store1", "A1"));

        //Reassigning users to the store.
        assign_users();

        //Invalid remove of s2, s2 is Store owner but wasn'y assigned by subscriber
        assertFalse(storeRoleImp.remove_store_owner("subscriber", "store1", "s2"));
    }


}
