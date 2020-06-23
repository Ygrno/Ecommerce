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
    public static void before() throws IOException {
        s =new Store("my_food");
        p1 = new Product("bamba",10,2,s);
        p2 = new Product("grill",10,4,s);
        //ProductBuyPolicy(policy_id,description, product_name, minProducts, maxProducts);
        a = new ProductBuyPolicy(5,"simple policy 1", "bamba", 1, 5);
        //BagBuyPolicy(policy_id,description, minCost, maxCost, min_quantity, max_quantity);
        b = new BagBuyPolicy(2,"simple policy 2", 0, 1000, 2, 3);
        c = new ComplexBuyPolicy(3, "simple policy 3", Logicaloperation.and);

        assert (a!=null) ;
        assert (b!=null) ;
        assert (c!=null) ;

        c.getPolicies_list().add(a);
        c.getPolicies_list().add(b);

        shoppingBag = new ShoppingBag(null);

        List<Product> productList = shoppingBag.getProducts();
        productList.add(p1);
        productList.add(p2);

    }

    @Test
    public void check_validate(){ assertTrue(shoppingBag.getProducts()!=null);
       assertTrue(a.validate(shoppingBag));
       assertTrue(b.validate(shoppingBag));
       assertFalse(c.validate(shoppingBag));
    }

}
