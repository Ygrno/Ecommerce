package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreManger;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import ServiceLayer.GuestImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class uc_4_7_remove_store_manager {

    private static GuestImp guestImp;

    @BeforeClass
    public static void before() throws IOException {
        SystemManage_Facade.init_system();
        guestImp = new GuestImp();
        guestImp.sign_up("manager", "password");
        System.getSystem().getStore_list().add(new Store("store"));
    }

    @Test
    public void success_scenario(){
        guestImp.login("manager", "password");

        Subscriber s = System.getSystem().get_subscriber("manager");
        Role r = new StoreManger(s, System.getSystem().get_store("store"));
        s.getRole_list().add(r);
        s.getRole_list().remove(r);
        assert s.get_role_at_store("store") == null;
    }


    @Test
    public void failure_scenario() {
        guestImp.login("manager", "password");

        Subscriber s = System.getSystem().get_subscriber("manager");
        Role r = new StoreManger(s, System.getSystem().get_store("store"));
        s.getRole_list().add(r);

        Role r2 = new StoreOwner(s, System.getSystem().get_store("store"));
        s.getRole_list().remove(r2);

        assert !s.get_role_at_store("store").equals(r2);
    }


}
