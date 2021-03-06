package integration.tests;

import DomainLayer.InternalService.Logicaloperation;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.*;
import DomainLayer.User.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BuyPolicyTest {
    private static Store s;
    private static Product p1, p2;
    private static ProductBuyPolicy a;
    private static BagBuyPolicy b;
    private static ComplexBuyPolicy c;
    private static PurchaseProcess purchaseProcess;
    private static ShoppingBag shoppingBag;
    private static User user;

    @BeforeClass
    public static void before() throws Exception {
        s =new Store("my_food");
        p1 = new Product("bamba",10,2,s);
        p2 = new Product("grill",10,4,s);
        //ProductBuyPolicy(policy_id,description, product_name, minProducts, maxProducts);
        a = new ProductBuyPolicy(5,"simple policy 1", "bamba", 2, 3);
        //BagBuyPolicy(policy_id,description, minCost, maxCost, min_quantity, max_quantity);
        b = new BagBuyPolicy(2,"simple policy 2", 0, 1000, 2, 4);
        c = new ComplexBuyPolicy(3, "simple policy 3", Logicaloperation.and);

        assert (a!=null) ;
        assert (b!=null) ;
        assert (c!=null) ;

        c.getPolicies_list().add(a);
        c.getPolicies_list().add(b);

        shoppingBag = new ShoppingBag(null);

        List<Product> productList = shoppingBag.getProducts();
        productList.add(p1);
        assertFalse(b.validate(shoppingBag)); //check min in bag
      //  assertFalse(a.validate(shoppingBag)); //1 p check min in product
        productList.add(p1);
        assertTrue(a.validate(shoppingBag)); //check min 2 p1 in product
        productList.add(p1);
        productList.add(p1);
        assertFalse(a.validate(shoppingBag)); //4 p1 check max in product
        productList.remove(p1);
        //productList.add(p2);
        productList.add(p2);
        productList.add(p2);
        assertFalse(b.validate(shoppingBag)); // total 5 product - over the maximum (4)
        productList.remove(p1);


    }

    @Test
    public void check_validate(){ assertTrue(shoppingBag.getProducts()!=null);
       assertTrue(a.validate(shoppingBag));
       assertTrue(b.validate(shoppingBag));
       assertTrue(c.validate(shoppingBag));
    }

}
