package ServiceLayer;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import Logs.LogErrors;
import Logs.LogInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class StoreRoleImp implements IStoreRole {


    LogInfo my_logInfo = LogInfo.getLogger();
    LogErrors my_logError = LogErrors.getLogger();


    @Override
    public boolean add_store_product(String user_name, String store_name, String product_name, double product_price, int product_amount) {
        my_logInfo.logger.info("add_store_product");
        if (!SystemManage_Facade.is_initialized() || product_amount == 0) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_product_to_store(user_name, store_name, product_name, product_price, product_amount);
        }
        my_logError.logger.severe("add_store_product failed!");
        return false;
    }

    @Override
    public boolean edit_store_product(String user_name, String store_name, String product_name,String new_product_name, int product_price, int product_amount) {
        my_logInfo.logger.info("edit_store_product");
        if (!SystemManage_Facade.is_initialized() || product_amount == 0) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.change_product_in_store(user_name, store_name, product_name, new_product_name, product_price, product_amount);
        }
        my_logError.logger.severe("edit_store_product failed!");
        return false;
    }

    @Override
    public boolean remove_store_product(String user_name, String store_name, String product_name) {
        my_logInfo.logger.info("remove_store_product");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_product_in_store(user_name, store_name, product_name);
        } else {
            my_logError.logger.severe("remove_store_product failed!");
            return false;
        }
    }




    public boolean create_store_simple_buyPolicy(String user_name, String store_name, int policy_type, int policy_id, String product_name, int minProducts, int maxProducts, int minCost, int maxCost, int min_quantity, int max_quantity, int day) {
        my_logInfo.logger.info("create simple buy Policy");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.create_store_simple_buyPolicy(user_name, store_name, policy_type, policy_id, product_name, minProducts, maxProducts,minCost,maxCost, min_quantity, max_quantity, day);
        }
        return false;
    }


    public boolean create_store_complex_buyPolicy(String user_name, String store_name, int policy_id, int[] policy_ids, int op) {
        my_logInfo.logger.info("create complex buy Policy");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.create_store_complex_buyPolicy(user_name, store_name, policy_id, policy_ids, op);
        }
        my_logError.logger.severe("create_store_simple_buyPolicy failed!");
        return false;
    }

    public boolean edit_store_simple_buyPolicy(String user_name, String store_name, int type, int policy_id, String product_name, int minProducts, int maxProducts, int minCost, int maxCost, int min_quantity, int max_quantity, int day){
        my_logInfo.logger.info("edit simple buy Policy");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.edit_store_simple_buyPolicy(user_name, store_name, type, policy_id, product_name, minProducts, maxProducts, minCost, maxCost, min_quantity, max_quantity, day);
        }
        my_logError.logger.severe("edit_store_simple_buyPolicy failed!");
        return false;
    }
    public boolean edit_store_complex_buyPolicy(String user_name, String store_name, int policy_id, int new_policy_id, String act){
        my_logInfo.logger.info("edit complex buy Policy");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.edit_store_complex_buyPolicy(user_name, store_name, policy_id, new_policy_id, act);
        }
        my_logError.logger.severe("edit_store_complex_buyPolicy failed!");
        return false;
    }

    public boolean remove_store_buyPolicy(String user_name, String store_name, int policy_id){
        my_logInfo.logger.info("remove buy Policy");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_store_buyPolicy(user_name, store_name, policy_id);
        }
        my_logError.logger.severe("remove_store_buyPolicy failed!");
        return false;
    }

    @Override
    public boolean assign_store_owner(String user_name, String store_name, String user_assign) {
        my_logInfo.logger.info("assign_store_owner");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_owner_to_store(user_name, store_name, user_assign);
        }
        my_logError.logger.severe("assign_store_owner failed!");
        return false;
    }

    @Override
    public boolean remove_store_owner(String user_name, String store_name, String user_assign) {
        my_logInfo.logger.info("remove_store_owner");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_owner_from_store(user_name, store_name, user_assign);
        }
        my_logError.logger.severe("remove_store_owner failed!");
        return false;
    }

    @Override
    public boolean assign_store_manager(String user_name, String store_name, String user_assign) {
        my_logInfo.logger.info("assign_store_manager");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_manager_to_store(user_name, store_name, user_assign);
        }
        my_logError.logger.severe("assign_store_manager failed!");
        return false;
    }



    @Override
    public boolean edit_manager_permissions(String user_name, String store_name,String user_assign , ArrayList<String> permissions) {
        my_logInfo.logger.info("edit_manager_permissions");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.change_permissions_of_manager(user_name, store_name,user_assign , permissions);
        }
        my_logError.logger.severe("edit_manager_permissions failed!");
        return false;
    }

    @Override
    public List<JSONObject> get_user_permissions(String username, String store) throws Exception {
        List<JSONObject> res = new LinkedList<>();
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return null;
        }
        List<String> pms = SystemManage_Facade.get_user_permissions(username, store);
        if(pms != null) {
            for (String p : pms) {
                JSONObject o = new JSONObject();
                o.put("p", p);
                res.add(o);
            }
        }
        JSONObject o = new JSONObject();
        o.put("p", "VIEW_AND_RESPOND_TO_USERS");
        res.add(o);

        return res;
    }

    @Override
    public boolean remove_store_manager(String user_name, String store_name, String user_assign) {
        my_logInfo.logger.info("remove_store_manager");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_manager_from_store(user_name, store_name, user_assign);
        }
        my_logError.logger.severe("remove_store_manager failed!");
        return false;
    }

    @Override
    public boolean close_store(String user_name, String store_name) {
        my_logInfo.logger.info("close_store");
        if (!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean view_and_respond_to_questions() {
        if (!SystemManage_Facade.is_initialized()) return false;
        return false;
    }


    @Override
    public String watch_store_history(String user_name, String store_name) {
        my_logInfo.logger.info("watch_store_history");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return null;
        }
        if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name) && SubscribersManage_Facade.check_if_logged_in(user_name) ) {
            return SubscribersManage_Facade.store_purchase_history(user_name, store_name);
        }
        my_logInfo.logger.info("watch_store_history failed!");
        return null;
    }

    @Override
    public boolean add_visible_discount(String user_name, String store_name, String discount_name, double discount_percentage, int end_of_use_date, String product_name) {
        my_logInfo.logger.info("create_visible_discount");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }

        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            SubscribersManage_Facade.add_visible_discount_to_product(user_name,store_name,product_name,discount_name,discount_percentage,end_of_use_date);

            return true;
        }
        my_logInfo.logger.info("add_visible_discount failed!");
        return false;
    }

    @Override
    public boolean add_conditioned_discount(String user_name, String store_name, String product_name, String discount_name, double discount_percentage, int due_date, int amount, int sum) {
        my_logInfo.logger.info("create_conditioned_discount");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }

        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            SubscribersManage_Facade.add_conditioned_discount_to_product(user_name, store_name, product_name, discount_name, discount_percentage, due_date, amount, sum);
            return true;
        }
        my_logInfo.logger.info("add_conditioned_discount failed!");
        return false;
    }

    @Override
    public boolean add_complex_discount(String user_name, String store_name, String discount_name, String[]discounts, String type, int end_of_use_date) {
        my_logInfo.logger.info("create_conditioned_discount");
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }
        if (discounts.length == 0) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            SubscribersManage_Facade.add_complex_discount(user_name, store_name, discount_name, discounts, type,end_of_use_date);
            return true;
        }
        my_logInfo.logger.info("add_complex_discount failed!");
        return false;
    }

    @Override
    public boolean delete_discount(String user_name, String store_name, String discount_name) {
        my_logInfo.logger.info("delete_discount");

        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return false;
        }

        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            SubscribersManage_Facade.delete_discount(user_name, store_name, discount_name);
            return true;
        }
        my_logInfo.logger.info("delete_discount failed!");
        return false;
    }



    public List<JSONObject> get_policies_ids_in_store(String store_name) throws Exception {
        List<JSONObject> res = new LinkedList<>();
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return null;
        }
        int[] ids = SubscribersManage_Facade.getBuyPolicyIdsList(store_name);
        if(ids != null) {
            for (int id : ids) {
                JSONObject o = new JSONObject();
                o.put("id", id);
                res.add(o);
            }
        }
        return res;
    }

    public List<JSONObject> get_sub_policies_in_complex( String store_name, int policy_id) throws JSONException {
        List<JSONObject> res = new LinkedList<>();
        if (!SystemManage_Facade.is_initialized()) {
            my_logError.logger.severe("System not initialized");
            return null;
        }
        int[] ids = SubscribersManage_Facade.getPoliciesInComplex(store_name, policy_id);
        if(ids != null) {
            for (int id : ids) {
                JSONObject o = new JSONObject();
                o.put("id", id);
                res.add(o);
            }
        }
        return res;
    }
}
