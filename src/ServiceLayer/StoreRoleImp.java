package ServiceLayer;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.Permission;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreManger;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Store.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreRoleImp implements IStoreRole {


    @Override
    public boolean add_store_product(String user_name, String store_name, String product_name, int product_price, int product_amount) {
        if (!SystemManage_Facade.is_initialized()) return false;
        if (SystemManage_Facade.find_subscriber(user_name) && SubscribersManage_Facade.check_if_logged_in(user_name)) {

        }


        return false;
    }

    @Override
    public boolean edit_store_product(String user_name, String store_name, String product_name, int product_price, int product_amount) {
        if (!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean remove_store_product(String user_name, String store_name) {
        if (!SystemManage_Facade.is_initialized())
            return false;
        else

            return false;
    }

    @Override
    public boolean edit_store_policy(String user_name, String store_name) {
        if (!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean assign_store_owner(String user_name, String store_name, String user_assign) {
        if (!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean remove_store_owner(String user_name, String store_name, String user_assign) {
        if (!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean assign_store_manager(String user_name, String store_name, String user_assign) {
        if (!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean edit_manager_permissions(String user_name, String store_name, ArrayList<String> permissions) {
        return false;
    }

    @Override
    public boolean edit_manager_permissions(String user_name, String store_name, List<String> permissions) {
        if (!SystemManage_Facade.is_initialized()) return false;
        else if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name)) {
            List<Permission> fixPermissions = SystemManage_Facade.strings_to_permissions(permissions);
            Store store = SystemManage_Facade.get_store(store_name);
            List<Role> roles = store.getRoles();
            for (Role r : roles) {
                if (r instanceof StoreManger)
                    if (((StoreManger) r).user.equals(user_name) && ((StoreManger) r).store.equals(store_name))
                        ((StoreManger) r).setPermissions(fixPermissions);

            }


        }
        return false;
    }

    @Override
    public boolean remove_store_manager(String user_name, String store_name, String user_assign) {
        if (!SystemManage_Facade.is_initialized()) return false;
        else if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name)) {
            Store store = SystemManage_Facade.get_store(store_name);
            List<Role> roles = store.getRoles();
            for (Role r : roles) {
                if (r instanceof StoreManger)
                    if (((StoreManger) r).user.equals(user_name) && ((StoreManger) r).store.equals(store_name))
                        store.getRoles().remove(r);

            }


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
    public List<PurchaseProcess> watch_store_history(String user_name, String store_name) {
        if (!SystemManage_Facade.is_initialized()) return null;
        if (SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.find_store(store_name)) {
            Store store = SystemManage_Facade.get_store(store_name);
            List<Role> roles = store.getRoles();
            for (Role r : roles) {
                if (r instanceof StoreManger)
                    if (((StoreManger) r).user.equals(user_name) && ((StoreManger) r).store.equals(store_name))
                        return store.getPurchase_process_list();
                if (r instanceof StoreOwner)
                    if (((StoreOwner) r).user.equals(user_name) && ((StoreOwner) r).store.equals(store_name))
                        return store.getPurchase_process_list();

            }
        }
        return null;

    }
}
