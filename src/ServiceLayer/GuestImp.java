package ServiceLayer;

import DomainLayer.*;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import Encryption.EncryptImp;

import java.util.ArrayList;
import java.util.List;


public class GuestImp implements IGuest {

    @Override
    public boolean sign_up(String user_name, String password) {

        if(!SystemManage_Facade.is_initialized()) return false;
        EncryptImp encryption = new EncryptImp();
        if(!encryption.connect()) return false;
        password = encryption.encrypt(password);



        if(!SystemManage_Facade.find_subscriber(user_name)) {
            SystemManage_Facade.add_subscriber(user_name, password);
            return true;
        }

        return false;
    }

    @Override
    public boolean login(String user_name, String password) {

        if(user_name.equals("Admin") && password.equals("Password")) SystemManage_Facade.init_system();
        if(!SystemManage_Facade.is_initialized()) return false;
        if(SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.check_password(user_name,password)){
            SubscribersManage_Facade.subscriber_login_state(user_name,true);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> view_products_information_store(String store_name) {
        List<Product> products = null;
        if(!SystemManage_Facade.is_initialized()) return null;
        products = SystemManage_Facade.get_products_of_store(store_name);
        return products;
    }

    @Override
    public List<Product> search_products(String product_name) {
        List<Product> products = new ArrayList<>();
        if(!SystemManage_Facade.is_initialized()) return null;
        List<Store> stores=SystemManage_Facade.get_stores();
        for(Store store : stores){
            for(Product p : store.getProduct_list()){
                if(p.getName().equals(product_name))
                    products.add(p);
            }
        }
        return products;

    }

    @Override
    public boolean save_products(int id,String product_name, String store_name) {

        if(!SystemManage_Facade.is_initialized()) return false;
        Guest g=SystemManage_Facade.getGuest(id);
        if(g==null)
            g=SystemManage_Facade.addGuest();
        boolean processExist=false;
        Product product=null;
        Store s=SystemManage_Facade.get_store(store_name);
        for(Product prod: s.getProduct_list()){
            if(prod.getName().equals(product_name)){
                product=prod;
            }
        }
        for(PurchaseProcess p:g.getPurchaseProcesslist()){
            if(p.getStore().getName().equals(store_name)){
                p.getShoppingBag().getProducts_names().add(product_name);
                p.getShoppingBag().getProducts().add(product);
                processExist=true;
            }
        }
        if(!processExist){
            PurchaseProcess p=new PurchaseProcess(g,SystemManage_Facade.get_store(store_name),new ShoppingBag(new ArrayList<>()));
            g.getShoppingCart().getShopping_bag_list().add(p.getShoppingBag());
            p.getShoppingBag().getProducts_names().add(product_name);
            p.getShoppingBag().getProducts().add(product);

        }

        return true;
    }

    @Override
    public List<String> watch_products_in_cart(int id) {
        List<String> res= new ArrayList<>();
        if(!SystemManage_Facade.is_initialized()) return null;
        Guest g= SystemManage_Facade.getGuest(id);
        assert g != null;
        ShoppingCart sc=g.getShoppingCart();
        for(ShoppingBag sb : sc.getShopping_bag_list()){
            res.addAll(sb.getProducts_names());
        }
        return res;
    }

    @Override
    public boolean buy_products_in_cart(int id,String buyerName,String creditCardNumber,String expireDate,int cvv,double discount) {
        if(!SystemManage_Facade.is_initialized()) return false;
        Guest g=SystemManage_Facade.getGuest(id);
        assert g != null;
        double price=0;
        for(PurchaseProcess pp : g.getPurchaseProcesslist()){
            for(Product prod : pp.getShoppingBag().getProducts()){
                price+=prod.getPrice();
            }
        }
        price= price*discount;

        DealDetails dd =new DealDetails(price,buyerName,creditCardNumber,expireDate,cvv);
        return SystemManage_Facade.buy(dd);
    }
}
