package integration.tests;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import DomainLayer.Store.BuyPolicy;
import DomainLayer.Store.ComplexBuyPolicy;
import DomainLayer.Store.ProductBuyPolicy;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class SubscribersManage_FacadeTest {


    @BeforeClass
    public static void setUp() throws Exception {
        SystemManage_Facade.init_system();
        System.getSystem().add_subscriber(new Subscriber("test_sub","test_pass"));
    }


    @Test
    public void subscriber_login_state() throws Exception {
        SubscribersManage_Facade.subscriber_login_state("test_sub", true);
        assert SubscribersManage_Facade.check_if_logged_in("test_sub");
        SubscribersManage_Facade.subscriber_login_state("test_sub", false);
        assert !SubscribersManage_Facade.check_if_logged_in("test_sub");

    }

    @Test
    public void check_if_logged_in() throws Exception {
        SubscribersManage_Facade.subscriber_login_state("test_sub", true);
        assert SubscribersManage_Facade.check_if_logged_in("test_sub");
        SubscribersManage_Facade.subscriber_login_state("test_sub", false);
        assert !SubscribersManage_Facade.check_if_logged_in("test_sub");
    }

    @Test
    public void create_store() throws Exception {
        SubscribersManage_Facade.create_store("test_sub", "test_store");
        assert System.getSystem().get_store("test_store") != null;
        assert System.getSystem().get_store("test_sub_not_existed") == null;
    }

    @Test
    public void add_product_to_store() throws Exception {
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
    public void create_store_simple_buyPolicy() throws Exception {
        Store s = System.getSystem().get_store("test_store");
        if(s == null){
            SubscribersManage_Facade.create_store("test_sub", "test_store");
            s = System.getSystem().get_store("test_store");
        }
        SubscribersManage_Facade.add_product_to_store("test_sub", "test_store", "test_product", 2,3);

        Boolean b = SubscribersManage_Facade.create_store_simple_buyPolicy("test_sub", "test_store", 2, "simple product policy",1, "test_product", 1, 5,  -1, -1,0,100,-1);

        assert b ;
        List<BuyPolicy> policies = s.getBuyPolicyList();
        assert policies != null;
        assert policies.size()==1;
        //assert policies.get(0).getPolicy_id()==1;
        System.getSystem().getStore_list().remove(s);
    }

    @Test
    public void remove_store_buyPolicy() throws Exception {
        Store s = System.getSystem().get_store("test_store");
        if(s == null){
            SubscribersManage_Facade.create_store("test_sub", "test_store");
            s = System.getSystem().get_store("test_store");
        }
        List<BuyPolicy> policies = s.getBuyPolicyList();
        SubscribersManage_Facade.add_product_to_store("test_sub", "test_store", "test_product", 2,3);
        SubscribersManage_Facade.create_store_simple_buyPolicy("test_sub", "test_store", 2,"simple policy 1", 1, "test_product", 1, 5,  -1, -1,0,100,-1);
        BuyPolicy p1 = s.getBuyPolicyList().get(0);
        SubscribersManage_Facade.create_store_simple_buyPolicy("test_sub", "test_store", 2, "simple policy 1",2, "test_product", 1, 5,  -1, -1,0,100,-1);
        BuyPolicy p2 = s.getBuyPolicyList().get(1);
        assert s.getBuyPolicyList().size()==2;
        Boolean b = SubscribersManage_Facade.remove_store_buyPolicy("test_sub", "test_store", p2.getPolicy_id());
       assert b;
  //      assert policies.size()==1;

        assert policies != null;
        assert policies.size()==1;
        assert policies.get(0).getPolicy_id()==p1.getPolicy_id();
        System.getSystem().getStore_list().remove(s);
    }


    @Test
    public void edit_store_simple_buyPolicy()throws Exception{
        Store s = System.getSystem().get_store("test_s1");
        if(s == null){
            SubscribersManage_Facade.create_store("test_sub", "test_s1");
            s = System.getSystem().get_store("test_s1");
        }
        SubscribersManage_Facade.add_product_to_store("test_sub", "test_s1", "test_product", 2,3);
        Boolean a = SubscribersManage_Facade.create_store_simple_buyPolicy("test_sub", "test_s1", 2, "simple policy 1",1, "test_product", 1, 5,  -1, -1,0,100,-1);
        Boolean aa = SubscribersManage_Facade.create_store_simple_buyPolicy("test_sub", "test_s1", 2, "simple policy 2",2, "test_product", 0, 0,  -1, -1,0,100,-1);
        assert a;
        assert aa;
        BuyPolicy p2 = s.getBuyPolicyList().get(1);
        Boolean b = SubscribersManage_Facade.edit_store_simple_buyPolicy("test_sub", "test_s1", 2, "simple policy 2", p2.getPolicy_id(), "test_product", 0, 0,  -1, -1,0,200,-1);
        assert b;
        List<BuyPolicy> policies = s.getBuyPolicyList();
        assert policies != null;
        assert policies.size()==2;
        ProductBuyPolicy p = (ProductBuyPolicy) policies.get(1);
       // assert p.getPolicy_id()==2;
        assert p.getMaxProducts()==200;
        System.getSystem().getStore_list().remove(s);
    }

    @Test
    public void create_store_complex_buyPolicy() throws Exception {
        Store s = System.getSystem().get_store("test_store");
        if(s == null){
            SubscribersManage_Facade.create_store("test_sub", "test_store");
            s = System.getSystem().get_store("test_store");
        }
        SubscribersManage_Facade.add_product_to_store("test_sub", "test_store", "test_product", 2,3);

        SubscribersManage_Facade.create_store_simple_buyPolicy("test_sub", "test_store", 2, "simple policy 1",1, "test_product", 1, 5,  -1, -1,0,100,-1);
        SubscribersManage_Facade.create_store_simple_buyPolicy("test_sub", "test_store", 2, "simple policy 2",2, "test_product", 1, 5,  -1, -1,0,100,-1);
        BuyPolicy p1 = s.getBuyPolicyList().get(0);
        BuyPolicy p2 = s.getBuyPolicyList().get(1);
        Boolean b = SubscribersManage_Facade.create_store_complex_buyPolicy("test_sub", "test_store","complex policy 3",3, new int[]{p1.getPolicy_id(), p2.getPolicy_id()}, 1);

        assert b ;
        List<BuyPolicy> policies = s.getBuyPolicyList();
        assert policies != null;
        assert policies.size()==3;
        ComplexBuyPolicy p = (ComplexBuyPolicy) policies.get(2);
        assert p.getPolicies_list().size()==2;
        assert p.getPolicies_list().get(0).getPolicy_id()==p1.getPolicy_id();
        assert p.getPolicies_list().get(1).getPolicy_id()==p2.getPolicy_id();
        System.getSystem().getStore_list().remove(s);
    }

    @Test
    public void edit_store_complex_buyPolicy() throws Exception {
        Store s = System.getSystem().get_store("test_store");
        if(s == null){
            SubscribersManage_Facade.create_store("test_sub", "test_store");
            s = System.getSystem().get_store("test_store");
        }
        SubscribersManage_Facade.add_product_to_store("test_sub", "test_store", "test_product", 2,3);

        SubscribersManage_Facade.create_store_simple_buyPolicy("test_sub", "test_store", 2, "simple policy 1",1, "test_product", 1, 5,  -1, -1,0,100,-1);
        SubscribersManage_Facade.create_store_simple_buyPolicy("test_sub", "test_store", 2,"simple policy 2", 2, "test_product", 1, 5,  -1, -1,0,100,-1);
        BuyPolicy p1 = s.getBuyPolicyList().get(0);
        BuyPolicy p2 = s.getBuyPolicyList().get(1);

        SubscribersManage_Facade.create_store_complex_buyPolicy("test_sub", "test_store","complex policy 3",3, new int[]{p1.getPolicy_id(), p2.getPolicy_id()}, 1);
        BuyPolicy p3 = s.getBuyPolicyList().get(2);
        Boolean b = SubscribersManage_Facade.edit_store_complex_buyPolicy("test_sub", "test_store",p3.getPolicy_id(), p2.getPolicy_id(), "remove");
        assert b ;
        List<BuyPolicy> policies = s.getBuyPolicyList();
        assert policies.size()==3;
        ComplexBuyPolicy p = (ComplexBuyPolicy) policies.get(2);
        assert p.getPolicies_list().size()==1;
        assert p.getPolicies_list().get(0).getPolicy_id()==p1.getPolicy_id();

        b = SubscribersManage_Facade.edit_store_complex_buyPolicy("test_sub", "test_store",p3.getPolicy_id(), p2.getPolicy_id(), "add");

        assert b ;
        List<BuyPolicy> policies2 = s.getBuyPolicyList();
        assert policies2.size()==3;
        p = (ComplexBuyPolicy) policies.get(2);
        assert p.getPolicies_list().size()==2;
        assert p.getPolicies_list().get(0).getPolicy_id()==p1.getPolicy_id();
        assert p.getPolicies_list().get(1).getPolicy_id()==p2.getPolicy_id();
        System.getSystem().getStore_list().remove(s);

    }

}