package integration.tests;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.*;
import DomainLayer.User.Subscriber;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DiscountsTest {

    private static Store s;
    private static Product p1, p2;
    private static DiscountComponent a,b,c,d;
    private static PurchaseProcess purchaseProcess;
    private static ShoppingBag shoppingBag;


    @BeforeClass
    public static void init(){

        s =new Store("food");

        p1 = new Product("bamba",10,2,s);
        p2 = new Product("grill",10,4,s);

        a = new VisibleDiscount("a",0.5,12062020,p1);
        b = new VisibleDiscount("b",0.25,12062020,p2);

        p1.setBuy_amount(2);
        c = new ConditionedDiscount("c",0.5,12062020, Condition.IF_NUMBER_OF_PRODUCTS,s,p1,3,0);

        d = new ComplexDiscount("d");
        d.add_OnlyOne(c);
        d.add_OnlyOne(b);


        shoppingBag = new ShoppingBag(null);

        List<Product> productList = shoppingBag.getProducts();
        productList.add(p1);
        productList.add(p2);

        purchaseProcess = new PurchaseProcess(new Subscriber("moti","loohim"),s,shoppingBag);

    }

    @Test
    public void check_validate(){
        assertTrue(a.validate(purchaseProcess));
        assertTrue(b.validate(purchaseProcess));
        assertFalse(c.validate(purchaseProcess));
        assertTrue(d.validate(purchaseProcess));

    }

    @Test
    public void check_calculate(){
        a.calculate_discount(purchaseProcess);
        assertEquals(5,((VisibleDiscount)a).getFinal_price(),0.001);

        d.calculate_discount(purchaseProcess);
        assertNotEquals(5, c.getFinal_price());
        assertEquals(7.5,b.getFinal_price(),0.001);

    }





}
