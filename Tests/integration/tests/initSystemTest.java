package integration.tests;

import DomainLayer.Product;
import DomainLayer.Roles.StoreManger;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Store.Policy;
import DomainLayer.Store.Store;
import DomainLayer.System;
import ServiceLayer.InitSystemState;
import ServiceLayer.AdminImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Store.BuyPolicy;

public class initSystemTest {

    public static AdminImp managerImp;


    @BeforeClass
    public static void setUp() throws Exception {
        managerImp =  new AdminImp();
        managerImp.init_system(true);
        InitSystemState.init();
    }

    @Test
    public void subscriber_login_state() throws Exception {
        assert SubscribersManage_Facade.check_if_logged_in("U1");
    }

    @Test
    public void check_if_logged_in() throws Exception {
        assert SubscribersManage_Facade.check_if_logged_in("U2");
    }


    @Test
    public void create_store() throws Exception {
        assert System.getSystem().get_store("S2") != null;
        assert System.getSystem().get_store("test_sub_not_existed") == null;
    }
/*
    @Test
    public void add_product_to_store() throws Exception {
        Store s = System.getSystem().get_store("shoes");

        Product p = s.getProduct("bamba");
        assert p != null;

        p = s.getProduct("test_product_not_exist");
        assert p == null;
    }
*/
/*    @Test
    public void add_store_simple_buyPolicy() {
        Store s = System.getSystem().get_store("shoes");
        List<BuyPolicy> policies = s.getBuyPolicyList();
        assert policies != null;
        assert policies.size()==1;
        for (Policy p: policies){
            assert (p.getPolicy_id()==1);
        }
    }

    @Test
    public void add_owner_to_store() throws Exception {
        Store s = System.getSystem().get_store("shoes");
        List<BuyPolicy> policies = s.getBuyPolicyList();
        StoreOwner owner = s.find_store_owner_by_name("hila");
        assert owner.user.getName().equals("hila");
    }
    @Test
    public void add_manager_to_store() throws Exception {
        Store s = System.getSystem().get_store("shoes");
        List<BuyPolicy> policies = s.getBuyPolicyList();
        StoreManger manager = s.find_store_manager_by_name("user2");
        assert manager.user.getName().equals("user2");
    }
*/
}
