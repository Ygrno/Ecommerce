package ServiceLayer;

import DomainLayer.*;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import DomainLayer.Product;
import DomainLayer.PurchaseProcess;

import java.util.ArrayList;
import java.util.List;

public class SubscriberImp implements ISubscriber {

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
    public boolean save_products(String userName,String product_name, String store_name) {
        Subscriber s=SystemManage_Facade.get_subscriber(userName);
        boolean processExist =false;
        Product product=null;
        Store store = SystemManage_Facade.get_store(store_name);
        if(store == null) return false;
        for(Product prod : store.getProduct_list()) {
            if (prod.getName().equals(product_name))
                product = prod;
        }
        for(PurchaseProcess p:s.getPurchaseProcesslist()){
            if(p.getStore().getName().equals(store_name)){
                p.getShoppingBag().getProducts_names().add(product_name);
                p.getShoppingBag().getProducts().add(product);
                processExist=true;
            }
        }
        if(!processExist){
            PurchaseProcess p=new PurchaseProcess(s,SystemManage_Facade.get_store(store_name),new ShoppingBag(new ArrayList<>()));
            s.getShoppingCart().getShopping_bag_list().add(p.getShoppingBag());
            p.getShoppingBag().getProducts_names().add(product_name);
            p.getShoppingBag().getProducts().add(product);
        }
        return true;
    }

    @Override
    public List<String> watch_products_in_cart(String userName) {
        List<String> res= new ArrayList<>();
        if(!SystemManage_Facade.is_initialized()) return null;
        Subscriber s= SystemManage_Facade.get_subscriber(userName);

        ShoppingCart sc=s.getShoppingCart();
        for(ShoppingBag sb : sc.getShopping_bag_list()){
            res.addAll(sb.getProducts_names());
        }
        return res;
    }

    @Override
    public boolean buy_products_in_cart(String id, String buyerName, String creditCardNumber, String expireDate, int cvv, double discount) {
        if(discount > 1 || discount < 0){
            return false;
        }
        if(expireDate.length() != 5){
            return false;
        }
        if(creditCardNumber.length()!=16)
            return false;
        if(cvv>=1000)
            return false;

        Subscriber g=SystemManage_Facade.get_subscriber(id);
        if(g==null)
            return false;
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


    @Override
    public boolean sign_out(String user_name) {
        if(!SystemManage_Facade.is_initialized()) return false;

        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)){
            SubscribersManage_Facade.subscriber_login_state(user_name,false);
            return true;
        }
        return false;
    }

    @Override
    public boolean open_store(String user_name, String store_name) {
        if(!SystemManage_Facade.is_initialized()) return false;

        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)){
            SubscribersManage_Facade.create_store(user_name,store_name);
            return true;
        }
        return false;
    }

    @Override
    public boolean write_review(String user_name, String product_name, String store_name, String review_data, int rank) {
        if(!SystemManage_Facade.is_initialized())
            return false;
        return SystemManage_Facade.addProductReview( user_name,  product_name,  store_name,  review_data, rank);
    }

    @Override
    public boolean rank_product() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean rank_store() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public void send_query_to_store(String user_name,String Query) {//add test
        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            SystemManage_Facade.Add_Query(user_name, Query);
        }
    }

    @Override
    public boolean fill_complaint() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public List<PurchaseProcess> view_purchase_history(String user_name) {
        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)){
            return SystemManage_Facade.View_purchase(user_name);
        }
        return null;
    }

    @Override
    public boolean edit_account() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }
}
