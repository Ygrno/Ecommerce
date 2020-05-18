package integration.tests;

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

public class SubscribersManage_FacadeTest {


    @BeforeClass
    public static void setUp() throws Exception {
        SystemManage_Facade.init_system();
        System.getSystem().add_subscriber(new Subscriber("test_sub","test_pass"));
    }


    @Test
    public void subscriber_login_state() {
        SubscribersManage_Facade.subscriber_login_state("test_sub", true);
        assert SubscribersManage_Facade.check_if_logged_in("test_sub");
        SubscribersManage_Facade.subscriber_login_state("test_sub", false);
        assert !SubscribersManage_Facade.check_if_logged_in("test_sub");

    }

    @Test
    public void check_if_logged_in() {
        SubscribersManage_Facade.subscriber_login_state("test_sub", true);
        assert SubscribersManage_Facade.check_if_logged_in("test_sub");
        SubscribersManage_Facade.subscriber_login_state("test_sub", false);
        assert !SubscribersManage_Facade.check_if_logged_in("test_sub");
    }

    @Test
    public void create_store() {
        SubscribersManage_Facade.create_store("test_sub", "test_store");
        assert System.getSystem().get_store("test_store") != null;
        assert System.getSystem().get_store("test_sub_not_existed") == null;
    }

    @Test
    public void add_product_to_store() {
        Store s = System.getSystem().get_store("test_sub");

        if(s == null){
            SubscribersManage_Facade.create_store("test_sub", "test_store");
            s = System.getSystem().get_store("test_store");
        }

        SubscribersManage_Facade.add_product_to_store("test_sub", "test_store", "test_product", 2,3);
        Product p = s.getProduct("test_product");
        assert p != null;

        p = s.getProduct("test_product_not_exist");
        assert p == null;
    }

    @Test
    public void add_store_simple_buyPolicy() {
        Store s = System.getSystem().get_store("test_store");

        if(s == null){
            SubscribersManage_Facade.create_store("test_sub", "test_store");
            s = System.getSystem().get_store("test_store");
        }
        SubscribersManage_Facade.add_product_to_store("test_sub", "test_store", "test_product", 2,3);

        SubscribersManage_Facade.create_store_simple_policy("test_sub", "test_store", 2, 1, "test_product", 1, 5,  -1, -1);
        List<Policy> policies = s.getPurchasePolicies();
        assert policies != null;
        assert policies.size()==1;
        for (Policy p: policies){
            assert (p.getPolicy_id()==1);
        }

    }


}