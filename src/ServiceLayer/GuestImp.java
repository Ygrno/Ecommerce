package ServiceLayer;


import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;


import Encryption.EncryptProxy;
import Logs.LogErrors;
import Logs.LogInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;


public class GuestImp implements IGuest {

    LogInfo my_logInfo = LogInfo.getLogger();
    LogErrors my_logError = LogErrors.getLogger();

    public GuestImp() {
    }

    @Override
    public boolean sign_up(String user_name, String password) throws Exception {

        my_logInfo.logger.info("Sign Up");


        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }

        EncryptProxy encryption = EncryptionDriver.getEncryption();
        if(!encryption.init()) {
            my_logError.logger.severe("Encryption not initialized");
            return false;
        }

        try {
            password = encryption.encrypt(password);
        } catch (Exception e) {
            my_logError.logger.severe("Password Encryption failed");
        }

        if(!SystemManage_Facade.find_subscriber(user_name)) {
            if(!SystemManage_Facade.add_subscriber(user_name, password)) return false;
            if(user_name.equals("Admin")) SystemManage_Facade.promote_to_manager(user_name,password);
            SubscribersManage_Facade.subscriber_login_state(user_name,true);
            return true;
        }

        my_logError.logger.severe("Password Encryption failed");
        return false;
    }

    @Override
    public boolean login(String user_name, String password) throws Exception {
        my_logInfo.logger.info("Login");

        //if(user_name.equals("Admin") && password.equals("Password")) SystemManage_Facade.init_system();
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }

        EncryptProxy encryption = EncryptionDriver.getEncryption();
        if(!encryption.init()) {
            my_logError.logger.severe("Encryption not initialized");
            return false;
        }

        try {
            password = encryption.encrypt(password);
        } catch (Exception e) {
            my_logError.logger.severe("Password Encryption failed");
        }

        if(SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.check_password(user_name,password)){
            SubscribersManage_Facade.subscriber_login_state(user_name,true);
            return true;
        }
        my_logError.logger.severe("login failed!");
        return false;
    }

    @Override
    public String[][] view_products_information_store(String store_name) {
        my_logInfo.logger.info("view_products_information_store");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return null;
        }
        String[][] products_arr;
        products_arr = SystemManage_Facade.get_products_of_store(store_name);
        return products_arr;
    }

    @Override
    public List<JSONObject> getAllStores() throws JSONException {
        return SystemManage_Facade.getAllStores();
    }

    @Override
    public HashMap<String, Double> search_products(String product_name) {
        my_logInfo.logger.info("search_products");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
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
        my_logInfo.logger.info("save_products");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }

        return SystemManage_Facade.saveProductForGuest(id,product_name,store_name,amount);
    }

    @Override
    public List<JSONObject> watch_products_in_cart(int id) throws JSONException {
        my_logInfo.logger.info("watch_products_in_cart");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return null;
        }
        if(SystemManage_Facade.getGuest(id)==null) {
            my_logError.logger.severe("Guest wasn't found!");
            return null;
        }
        return SystemManage_Facade.getProductsInCartForGuest(id);
    }

    @Override
    public boolean remove_product_from_cart(int id, String product_name, String store_name) {
        my_logInfo.logger.info("remove_product_from_cart");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        return SystemManage_Facade.removeProductFromCart(String.valueOf(id),product_name,store_name);
    }

    @Override
    public boolean buy_products_in_cart(int id,String buyerName,String creditCardNumber,String expireDate,int cvv) throws Exception {
        my_logInfo.logger.info("buy_products_in_cart");
        if (!SystemManage_Facade.is_initialized()) {
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
        //check product availability

        double price=SystemManage_Facade.getPriceOfCart(String.valueOf(id));
        String[] dealDetails={String.valueOf(id),String.valueOf(price),buyerName,creditCardNumber,expireDate, String.valueOf(cvv)};
        boolean b;
        synchronized (this) {
            if(!SystemManage_Facade.checkIfCanBuy(String.valueOf(id))) return false;
            b = SystemManage_Facade.buy(dealDetails);
        }
        return b;
    }

    @Override
    public double getTotalPriceOfCart(String userName){
        return SystemManage_Facade.getPriceOfCart(userName);
    }
}
