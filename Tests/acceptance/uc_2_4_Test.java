package acceptance;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import ServiceLayer.GuestImp;
import ServiceLayer.IGuest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class uc_2_4_Test {
    IGuest guestimp;
    System system;
    Store s1;
    Product p1;
    Product p2;
    Product p3;
    @Before
    public void setUp() throws IOException {
        SystemManage_Facade.init_system();
        guestimp=new GuestImp();
        system=System.getSystem();
        s1=new Store("store1");
        p1=new Product("bmba",1,2,s1);
        p2=new Product("besli",12,2,s1);
        p3=new Product("twix",3,2,s1);
        s1.getProduct_list().add(p1);
        s1.getProduct_list().add(p2);
        s1.getProduct_list().add(p3);
        system.getStore_list().add(s1);
    }
    @Test
    public void successScenario(){
        assert guestimp.view_products_information_store("store1").contains(p1);
        assert guestimp.view_products_information_store("store1").contains(p2);
        assert guestimp.view_products_information_store("store1").contains(p3);

    }
    @Test
    public void failScenario1(){
        assert guestimp.view_products_information_store("store")==null;
    }
    @Test
    public void failScenario2(){
        Store s2=new Store("store2");
        system.getStore_list().add(s2);
        assert guestimp.view_products_information_store("store2").size()==0;
    }
}
