package ServiceLayer;


import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;


import Encryption.EncryptImp;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GuestImp implements IGuest {

    Log my_log = Log.getLogger();

    public GuestImp() {
    }

    @Override
    public boolean sign_up(String user_name, String password) {

        my_log.logger.info("Sign Up");


        if(!SystemManage_Facade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return false;
        }
        EncryptImp encryption = new EncryptImp();
        if(!encryption.connect()) {
            my_log.logger.warning("System not initialized");
            return false;
        }
        password = encryption.encrypt(password);

        if(!SystemManage_Facade.find_subscriber(user_name)) {
            SystemManage_Facade.add_subscriber(user_name, password);
            this.login(user_name,password);
            return true;
        }

        my_log.logger.severe("Action Failed");
        return false;
    }

    @Override
    public boolean login(String user_name, String password) {
        my_log.logger.info("Login");

        if(user_name.equals("Admin") && password.equals("Password")) SystemManage_Facade.init_system();
        if(!SystemManage_Facade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return false;

        }
        if(SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.check_password(user_name,password)){
            SubscribersManage_Facade.subscriber_login_state(user_name,true);
            return true;
        }
        my_log.logger.severe("Action Failed");
        return false;
    }

    @Override
    public String[][] view_products_information_store(String store_name) {
        my_log.logger.info("view_products_information_store");
        if(!SystemManage_Facade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return null;
        }

        String[][] products_arr;
        if(!SystemManage_Facade.is_initialized()) return null;
        products_arr = SystemManage_Facade.get_products_of_store(store_name);
        return products_arr;
    }

    @Override
    public List<JSONObject> getAllStores() throws JSONException {
        return null;
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
    public int addGuest() {
        return 0;
    }


    @Override
    public boolean save_products(int id,String product_name, String store_name, int amount) {
        my_log.logger.info("save_products");
        if(!SystemManage_Facade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return false;
        }

        return SystemManage_Facade.saveProductForGuest(id,product_name,store_name,amount);
    }

    @Override
    public List<JSONObject> watch_products_in_cart(int id) throws JSONException {
        my_log.logger.info("watch_products_in_cart");
        if(!SystemManage_Facade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return null;
        }
        if(SystemManage_Facade.getGuest(id)==null)
            return null;

        return SystemManage_Facade.getProductsInCartForGuest(id);
    }

    @Override
    public boolean buy_products_in_cart(int id,String buyerName,String creditCardNumber,String expireDate,int cvv) throws JSONException {
        my_log.logger.info("buy_products_in_cart");
        if(!SystemManage_Facade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return false;
        }
//        if(discount > 1 || discount < 0){
//            return false;
//        }
        if(expireDate.length() != 5){
            return false;
        }
        if(creditCardNumber.length()!=16)
            return false;
        if(cvv>=1000)
            return false;

        //check product availability

        double price=SystemManage_Facade.getPriceOfCart(String.valueOf(id));
        String[] dealDetails={String.valueOf(price),buyerName,creditCardNumber,expireDate, String.valueOf(cvv)};
        return SystemManage_Facade.buy(dealDetails);
    }
}
