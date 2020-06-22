package ServiceLayer;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;

import Logs.LogErrors;
import Logs.LogInfo;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.List;

public class SubscriberImp implements ISubscriber {

    LogInfo my_logInfo = LogInfo.getLogger();
    LogErrors my_logError = LogErrors.getLogger();


    public SubscriberImp() {
    }

    @Override
    public String[][] view_products_information_store(String store_name) {
        my_logInfo.logger.info("view_products_information_store");
        String[][] products_arr;
        if(!SystemManage_Facade.is_initialized()) return null;
        products_arr = SystemManage_Facade.get_products_of_store(store_name);
        return products_arr;
    }

    @Override
    public HashMap<String, Double> search_products(String product_name) {
        my_logInfo.logger.info("search_products");
        if(!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return null;
        }
        return SystemManage_Facade.searchProductStores(product_name);
    }

    @Override
    public boolean save_products(String userName,String product_name, String store_name, int amount) {
        my_logInfo.logger.info("save_products");
        if(!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("Save Products failed, system wasn't initialized");
            return false;
        }
        return SystemManage_Facade.saveProductForSubscriber(userName,product_name,store_name, amount);
    }

    @Override
    public List<JSONObject> watch_products_in_cart(String userName) throws JSONException {
        my_logInfo.logger.info("watch_products_in_cart");
        if(!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return null;
        }

        return SystemManage_Facade.getProductsInCartForSubscriber(userName);
    }

    @Override
    public boolean remove_product_from_cart(String id, String product_name, String store_name) {
        my_logInfo.logger.info("remove_product_from_cart");
        if(!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        return SystemManage_Facade.removeProductFromCart(id,product_name,store_name);
    }


    @Override
    public boolean buy_products_in_cart(String id, String buyerName, String creditCardNumber, String expireDate, int cvv) throws Exception {
        my_logInfo.logger.info("buy_products_in_cart");
        if(!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
//        if(discount > 1 || discount < 0){
//            return false;
//        }
        if(expireDate.length() != 5){
            my_logError.logger.severe("expireDate length wasn't 5");
            return false;
        }
        if(creditCardNumber.length()!=16) {
            my_logError.logger.severe("creditCardNumber length wasn't 16");
            return false;
        }
        if(cvv>=1000) {
            my_logError.logger.severe("cvv lower than 1000");
            return false;
        }
        double price=SystemManage_Facade.getPriceOfCart(id);
        String[] dealDetails = {id,String.valueOf(price),buyerName,creditCardNumber,expireDate, String.valueOf(cvv)};
        return SystemManage_Facade.buy(dealDetails);
    }


    @Override
    public boolean sign_out(String user_name) {
        my_logInfo.logger.info("sign_out");
        if(!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }

        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)){
            SubscribersManage_Facade.subscriber_login_state(user_name,false);
            return true;
        }
        my_logError.logger.severe("sign_out failed!");
        return false;
    }

    @Override
    public boolean open_store(String user_name, String store_name) {
        my_logInfo.logger.info("open_store");
        if(!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)){
            if(!SubscribersManage_Facade.create_store(user_name,store_name)) return false;
                return true;
        }
        my_logError.logger.severe("open_store failed!");
        return false;
    }

    @Override
    public boolean write_review(String user_name, String product_name, String store_name, String review_data, int rank) {
        my_logInfo.logger.info("write_review");
        if(!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name))
            return SubscribersManage_Facade.addProductReview( user_name,  product_name,  store_name,  review_data, rank);
        my_logError.logger.severe("write_review failed!");
        return false;
    }

    @Override
    public List<JSONObject> getNotifications(String userName) {
        my_logInfo.logger.info("getNotifications");
        if (!SystemManage_Facade.is_initialized())
        {
            my_logError.logger.severe("System not initialized");
            return null;
        }


        if (SystemManage_Facade.find_subscriber(userName) && SubscribersManage_Facade.check_if_logged_in(userName)) {
            return  SubscribersManage_Facade.getNotifications(userName);
        }
        my_logError.logger.severe("getNotifications failed!");
        return null;
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
        my_logInfo.logger.info("send_query_to_store");
        if(!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            SystemManage_Facade.Add_Query(user_name, Query);
            return true;
        }
        my_logError.logger.severe("send_query_to_store failed!");
        return false;
    }

    @Override
    public boolean fill_complaint() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public String view_purchase_history(String user_name) {
        my_logInfo.logger.info("view_purchase_history");
        if(!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return null;
        }
        if(SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)){
            return SystemManage_Facade.get_subscriber_purchase_process(user_name);
        }
        my_logError.logger.severe("view_purchase_history failed!");
        return null;
    }

    @Override
    public boolean edit_account() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }
}
