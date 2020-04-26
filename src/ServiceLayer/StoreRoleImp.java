package ServiceLayer;
import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import java.util.ArrayList;


public class StoreRoleImp implements IStoreRole {

//    private String user_name, store_name;
//    private String product_name;
//    private String product_price;
//    private String product_amount;


    @Override
    public boolean add_store_product(String user_name, String store_name, String product_name, int product_price, int product_amount) {
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_product_to_store(user_name, store_name, product_name, product_price, product_amount);
        }
        return false;
    }

    @Override
    public boolean edit_store_product(String user_name, String store_name, String product_name,String new_product_name, int product_price, int product_amount) {
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.change_product_in_store(user_name, store_name, product_name,new_product_name, product_price, product_amount);
        }
        return false;
    }

    @Override
    public boolean remove_store_product(String user_name, String store_name, String product_name) {
        if (!SystemManage_Facade.is_initialized())  return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_product_in_store(user_name, store_name, product_name);
        }
        else
            return false;
    }

    @Override
    public boolean edit_store_policy(String user_name, String store_name) {
        //if (!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean assign_store_owner(String user_name, String store_name, String user_assign) {
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_owner_to_store(user_name, store_name, user_assign);
        }
        return false;
    }

    @Override
    public boolean remove_store_owner(String user_name, String store_name, String user_assign) {
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_owner_from_store(user_name, store_name, user_assign);
        }
        return false;
    }

    @Override
    public boolean assign_store_manager(String user_name, String store_name, String user_assign) {
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.add_manager_to_store(user_name, store_name, user_assign);
        }
        return false;
    }



    @Override
    public boolean edit_manager_permissions(String user_name, String store_name,String user_assign , ArrayList<String> permissions) {
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.change_permissions_of_manager(user_name, store_name,user_assign , permissions);
        }
        return false;
    }


    @Override
    public boolean remove_store_manager(String user_name, String store_name, String user_assign) {
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {
            return SubscribersManage_Facade.remove_manager_from_store(user_name, store_name, user_assign);
        }
        return false;
    }

    @Override
    public boolean close_store(String user_name, String store_name) {
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
       if (!SystemManage_Facade.is_initialized()) return null;
        if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name) && SubscribersManage_Facade.check_if_logged_in(user_name) ) {
            return SubscribersManage_Facade.store_purchase_history(user_name, store_name);
        }
        return null;
    }

}
