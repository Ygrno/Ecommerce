package ServiceLayer;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;

import DomainLayer.PurchaseProcess;


import java.util.HashMap;
import java.util.List;

public class SubscriberImp implements ISubscriber {

    Log my_log = Log.getLogger();

    public SubscriberImp() {
    }

    @Override
    public String[][] view_products_information_store(String store_name) {
        my_log.logger.info("view_products_information_store");
        String[][] products_arr;
        if(!SystemManage_Facade.is_initialized()) return null;
        products_arr = SystemManage_Facade.get_products_of_store(store_name);
        return products_arr;
    }

    @Override
    public HashMap<String, Double> search_products(String product_name) {
        my_log.logger.info("search_products");
        if(!SystemManage_Facade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return null;
        }
        return SystemManage_Facade.searchProductStores(product_name);
    }

    @Override
    public boolean save_products(String userName,String product_name, String store_name) {
        my_log.logger.info("save_products");
        if(!SystemManage_Facade.is_initialized())
            return false;
        return SystemManage_Facade.saveProductForSubscriber(userName,product_name,store_name);
    }

    @Override
    public List<String> watch_products_in_cart(String userName) {
        my_log.logger.info("watch_products_in_cart");
        if(!SystemManage_Facade.is_initialized())
            return null;

        return SystemManage_Facade.getProductsInCartForSubscriber(userName);
    }

    @Override
    public boolean buy_products_in_cart(String id, String buyerName, String creditCardNumber, String expireDate, int cvv, double discount) {
        my_log.logger.info("buy_products_in_cart");
        if(!SystemManage_Facade.is_initialized())
            return false;
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
        double price=SystemManage_Facade.getPriceOfCart(id,discount);
        String[] dealDetails={String.valueOf(price),buyerName,creditCardNumber,expireDate, String.valueOf(cvv)};
        return SystemManage_Facade.buy(dealDetails);
    }


    @Override
    public boolean sign_out(String user_name) {
        my_log.logger.info("sign_out");
        if(!SystemManage_Facade.is_initialized()) return false;

        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)){
            SubscribersManage_Facade.subscriber_login_state(user_name,false);
            return true;
        }
        return false;
    }

    @Override
    public boolean open_store(String user_name, String store_name) {
        my_log.logger.info("open_store");
        if(!SystemManage_Facade.is_initialized()) return false;

        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)){
            SubscribersManage_Facade.create_store(user_name,store_name);
            return true;
        }
        return false;
    }

    @Override
    public boolean write_review(String user_name, String product_name, String store_name, String review_data, int rank) {
        my_log.logger.info("write_review");
        if(!SystemManage_Facade.is_initialized())
            return false;
        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name))
            return SystemManage_Facade.addProductReview( user_name,  product_name,  store_name,  review_data, rank);
        return false;
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
    public boolean send_query_to_store(String user_name,String Query) {//add test
        my_log.logger.info("send_query_to_store");
        if(!SystemManage_Facade.is_initialized())
            return false;
        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            SystemManage_Facade.Add_Query(user_name, Query);
        }
        return true;
    }

    @Override
    public boolean fill_complaint() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public List<PurchaseProcess> view_purchase_history(String user_name) {
        my_log.logger.info("view_purchase_history");
        if(!SystemManage_Facade.is_initialized())
            return null;
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
