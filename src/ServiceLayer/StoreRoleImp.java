package ServiceLayer;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Store.Policy;
import DomainLayer.User.User;

import java.io.IOException;
import java.util.ArrayList;


public class StoreRoleImp implements IStoreRole {

//    private String user_name, store_name;
//    private String product_name;
//    private String product_price;
//    private String product_amount;

    Log my_log = Log.getLogger();

    public StoreRoleImp() throws IOException {
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
    public boolean edit_store_product(String user_name, String store_name, String product_name,String new_product_name, int product_price, int product_amount) {
        my_log.logger.info("edit_store_product");
        if (!SystemManage_Facade.is_initialized() || product_amount == 0) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.change_product_in_store(user_name, store_name, product_name,new_product_name, product_price, product_amount);
        }
        return false;
    }

    @Override
    public boolean remove_store_product(String user_name, String store_name, String product_name) {
        my_log.logger.info("remove_store_product");
        if (!SystemManage_Facade.is_initialized())  return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_product_in_store(user_name, store_name, product_name);
        }
        else
            return false;
    }

    @Override
    public boolean edit_store_policy(String user_name, String store_name) {
        return false;
    }


    public boolean add_store_simple_policy(String user_name, String store_name, int type, int policy_id, String product_name, int min, int max, User user, int max_quantity, int day) {
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.create_store_simple_policy(user_name, store_name, type, policy_id, product_name, min, max, user, max_quantity, day);
        }
        else return false;
    }
    public boolean add_store_complex_policy(String user_name, String store_name, int type, int policy_id, String product_name, int min, int max, User user, int max_quantity, int day, int op) {
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.create_store_complex_policy(user_name, store_name, type, policy_id, product_name, min, max, user, max_quantity,day, op);
        }
        else return false;
    }
    public boolean add_policy_to_comlexPolicy(String user_name, String store_name, int type, int complex_policy_id, int policy_id, String product_name, int min, int max, User user, int max_quantity, int day) {
        //if (!SystemManage_Facade.is_initialized()) return false;
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
    public boolean edit_manager_permissions(String user_name, String store_name,String user_assign , ArrayList<String> permissions) {
        my_log.logger.info("edit_manager_permissions");
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.change_permissions_of_manager(user_name, store_name,user_assign , permissions);
        }
        return false;
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
        if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name) && SubscribersManage_Facade.check_if_logged_in(user_name) ) {
            return SubscribersManage_Facade.store_purchase_history(user_name, store_name);
        }
        return null;
    }

}
