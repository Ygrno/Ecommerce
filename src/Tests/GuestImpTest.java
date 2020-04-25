package Tests;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import ServiceLayer.GuestImp;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class GuestImpTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(GuestImp.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    GuestImp gi;
    private System system;
    private Guest guest;

    @Before
    public void setUp(){
        gi=new GuestImp();
        system=System.getSystem();
        Store s1=new Store("sotre1");
        Product p1=new Product("bmba",1,2,s1);
        Product p2=new Product("besli",12,2,s1);
        Product p3=new Product("twix",3,2,s1);
        s1.getProduct_list().add(p1);
        s1.getProduct_list().add(p2);
        s1.getProduct_list().add(p3);

        Store s2=new Store("sotre2");
        Product p5=new Product("bmba",11,2,s2);
        Product p4=new Product("chips",3,2,s2);
        Product p6=new Product("twix",5,2,s2);
        s2.getProduct_list().add(p1);
        s2.getProduct_list().add(p2);
        s2.getProduct_list().add(p3);

        Store s3=new Store("sotre3");
        Product p7=new Product("bmba",4,2,s3);
        Product p8=new Product("besli",4,2,s3);
        Product p9=new Product("twix",6,2,s3);
        s3.getProduct_list().add(p1);
        s3.getProduct_list().add(p2);
        s3.getProduct_list().add(p3);

        system.getStore_list().add(s1);
        system.getStore_list().add(s2);
        system.getStore_list().add(s3);

        guest=new Guest(1);
        system.getGuest_list().add(guest);


    }

    public void view_products_information_store() {
        assertEquals(system.get_store("store1").getProduct_list(), gi.view_products_information_store("store1"));
        assertEquals(system.get_store("store2"), gi.view_products_information_store("store2"));
        assertEquals(system.get_store("store3"), gi.view_products_information_store("store3"));
        assertEquals(null, gi.view_products_information_store("store10"));
    }

    @org.junit.Test
    public void search_products() {
        assertEquals(3, gi.search_products("bmba").size());
        assertEquals(2, gi.search_products("besli").size());
        assertEquals(3, gi.search_products("twix").size());
        assertEquals(1, gi.search_products("chips").size());
        assertEquals(0, gi.search_products("bueno").size());
    }

    @org.junit.Test
    public void save_products() {
        gi.save_products(1,"bmba","store1");
        gi.save_products(1,"twix","store3");
        gi.save_products(1,"chips","store2");
        boolean b1=false,b2=false,b3=false;
        for(ShoppingBag sb:guest.getShoppingCart().getShopping_bag_list()) {
            if (sb.getProducts_names().contains("bmba"))
                b1 = true;
            if(sb.getProducts_names().contains("twix"))
                b2=true;
            if(sb.getProducts_names().contains("chips"))
                b3=true;
        }

        assertTrue(b1);
        assertTrue(b2);
        assertTrue(b3);
    }

    @org.junit.Test
    public void watch_products_in_cart() {
        gi.save_products(1,"bmba","store1");
        gi.save_products(1,"twix","store3");
        gi.save_products(1,"chips","store2");

        assertTrue(gi.watch_products_in_cart(1).contains("bmba"));
        assertTrue(gi.watch_products_in_cart(1).contains("twix"));
        assertTrue(gi.watch_products_in_cart(1).contains("chips"));
        assertFalse(gi.watch_products_in_cart(1).contains("besli"));
    }

    @org.junit.Test
    public void buy_products_in_cart() {
        assertTrue(gi.buy_products_in_cart(1,"mahmoud","1234123412341234","11/26",999,0));
        assertFalse(gi.buy_products_in_cart(0,"mahmoud","1234123412341234","11/26",999,0));
        assertFalse(gi.buy_products_in_cart(1,"mahmoud","1234123412341234","11/26",999,2));
        assertFalse(gi.buy_products_in_cart(1,"mahmoud","12341234123412341","11/26",999,0));
        assertFalse(gi.buy_products_in_cart(1,"mahmoud","1234123412341234","11/261",999,0));
    }


}
