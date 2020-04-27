package acceptance;

import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import ServiceLayer.GuestImp;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class uc_2_7_Test {
    GuestImp gi;
    private System system;
    private Guest guest;

    @Before
    public void setUp() throws IOException {
        gi = new GuestImp();
        system = System.getSystem();
        Store s1 = new Store("sotre1");
        Product p1 = new Product("bmba", 1, 2, s1);
        Product p2 = new Product("besli", 12, 2, s1);
        Product p3 = new Product("twix", 3, 2, s1);
        s1.getProduct_list().add(p1);
        s1.getProduct_list().add(p2);
        s1.getProduct_list().add(p3);

        Store s2 = new Store("sotre2");
        Product p5 = new Product("bmba", 11, 2, s2);
        Product p4 = new Product("chips", 3, 2, s2);
        Product p6 = new Product("twix", 5, 2, s2);
        s2.getProduct_list().add(p1);
        s2.getProduct_list().add(p2);
        s2.getProduct_list().add(p3);

        Store s3 = new Store("sotre3");
        Product p7 = new Product("bmba", 4, 2, s3);
        Product p8 = new Product("besli", 4, 2, s3);
        Product p9 = new Product("twix", 6, 2, s3);
        s3.getProduct_list().add(p1);
        s3.getProduct_list().add(p2);
        s3.getProduct_list().add(p3);

        system.getStore_list().add(s1);
        system.getStore_list().add(s2);
        system.getStore_list().add(s3);

        guest = new Guest(1);
        system.getGuest_list().add(guest);
    }


    @Test
    public void successScenario(){
        gi.save_products(1,"bmba","store1");
        gi.save_products(1,"twix","store3");
        gi.save_products(1,"chips","store2");

        assertTrue(gi.watch_products_in_cart(1).contains("bmba"));
        assertTrue(gi.watch_products_in_cart(1).contains("twix"));
        assertTrue(gi.watch_products_in_cart(1).contains("chips"));
        assertFalse(gi.watch_products_in_cart(1).contains("besli"));

    }
    @Test
    public void failScenario1(){
        guest = new Guest(2);
        system.getGuest_list().add(guest);
        assert gi.watch_products_in_cart(2).size()==0;
    }

}
