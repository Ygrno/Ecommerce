package DomainLayer.InternalService;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.*;
import DomainLayer.Store.*;
import DomainLayer.Store.DiscountPolicy;
import DomainLayer.Store.Store;
import DomainLayer.Store.VisibleDiscount;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import DomainLayer.User.User;


import java.util.ArrayList;
import java.util.List;

public class SubscribersManage_Facade implements InternalService {

    /////////////// login/signup methods///////////////////////////
    public static boolean login(String username, String password){
        if(System.getSystem().get_subscriber(username)== null)
            return false;
        subscriber_login_state(username, true);
        return true;
    }

    public static boolean signup(String username, String password){
        return true;
    }

    public static void subscriber_login_state(String user_name, boolean state) {
        System.getSystem().get_subscriber(user_name).setLogged_in(state);
    }

    public static boolean check_if_logged_in(String user_name) {
        if (System.initialized) {
            return System.getSystem().get_subscriber(user_name).isLogged_in();
        }
        return false;
    }

    public static void create_store(String user_name, String store_name) {

        Subscriber subscriber = System.getSystem().get_subscriber(user_name);

        Store store = new Store(store_name);

        StoreOwner storeOwner = new StoreOwner(subscriber, store);

        subscriber.getRole_list().add(storeOwner);

        store.getRoles().add(storeOwner);

        System.getSystem().getStore_list().add(store);
    }

    public static boolean add_product_to_store(String user_name, String store_name, String product_name, int product_price, int product_amount) {
        Subscriber subscriber = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = subscriber.get_role_at_store(store_name);
        if (store_role instanceof StoreOwner) {
            store_role.store.getProduct_list().add(new Product(product_name, product_price, product_amount, store_role.store));
            return true;
        }
        else if (store_role instanceof StoreManger) {
            if (((StoreManger) store_role).havePermission("ADD_PRODUCT"))
                store_role.store.getProduct_list().add(new Product(product_name, product_price, product_amount, store_role.store));
            return true;
        }
        return false;
    }


    public static boolean change_product_in_store(String user_name, String store_name, String product_name, String new_product_name, int product_price, int product_amount) {
        Subscriber subscriber = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = subscriber.get_role_at_store(store_name);
        if (store_role instanceof StoreOwner) {
            Product product = store_role.store.getProduct(product_name);
            if(product == null) return false;
            product.setName(new_product_name);
            product.setPrice(product_price);
            product.setAmount(product_amount);
            return true;
        }
        else if (store_role instanceof StoreManger) {
            if (((StoreManger) store_role).havePermission("EDIT_PRODUCT")) {
                Product product = store_role.store.getProduct(product_name);
                product.setName(new_product_name);
                product.setPrice(product_price);
                product.setAmount(product_amount);
                return true;
            }
        }
        return false;
    }

    public static boolean remove_product_in_store(String user_name, String store_name, String product_name) {
        Subscriber subscriber = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = subscriber.get_role_at_store(store_name);
        if (store_role instanceof StoreOwner) {
            Product product = store_role.store.getProduct(product_name);
            if(product == null) return false;
            store_role.store.getProduct_list().remove(product);
            return true;
        } else if (store_role instanceof StoreManger) {
            if (((StoreManger) store_role).havePermission("REMOVE_PRODUCT")) {
                Product product = store_role.store.getProduct(product_name);
                store_role.store.getProduct_list().remove(product);
                return true;
            }

        }
        return false;
    }

    public static boolean add_owner_to_store(String user_name, String store_name, String user_assign) {
        Subscriber subscriber1 = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = subscriber1.get_role_at_store(store_name);
        if (store_role instanceof StoreOwner) {
            Subscriber subscriber2 = System.getSystem().get_subscriber(user_assign);
            if(subscriber2 == null || store_role.store.find_store_owner_by_name(user_assign) != null) return false;
            StoreOwner storeOwner = new StoreOwner(subscriber2,store_role.store);
            subscriber2.getRole_list().add(storeOwner);
            storeOwner.store.getRoles().add(storeOwner);
            store_role.getAssigned_users().add(storeOwner);
            storeOwner.setAssigned_by(store_role);
            return true;
        }
        return false;
    }

    public static boolean remove_owner_from_store(String user_name, String store_name, String user_assign) {
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);

        if (store_role instanceof StoreOwner) {
            Subscriber to_remove = System.getSystem().get_subscriber(user_assign);
            if(to_remove == null) return false;
            StoreOwner storeOwner2 = ((StoreOwner)store_role).store.find_store_owner_by_name(to_remove.getName());
            if(storeOwner2 == null) return false;
            if(!store_role.getAssigned_users().contains(storeOwner2)) return false;
            store_role.store.getRoles().remove(storeOwner2);
            store_role.getAssigned_users().remove(storeOwner2);
            storeOwner2.setAssigned_by(null);
            return true;
        }
        return false;
    }


    public static boolean add_manager_to_store(String user_name, String store_name, String user_assign) {
        Subscriber requester = System.getSystem().get_subscriber(user_name);

        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null) return false;

        if (store_role instanceof StoreOwner || (store_role instanceof  StoreManger && ((StoreManger) store_role).havePermission("ASSIGN_MANAGER"))) {
            Subscriber manager_to_add = System.getSystem().get_subscriber(user_assign);
            if(manager_to_add == null) return false;
            StoreManger storeManger = new StoreManger(manager_to_add,store_role.store);
            manager_to_add.getRole_list().add(storeManger);
            storeManger.store.getRoles().add(storeManger);
            store_role.getAssigned_users().add(storeManger);
            storeManger.setAssigned_by(store_role);
            return true;
        }
        return false;
    }

    public static boolean add_visible_discount_to_product(String user_name, String store_name, String product_name, String discount_name, double discount_percentage, int due_date) {
        Subscriber requester = System.getSystem().get_subscriber(user_name);

        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null ) return false;
        Store store_to_add = store_role.store;
        Product p = store_to_add.getProduct(product_name);
        if(p == null) return false;

        if (store_role instanceof StoreOwner || (store_role instanceof  StoreManger && ((StoreManger) store_role).havePermission("ADD_DISCOUNT"))) {
            DiscountPolicy discountPolicy = store_to_add.getDiscountPolicy();
            discountPolicy.add_discount(new VisibleDiscount(discount_name,discount_percentage,due_date,p));
            return true;
        }
        return false;

    }

    public static boolean remove_manager_from_store(String user_name, String store_name, String user_assign) {
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null) return false;
        if (store_role instanceof StoreOwner || (store_role instanceof  StoreManger && ((StoreManger) store_role).havePermission("REMOVE_MANAGER"))) {
            Subscriber to_remove = System.getSystem().get_subscriber(user_assign);
            if(to_remove == null) return false;

            StoreManger storeManger = store_role.store.find_store_manager_by_name(to_remove.getName());
            if(storeManger == null) return false;
            if(!store_role.getAssigned_users().contains(storeManger)) return false;
            store_role.store.getRoles().remove(storeManger);
            store_role.getAssigned_users().remove(storeManger);
            storeManger.setAssigned_by(null);
            return true;
        }
        return false;
    }

    public static boolean change_permissions_of_manager(String user_name, String store_name,String user_assign , ArrayList<String> permissions) {
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null) return false;
        if (store_role instanceof StoreOwner) {
            Subscriber to_edit_permissions = System.getSystem().get_subscriber(user_assign);
            if(to_edit_permissions == null) return false;
            StoreManger storeManger = store_role.store.find_store_manager_by_name(to_edit_permissions.getName());
            if(storeManger == null || !store_role.getAssigned_users().contains(storeManger)) return false;

            List<Permission> fixPermissions = SystemManage_Facade.strings_to_permissions(permissions);
            storeManger.setPermissions(fixPermissions);
            return true;

        }
        return false;
    }

    public static String store_purchase_history(String user_name, String store_name){
        StringBuilder history = new StringBuilder();
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null) return null;
        if (store_role instanceof StoreOwner || (store_role instanceof StoreManger && ((StoreManger) store_role).havePermission("VIEW_STORE_HISTORY"))) {
            Store store = store_role.store;
            for(PurchaseProcess purchase: store.getPurchase_process_list()){
                if(purchase.isFinished())
                    history.append("\n").append("Customer Name: ").append(purchase.getDetails().getBuyer_name()).append("\nList of products: ").append(purchase.getShoppingBag().getProducts_names().toString()).append("\n sum: ").append(purchase.getDetails().getPrice());
            }
        }
        return history.toString();
    }

    public static boolean create_store_simple_policy(String user_name, String store_name, int type, int policy_id, String product_name, int min, int max, User user, int max_quantity, int day) {
        BuyPolicy policy;
        List<Store> store_list= System.getSystem().getStore_list();
        Store store;
        switch(type){   //1=bag; 2=product; 3=system; 4=user
            case 1:
                policy = new BagBuyPolicy(policy_id, product_name, max_quantity);
                for (Store s: store_list) {
                    if(s.getName().equals(store_name))
                        s.getPurchasePolicies().add(policy);
                }
                break;
            case 2:
                policy = new ProductBuyPolicy(policy_id,product_name, min, max);
                for (Store s: store_list) {
                    if(s.getName().equals(store_name))
                        s.getPurchasePolicies().add(policy);
                }
                break;
            case 3:
                policy = new SystemBuyPolicy(policy_id, day);
                for (Store s: store_list) {
                    if(s.getName().equals(store_name))
                        s.getPurchasePolicies().add(policy);
                }
                break;
            case 4:
                policy = new UserBuyPolicy(policy_id, user);
                for (Store s: store_list) {
                    if(s.getName().equals(store_name))
                        s.getPurchasePolicies().add(policy);
                }
                break;
            default: return false;
        }
        return true;
    }

    public static boolean create_store_complex_policy(String user_name, String store_name, int type, int policy_id, String product_name, int min, int max, User user, int max_quantity,int day, int op) {
        int_to_logic(op);
        ComplexBuyPolicy complex_policy = new ComplexBuyPolicy(policy_id, int_to_logic(op));
        List<Store> store_list= System.getSystem().getStore_list();
        Store store;
        SimpleBuyPolicy policy;
        switch(type){   //1=bag; 2=product; 3=system; 4=user
            case 1:
                policy = new BagBuyPolicy(policy_id, product_name, max_quantity);

                break;
            case 2:
                policy = new ProductBuyPolicy(policy_id,product_name, min, max);

                break;
            case 3:
                policy = new SystemBuyPolicy(policy_id, day);

                break;
            case 4:
                policy = new UserBuyPolicy(policy_id, user);

                break;
            default: policy=null;
        }
        complex_policy.getPolicies_list().add(policy);
        for (Store s: store_list) {
            if(s.getName().equals(store_name))
                s.getPurchasePolicies().add(complex_policy);
    }
        return true;


    }

    private static Logicaloperation int_to_logic(int op) {
        Logicaloperation logic_op;
        switch(op){
            case 1:
                logic_op= Logicaloperation.and;
                break;
            case 2:
                logic_op= Logicaloperation.or;
                break;
            case 3:
                logic_op= Logicaloperation.xor;
                break;
        }
        return Logicaloperation.and;

    }
}
