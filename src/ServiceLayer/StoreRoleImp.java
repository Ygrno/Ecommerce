package ServiceLayer;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class StoreRoleImp implements IStoreRole {

    Log my_log = Log.getLogger();

    public StoreRoleImp() {
    }


    @Override
    public boolean add_store_product(String user_name, String store_name, String product_name, int product_price, int product_amount) {
        my_log.logger.info("add_store_product");
        if (!SystemManage_Facade.is_initialized() || product_amount == 0) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_product_to_store(user_name, store_name, product_name, product_price, product_amount);
        }
        return false;
    }

    @Override
    public boolean edit_store_product(String user_name, String store_name, String product_name, String new_product_name, int product_price, int product_amount) {
        my_log.logger.info("edit_store_product");
        if (!SystemManage_Facade.is_initialized() || product_amount == 0) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.change_product_in_store(user_name, store_name, product_name, new_product_name, product_price, product_amount);
        }
        return false;
    }

    @Override
    public boolean remove_store_product(String user_name, String store_name, String product_name) {
        my_log.logger.info("remove_store_product");
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_product_in_store(user_name, store_name, product_name);
        } else
            return false;
    }


    @Override
    public boolean add_store_visible_discount(String user_name, String store_name, String product_name, String discount_name, double discount_percentage, int due_date) {
        my_log.logger.info("Add visible discount");
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_visible_discount_to_product(user_name, store_name, product_name, discount_name, discount_percentage, due_date);
        }


        return false;
    }

    public boolean add_store_simple_buyPolicy(String user_name, String store_name, int policy_type, int policy_id, String product_name, int minProducts, int maxProducts, int minCost, int maxCost, int min_quantity, int max_quantity, int day) {
        my_log.logger.info("Add simple buy Policy");
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.create_store_simple_policy(user_name, store_name, policy_type, policy_id, product_name, minProducts, maxProducts, minCost, maxCost, min_quantity, max_quantity, day);
        }
        return false;
    }

    public boolean add_store_complex_buyPolicy(String user_name, String store_name, int policy_type, int policy_id, String product_name, int minProducts, int maxProducts, int minCost, int maxCost, int min_quantity, int max_quantity, int day, int op) {
        my_log.logger.info("Add complex buy Policy");
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.create_store_complex_policy(user_name, store_name, policy_type, policy_id, product_name, minProducts, maxProducts, minCost, maxCost, min_quantity, max_quantity, day, op);
        }
        return false;
    }

    public boolean add_simple_policy_to_complexPolicy(String user_name, String store_name, int policy_type, int policy_id, String product_name, int minProducts, int maxProducts, int minCost, int maxCost, int min_quantity, int max_quantity, int day) {
        my_log.logger.info("Add buy policy to complex buy Policy");
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_simple_buyPolicy_to_complex_policy(user_name, store_name, policy_type, policy_id, product_name, minProducts, maxProducts, minCost, maxCost, min_quantity, max_quantity, day);
        }
        return false;
    }

    @Override
    public boolean assign_store_owner(String user_name, String store_name, String user_assign) {
        my_log.logger.info("assign_store_owner");
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_owner_to_store(user_name, store_name, user_assign);
        }
        return false;
    }

    @Override
    public boolean remove_store_owner(String user_name, String store_name, String user_assign) {
        my_log.logger.info("remove_store_owner");
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_owner_from_store(user_name, store_name, user_assign);
        }
        return false;
    }

    @Override
    public boolean assign_store_manager(String user_name, String store_name, String user_assign) {
        my_log.logger.info("assign_store_manager");
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_manager_to_store(user_name, store_name, user_assign);
        }
        return false;
    }


    @Override
    public boolean edit_manager_permissions(String user_name, String store_name, String user_assign, ArrayList<String> permissions) {
        my_log.logger.info("edit_manager_permissions");
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name)) {
            return SubscribersManage_Facade.change_permissions_of_manager(user_name, store_name, user_assign, permissions);
        }
        return false;
    }

    @Override
    public List<JSONObject> get_user_permissions(String username, String store) throws Exception {
        List<JSONObject> res = new LinkedList<>();
        if (!SystemManage_Facade.is_initialized()) return res;

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
        my_log.logger.info("remove_store_manager");
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_manager_from_store(user_name, store_name, user_assign);
        }
        return false;
    }

    @Override
    public boolean close_store(String user_name, String store_name) {
        my_log.logger.info("close_store");
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
        my_log.logger.info("watch_store_history");
        if (!SystemManage_Facade.is_initialized()) return null;
        if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.store_purchase_history(user_name, store_name);
        }
        return null;
    }

}
