package integration.tests;

import DomainLayer.InternalService.Logicaloperation;
import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.BagBuyPolicy;
import DomainLayer.Store.ComplexBuyPolicy;
import DomainLayer.Store.ProductBuyPolicy;
import DomainLayer.Store.Store;
import DomainLayer.User.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ComplexBuyPolicyTest {
    private static Store s;
    private static Product p1, p2;
    private static ProductBuyPolicy a;
    private static BagBuyPolicy b;
    private static ComplexBuyPolicy c, d;
    private static PurchaseProcess purchaseProcess;
    private static ShoppingBag shoppingBag;
    private static User user;
    private Boolean boolD;

    @BeforeClass
    public static void before() throws Exception {
        s =new Store("my_food");
        p1 = new Product("bamba",10,2,s);
        p2 = new Product("grill",10,4,s);
        //ProductBuyPolicy(policy_id,description, product_name, minProducts, maxProducts);
        a = new ProductBuyPolicy(5,"simple policy 1", "bamba", 2, 6);
        //BagBuyPolicy(policy_id,description, minCost, maxCost, min_quantity, max_quantity);
        b = new BagBuyPolicy(2,"simple policy 2", 0, 1000, 2, 4);
        c = new ComplexBuyPolicy(3, "simple policy 3", Logicaloperation.and);
        d = new ComplexBuyPolicy(3, "simple policy 3", Logicaloperation.or);

        assert (a!=null) ;
        assert (b!=null) ;
        assert (c!=null) ;
        assert (d!=null) ;

        c.getPolicies_list().add(a);
        c.getPolicies_list().add(b);
        d.getPolicies_list().add(a);
        d.getPolicies_list().add(b);

        shoppingBag = new ShoppingBag(null);

        List<Product> productList = shoppingBag.getProducts();
        //assertTrue(a.validate(shoppingBag));
        productList.add(p1);
        assertFalse(b.validate(shoppingBag)); //check min in bag
        assertFalse(a.validate(shoppingBag)); //1 p check min in product
        productList.add(p2);
        productList.add(p2);
        productList.add(p2);
        assert c.getPolicies_list().size()==2;
        assert (c.getPolicies_list().get(0)).getPolicy_id()==a.getPolicy_id();
        assert (c.getPolicies_list().get(1)).getPolicy_id()==b.getPolicy_id();
        assert (d.getPolicies_list().get(0)).getPolicy_id()==a.getPolicy_id();
        assert (d.getPolicies_list().get(1)).getPolicy_id()==b.getPolicy_id();
        //assertTrue(a.validate(shoppingBag)); //2 p1
        //productList.remove(p2);


    }

    @Test
    public void check_validate(){ assertTrue(shoppingBag.getProducts()!=null);
        assertFalse(a.validate(shoppingBag));
        assertTrue(b.validate(shoppingBag));
        assertFalse(c.validate(shoppingBag));
        boolD = d.validate(shoppingBag);
        assert boolD;
        assertTrue(d.validate(shoppingBag));
    }

}
