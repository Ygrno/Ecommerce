package integration.tests;

import DomainLayer.InternalService.*;
import DomainLayer.Product;
import DomainLayer.Roles.StoreManger;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Store.Policy;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import DomainLayer.Store.BuyPolicy;
import DomainLayer.Store.Policy;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class initSystemTest {


    @BeforeClass
    public static void setUp() throws Exception {
        SystemManage_Facade.init_system();
        InitSystemState.init();
    }

    @Test
    public void subscriber_login_state() {
        assert SubscribersManage_Facade.check_if_logged_in("hila");
    }

    @Test
    public void check_if_logged_in() {
        assert SubscribersManage_Facade.check_if_logged_in("user1");
    }


    @Test
    public void create_store() {
        assert System.getSystem().get_store("shoes") != null;
        assert System.getSystem().get_store("test_sub_not_existed") == null;
    }

    @Test
    public void add_product_to_store() {
        Store s = System.getSystem().get_store("shoes");

        Product p = s.getProduct("bamba");
        assert p != null;

        p = s.getProduct("test_product_not_exist");
        assert p == null;
    }

    @Test
    public void add_store_simple_buyPolicy() {
        Store s = System.getSystem().get_store("shoes");
        List<Policy> policies = s.getPurchasePolicies();
        assert policies != null;
        assert policies.size()==1;
        for (Policy p: policies){
            assert (p.getPolicy_id()==1);
        }
    }

    @Test
    public void add_owner_to_store() {
        Store s = System.getSystem().get_store("shoes");
        List<Policy> policies = s.getPurchasePolicies();
        StoreOwner owner = s.find_store_owner_by_name("hila");
        assert owner.user.getName().equals("hila");
    }
    @Test
    public void add_manager_to_store() {
        Store s = System.getSystem().get_store("shoes");
        List<Policy> policies = s.getPurchasePolicies();
        StoreManger manager = s.find_store_manager_by_name("user2");
        assert manager.user.getName().equals("user2");
    }

}