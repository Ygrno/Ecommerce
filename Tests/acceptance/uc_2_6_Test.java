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
import java.util.List;

import static org.junit.Assert.assertTrue;

public class uc_2_6_Test {

    GuestImp gi;
    private System system;
    private Guest guest;
    Product p1;
    Product p2;
    Product p3;
    Product p4;
    Product p5;
    Product p6;
    Product p7;
    Product p8;
    Product p9;

    @Before
    public void setUp() throws IOException {
        gi=new GuestImp();
        system=System.getSystem();
        Store s1=new Store("sotre1");
        p1=new Product("bmba",1,2,s1);
        p2=new Product("besli",12,2,s1);
        p3=new Product("twix",3,2,s1);
        s1.getProduct_list().add(p1);
        s1.getProduct_list().add(p2);
        s1.getProduct_list().add(p3);

        Store s2=new Store("sotre2");
        p5=new Product("bmba",0,2,s2);
        p4=new Product("chips",3,2,s2);
        p6=new Product("twix",5,2,s2);
        s2.getProduct_list().add(p1);
        s2.getProduct_list().add(p2);
        s2.getProduct_list().add(p3);

        Store s3=new Store("sotre3");
        p7=new Product("bmba",4,2,s3);
        p8=new Product("besli",4,2,s3);
        p9=new Product("twix",6,2,s3);
        s3.getProduct_list().add(p1);
        s3.getProduct_list().add(p2);
        s3.getProduct_list().add(p3);

        system.getStore_list().add(s1);
        system.getStore_list().add(s2);
        system.getStore_list().add(s3);

        guest=new Guest(1);
        system.getGuest_list().add(guest);
    }
    @Test
    public void successScenario(){
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
    @Test
    public void failScenario1(){
        assert !gi.save_products(1,"bmba","store2");
    }

}
