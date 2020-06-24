package integration.tests;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.*;
import DomainLayer.User.Subscriber;

import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DiscountsTest {

    private static Store s;
    private static Product p1, p2;
    private static DiscountComponent a,b,c,d;
    private static PurchaseProcess purchaseProcess;
    private static ShoppingBag shoppingBag;


    @BeforeClass
    public static void init() throws Exception {
        //SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        //String dateString = format.format( new Date()   );
        //System.out.println(dateString);
        s = new Store("food");

        p1 = new Product("bamba",10,2,s);
        p2 = new Product("grill",10,4,s);

        a = new VisibleDiscount("a",0.5,30062120,p1);
        b = new VisibleDiscount("b",0.25,12062120,p2);
        //p1.setBuy_amount(2);
        c = new ConditionedDiscount("c",0.5,12062120, Condition.IF_NUMBER_OF_PRODUCTS,s,p1,3,0);
        ArrayList<DiscountComponent> only_one = new ArrayList<>();
        only_one.add(c);
        only_one.add(b);
        d = new ComplexDiscount("d",only_one,"one",12062120);


        shoppingBag = new ShoppingBag(null);

        List<Product> productList = shoppingBag.getProducts();
        productList.add(p1);
        productList.add(new Product(p1.getName(),p1.getPrice(),p1.getSupplied_amount(),p1.getStore()));
        productList.add(p2);

        //purchaseProcess = new PurchaseProcess(new Subscriber("moti","loohim"),s,shoppingBag);

    }

    @Test
    public void check_validate(){
        assertTrue(a.validate(shoppingBag));
        assertTrue(b.validate(shoppingBag));
        assertFalse(c.validate(shoppingBag));
        assertTrue(d.validate(shoppingBag));

    }

    @Test
    public void check_calculate(){
        a.calculate_discount(shoppingBag);
        assertEquals(5,((VisibleDiscount)a).getFinal_price(),0.001);

        d.calculate_discount(shoppingBag);
        assertNotEquals(5, c.getFinal_price());
        assertEquals(7.5,b.getFinal_price(),0.001);

    }





}
