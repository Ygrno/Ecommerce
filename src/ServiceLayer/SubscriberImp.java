package ServiceLayer;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import DomainLayer.PurchaseProcess;

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
    public boolean search_products(String product_name) {
        return false;
    }

    @Override
    public boolean save_products(String product_name, String store_name) {
        return false;
    }

    @Override
    public boolean watch_products_in_cart() {
        return false;
    }

    @Override
    public boolean buy_products_in_cart() {
        return false;
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
    public void send_query_to_store(String user_name,String Query) {
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
