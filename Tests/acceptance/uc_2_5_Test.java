package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import ServiceLayer.GuestImp;
import ServiceLayer.IGuest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class uc_2_5_Test {
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
        p5=new Product("bmba",11,2,s2);
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
        List<Product> l=gi.search_products("bmba");
        assert l.contains(p1);
        assert l.contains(p2);
        assert l.contains(p3);

    }
    @Test
    public void failScenario1(){
        List<Product> l=gi.search_products("aa");
        assert l.size()==0;
    }


}
