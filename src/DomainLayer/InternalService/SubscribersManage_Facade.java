package DomainLayer.InternalService;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.*;
import DomainLayer.ShoppingBag;
import DomainLayer.Store.*;
import DomainLayer.Store.DiscountPolicy;
import DomainLayer.Store.Store;
import DomainLayer.Store.VisibleDiscount;
import DomainLayer.System;
import DomainLayer.User.ProductReview;
import DomainLayer.User.Subscriber;
import Logs.LogErrors;
import Logs.LogInfo;
import Observer.Observer;
import com.mysql.cj.xdevapi.JsonArray;
import jdk.jshell.tool.JavaShellToolBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import DAL.DBAccess;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubscribersManage_Facade implements InternalService {

    private static DBAccess dB = DBAccess.getInstance();
    private static LogInfo my_logInfo = LogInfo.getLogger();
    private static LogErrors my_logError = LogErrors.getLogger();


    /////////////// login/signup methods///////////////////////////
    public static boolean login(String username, String password) throws Exception {
        if(System.getSystem().get_subscriber(username)== null)
            return false;
        subscriber_login_state(username, true);
        //check password
        return true;
    }

    public static boolean signup(String username, String password) throws Exception {
        if(System.getSystem().get_subscriber(username) != null ){
            return false;
        }
        Subscriber sub = new Subscriber(username,password);
        System.getSystem().getUser_list().add(sub);
        return dB.updateAndCommit(sub);
    }
/*    public static Boolean openStore(String username, String name) {
        return System.getSystem().SubImp().open_store(username,name);
    }*/

    public static List<StoreRole> GetRoles(String store) throws Exception {
        return System.getSystem().get_store(store).getRoles();
    }
    public static StoreOwner StoreOwner(String username,String store) throws Exception {
        return System.getSystem().storeOwner(username,store);
    }

    public static boolean signout(String username) throws Exception {
        if(!check_if_logged_in(username)) {
            return false;
        }
        subscriber_login_state(username,false);
        return true;
    }
    public static void purchaseListAdd(String sub, String store ,ArrayList<String> arr) throws Exception {
        ShoppingBag shoppingBag = new ShoppingBag(arr);
        dB.updateAndCommit(shoppingBag);
        PurchaseProcess pp = new PurchaseProcess( System.getSystem().get_subscriber(sub), System.getSystem().get_store(store),shoppingBag);
        System.getSystem().get_subscriber(sub).getPurchaseProcesslist().add(pp);
        dB.updateAndCommit(pp);
    }


//    public static void view_purchase_history(String sub){
//        SubscriberImp SubImp = new SubscriberImp();
//        return SubImp.view_purchase_history("subscriber");
//    }




    public static void subscriber_login_state(String user_name, boolean state) throws Exception { //notifi updated
        java.lang.System.out.println("we are after subscriber_login_state");
        System.getSystem().get_subscriber(user_name).setLogged_in(state);
        if(state)
           if (Observer.GetObserver().CheckNotification(user_name)) {
               Observer.GetObserver().Notify(user_name);
//               JSONObject o = new JSONObject();
//               o.put("username",user_name);
//               o.put("message","login");
//               Observer.GetObserver().update(o);
               java.lang.System.out.println("we found notification");
           }
    }

    public static boolean check_if_logged_in(String user_name) throws Exception {
        if (System.initialized) {
            return System.getSystem().get_subscriber(user_name).isLogged_in();
        }
        return false;
    }
    public static boolean addProductReview(String user_name, String product_name, String store_name, String review_data, int rank) throws Exception {
        Subscriber subscriber = System.getSystem().get_subscriber(user_name);
        Product reviewedProduct = System.getSystem().get_store(store_name).getProduct(product_name);
        List<PurchaseProcess> purchedlist = new ArrayList<>();
        ProductReview product_review;
        boolean isPurchased = false;
        ShoppingBag currentShoppingBag;
        if (subscriber != null && subscriber.isLogged_in() && reviewedProduct != null) {
            purchedlist = subscriber.getPurchaseProcesslist();
            for (PurchaseProcess pp : purchedlist) {
                currentShoppingBag = pp.getShoppingBag();
                for (String p : currentShoppingBag.getProducts_names())
                    if (p.equals(product_name) && pp.getStore().getName().equals(store_name)&&pp.isFinished()) {
                        isPurchased = true;
                        product_review = new ProductReview(subscriber,rank,review_data);
                        dB.updateAndCommit(product_review);
                        reviewedProduct.addReview(product_review);
                    }
            }
        }
        return isPurchased;
    }
    public static boolean delete_discount(String user_name, String store_name, String discount_name) throws Exception {
        Subscriber requester = System.getSystem().get_subscriber(user_name);

        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null ) return false;
        Store store_to_add = store_role.store;
        if (store_role instanceof StoreOwner || (store_role instanceof  StoreManger && ((StoreManger) store_role).havePermission("ADD_DISCOUNT"))) {
            DiscountPolicy discountPolicy = store_to_add.getDiscountPolicy();
            if(!discountPolicy.check_if_unique(discount_name)) {
                DiscountComponent dc=discountPolicy.get_discount_by_name(discount_name);
                discountPolicy.delete_discount(discount_name);
                dB.deleteAndCommit(dc);
                return true;
            }
        }
        return false;

    }

    public static boolean add_visible_discount_to_product(String user_name, String store_name, String product_name, String discount_name, double discount_percentage, int due_date) throws Exception {
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null ) return false;
        Store store_to_add = store_role.store;
        Product p = store_to_add.getProduct(product_name);
        if(p == null) return false;

        if (store_role instanceof StoreOwner || (store_role instanceof  StoreManger && ((StoreManger) store_role).havePermission("ADD_DISCOUNT"))) {
            DiscountPolicy discountPolicy = store_to_add.getDiscountPolicy();
            if(discountPolicy.check_if_unique(discount_name)) {
                VisibleDiscount visibleDiscount = new VisibleDiscount(discount_name, discount_percentage, due_date, p);
                dB.updateAndCommit(visibleDiscount);
                discountPolicy.add_discount(visibleDiscount);
                return true;
            }
        }
        return false;

    }

    public static boolean add_conditioned_discount_to_product(String user_name, String store_name, String product_name, String discount_name, double discount_percentage, int due_date,int amount,int sum) throws Exception {
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null ) return false;
        Store store_to_add = store_role.store;
        Product p = store_to_add.getProduct(product_name);
        if(p == null) return false;

        if (store_role instanceof StoreOwner || (store_role instanceof  StoreManger && ((StoreManger) store_role).havePermission("ADD_DISCOUNT"))) {
            DiscountPolicy discountPolicy = store_to_add.getDiscountPolicy();
            if(discountPolicy.check_if_unique(discount_name)) {
                if (amount > 0) {
                    ConditionedDiscount conditionedDiscount = new ConditionedDiscount(discount_name, discount_percentage, due_date, Condition.IF_NUMBER_OF_PRODUCTS, store_to_add, p, amount, sum);
                    discountPolicy.add_discount(conditionedDiscount);
                    dB.updateAndCommit(conditionedDiscount);
                    return true;
                }
                if (sum > 0) {
                    ConditionedDiscount conditionedDiscount = new ConditionedDiscount(discount_name, discount_percentage, due_date, Condition.IF_SUM_GREATER_THAN, store_to_add, p, amount, sum);
                    discountPolicy.add_discount(conditionedDiscount);
                    dB.updateAndCommit(conditionedDiscount);
                    return true;
                }
                return false;
            }
        }
        return false;

    }

    public static boolean add_complex_discount(String user_name, String store_name, String discount_name, String[] discounts, String type, int end_of_use_date) throws Exception {
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null ) return false;
        Store store_to_add = store_role.store;
        if (store_role instanceof StoreOwner || (store_role instanceof  StoreManger && ((StoreManger) store_role).havePermission("ADD_DISCOUNT"))) {
            DiscountPolicy discountPolicy = store_to_add.getDiscountPolicy();
            for(String dc :discounts) // see if all the discount are real (in the store)
                if(discountPolicy.check_if_unique(dc)) {
                    return false;
                }
            ArrayList<DiscountComponent> discounts_comp = new ArrayList<>();
            for(String name : discounts){
                DiscountComponent dc=discountPolicy.get_discount_by_name(name);
                if(dc!=null)
                    discounts_comp.add(dc);
                else{
                    return false;
                }
            }
            ComplexDiscount complexDiscount = new ComplexDiscount(discount_name,discounts_comp,type,end_of_use_date);
            discountPolicy.add_discount(complexDiscount);
            dB.updateAndCommit(complexDiscount);
        }
        return false;
    }

    public static boolean create_store(String user_name, String store_name) throws Exception {

        JSONObject O = new JSONObject();
        O.put("username",user_name);
        O.put("message","opened store : " + store_name);
        Observer.GetObserver().update(O);
        java.lang.System.out.println(user_name + " " + store_name);

        Subscriber subscriber = System.getSystem().get_subscriber(user_name);

        Store store = new Store(store_name);


        StoreOwner storeOwner = new StoreOwner(subscriber, store);


        subscriber.getRole_list().add(storeOwner);

        store.getRoles().add(storeOwner);

        System.getSystem().getStore_list().add(store);
        if(!dB.updateAndCommit(store)) return false;
        if(!dB.updateAndCommit(storeOwner)) return false;
        return true;
    }

    public static boolean add_product_to_store(String user_name, String store_name, String product_name, double product_price, int product_amount) throws Exception {
        Subscriber subscriber = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = subscriber.get_role_at_store(store_name);
        if (store_role instanceof StoreOwner) {
            Product product = new Product(product_name, product_price, product_amount, store_role.store);

            if(!dB.updateAndCommit(product)) return false;
            store_role.store.getProduct_list().add(product);
            return true;
        }
        else if (store_role instanceof StoreManger) {
            if (((StoreManger) store_role).havePermission("ADD_PRODUCT")) {
                Product product = new Product(product_name, product_price, product_amount, store_role.store);
                if(!dB.updateAndCommit(product)) return false;
                store_role.store.getProduct_list().add(product);
            }
            return true;
        }
        java.lang.System.out.println("her2");

        return false;
    }


    public static boolean change_product_in_store(String user_name, String store_name, String product_name, String new_product_name, int product_price, int product_amount) throws Exception {
        Subscriber subscriber = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = subscriber.get_role_at_store(store_name);
        if (store_role instanceof StoreOwner) {
            Product product = store_role.store.getProduct(product_name);
            if(product == null) return false;
            product.setName(new_product_name);
            product.setPrice(product_price);
            product.setSupplied_amount(product_amount);
            dB.updateAndCommit(product);
            return true;
        }
        else if (store_role instanceof StoreManger) {
            if (((StoreManger) store_role).havePermission("EDIT_PRODUCT")) {
                Product product = store_role.store.getProduct(product_name);
                product.setName(new_product_name);
                product.setPrice(product_price);
                product.setSupplied_amount(product_amount);
                dB.updateAndCommit(product);
                return true;
            }
        }
        return false;
    }

    public static boolean remove_product_in_store(String user_name, String store_name, String product_name) throws Exception {
        Subscriber subscriber = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = subscriber.get_role_at_store(store_name);
        if (store_role instanceof StoreOwner) {
            Product product = store_role.store.getProduct(product_name);
            if(product == null) return false;
            store_role.store.getProduct_list().remove(product);
            dB.deleteAndCommit(product);
            return true;
        } else if (store_role instanceof StoreManger) {
            if (((StoreManger) store_role).havePermission("REMOVE_PRODUCT")) {
                Product product = store_role.store.getProduct(product_name);
                store_role.store.getProduct_list().remove(product);
                dB.deleteAndCommit(product);
                return true;
            }
        }
        return false;
    }

    public static boolean add_owner_to_store(String user_name, String store_name, String user_assign) throws Exception {
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
            dB.updateAndCommit(store_role);
            dB.updateAndCommit(storeOwner);

            return true;
        }
        return false;
    }

    public static boolean remove_owner_from_store(String user_name, String store_name, String user_assign) throws Exception {
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);

        if (store_role instanceof StoreOwner) {
            Subscriber to_remove = System.getSystem().get_subscriber(user_assign);
            if(to_remove == null) return false; //User wasn't found in the system.
            StoreOwner storeOwner2 = ((StoreOwner)store_role).store.find_store_owner_by_name(to_remove.getName());
            if(storeOwner2 == null) return false; //User wasn't found in the store as a Store Owner.
            if(!store_role.getAssigned_users().contains(storeOwner2)) return false; //Requester can't remove this store Owner cause he wasn't assigned by him.

            List<Role> assigned_users = storeOwner2.getAssigned_users();
            boolean end = assigned_users.size() == 0;
            while(!end){

                Role role = assigned_users.get(0);
                if(role instanceof StoreRole){
                    Store store = ((StoreRole) role).store;
                    if(store.getName().equals(store_name)){
                        if(role instanceof StoreOwner)
                        {
                            remove_owner_from_store(storeOwner2.user.getName(),store_name,role.user.getName());
                        }

                        else if(role instanceof  StoreManger)
                        {
                            remove_manager_from_store(storeOwner2.user.getName(),store_name,role.user.getName());
                        }
                    }
                }
                if(assigned_users.size() == 0) end = true;

            }
            store_role.store.getRoles().remove(storeOwner2);
            store_role.getAssigned_users().remove(storeOwner2);
            storeOwner2.user.getRole_list().remove(storeOwner2);
            storeOwner2.user=null;
            storeOwner2.setAssigned_by(null);

            dB.deleteAndCommit(storeOwner2);

            return true;
        }
        return false;
    }

    public static boolean add_manager_to_store(String user_name, String store_name, String user_assign) throws Exception {
        Subscriber requester = System.getSystem().get_subscriber(user_name);

        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null) return false;

        if (store_role instanceof StoreOwner || (store_role instanceof  StoreManger && ((StoreManger) store_role).havePermission("ASSIGN_MANAGER"))) {
            Subscriber manager_to_add = System.getSystem().get_subscriber(user_assign);
            if(manager_to_add == null || requester.equals(manager_to_add)) return false;
            if (have_role_in_store(store_role, manager_to_add)) return false;
            StoreManger storeManger = new StoreManger(manager_to_add,store_role.store);
            manager_to_add.getRole_list().add(storeManger);
            storeManger.store.getRoles().add(storeManger);
            store_role.getAssigned_users().add(storeManger);
            storeManger.setAssigned_by(store_role);
            if(!dB.updateAndCommit(storeManger)) return false;
            if(!dB.updateAndCommit(store_role)) return false;
            return true;
        }
        return false;
    }

    private static boolean have_role_in_store(StoreRole store_role, Subscriber manager_to_add) {
        Store store = store_role.store;
        for(StoreRole storeRole: store.getRoles()){
            if(storeRole.user.getName().equals(manager_to_add.getName())) return true;
        }
        return false;
    }

    public static boolean remove_manager_from_store(String user_name, String store_name, String user_assign) throws Exception {
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
            //store_role.user.getRole_list().remove(storeManger);
            storeManger.user.getRole_list().remove(storeManger);
            storeManger.setAssigned_by(null);
            storeManger.setUser(null);
            //dB.updateAndCommit(store_role);
            dB.deleteAndCommit(storeManger);
            return true;
        }
        return false;
    }

    public static boolean change_permissions_of_manager(String user_name, String store_name,String user_assign , ArrayList<String> permissions) throws Exception {
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null) return false; //bug


        if (store_role instanceof StoreOwner) {
            Subscriber to_edit_permissions = System.getSystem().get_subscriber(user_assign);
            if(to_edit_permissions == null) return false;
            StoreManger storeManger = store_role.store.find_store_manager_by_name(to_edit_permissions.getName());
            if(storeManger == null || !store_role.getAssigned_users().contains(storeManger)) return false;

            List<Permission> fixPermissions = SystemManage_Facade.strings_to_permissions(permissions);
            storeManger.setPermissions(fixPermissions);
            dB.updateAndCommit(storeManger);
            return true;
        }
        return false;
    }

    public static String store_purchase_history(String user_name, String store_name) throws Exception {
        StringBuilder history = new StringBuilder();
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);
        if(store_role == null) return null;
        if (store_role instanceof StoreOwner || (store_role instanceof StoreManger && ((StoreManger) store_role).havePermission("VIEW_STORE_HISTORY"))) {
            Store store = store_role.store;
            for(PurchaseProcess purchase: store.getPurchase_process_list()){
                if(purchase.isFinished())
                    history.append("\n").append(" Customer Name: ").append(purchase.getDetails().getBuyer_name()).append("\n List of products: ").append(purchase.getShoppingBag().getProducts_names().toString()).append("\n sum: ").append(purchase.getDetails().getPrice());
            }
        }
        return history.toString();
    }

    public static boolean create_store_simple_buyPolicy(String user_name, String store_name, int type, String description, int policy_id, String product_name, int minProducts, int maxProducts, int minCost, int maxCost, int min_quantity, int max_quantity, int day) throws Exception {
        if (checkPermission(user_name, store_name)) return false;
        Store store = find_store(store_name);
        if (store==null)return false;
        BuyPolicy policy = find_buy_policy(policy_id, store);
        if (policy!= null) return false;//policy_id already exists;

        return build_and_add_policy_to_store(type, description, policy_id, product_name, minProducts, maxProducts, minCost, maxCost, min_quantity, max_quantity, day, store);
    }

    public static boolean create_store_complex_buyPolicy(String user_name, String store_name,String description, int policy_id, int[] policy_ids, int op) throws Exception {
        if (checkPermission(user_name, store_name)) return false;
        Store store = find_store(store_name);
        if (store==null)return false;
        BuyPolicy comp_policy = find_buy_policy(policy_id, store);
        if (comp_policy!= null) return false;//policy_id already exists;
        ComplexBuyPolicy complex_policy = new ComplexBuyPolicy(policy_id,description, int_to_logic(op));
        complex_policy.setStore(store);
        dB.updateAndCommit(complex_policy);
        int counter=0;
        List<BuyPolicy> policies_toAdd=new ArrayList<>();
        for (int id: policy_ids){
            for (BuyPolicy p: store.getBuyPolicyList()) {
                if (p.getPolicy_id() == id){
                    counter++;
                    policies_toAdd.add(p);
                }
            }
        }
        if (policies_toAdd.size()==counter){
            for(BuyPolicy policy: policies_toAdd) {
                complex_policy.getPolicies_list().add(policy);
            }
            store.getBuyPolicyList().add(complex_policy);
        }
        else return false;
    return true;
    }

    public static boolean edit_store_simple_buyPolicy(String user_name, String store_name, int type,String description, int policy_id, String product_name, int minProducts, int maxProducts, int minCost, int maxCost, int min_quantity, int max_quantity, int day) throws Exception {

        if (checkPermission(user_name, store_name)) return false;
        Store store = find_store(store_name);
        if (store==null)
            return false;
        BuyPolicy policy = find_buy_policy(policy_id, store);
        if (policy == null)
            return false; //policy_id not exists;
        store.getBuyPolicyList().remove(policy); //remove the old policy
        dB.updateAndCommit(policy);
        return build_and_add_policy_to_store(type,description, policy_id, product_name, minProducts, maxProducts, minCost, maxCost, min_quantity, max_quantity, day, store);
    }

    public static boolean edit_store_complex_buyPolicy(String user_name, String store_name, int policy_id, int new_policy_id, String act) throws Exception {
        if (checkPermission(user_name, store_name)) return false;
        Store store = find_store(store_name);
        if (store==null) return false;
        BuyPolicy policy = find_buy_policy(policy_id, store);
        if (policy == null) return false; //policy_id not exists;
        ComplexBuyPolicy complex_policy=(ComplexBuyPolicy) policy;
        complex_policy.setStore(store);
        BuyPolicy new_policy = find_buy_policy(new_policy_id, store);
        if (new_policy == null) return false; //policy_id not exists;

        if (act.equals("add")) {
            complex_policy.getPolicies_list().add(new_policy);
            dB.updateAndCommit(complex_policy);
            return true;
        }
        else if (act.equals("remove")) {
            for (BuyPolicy p : complex_policy.getPolicies_list()) {
                if (p.getPolicy_id() == new_policy_id) {
                    complex_policy.getPolicies_list().remove(p);
                    dB.updateAndCommit(complex_policy);
                    if (complex_policy.getPolicies_list().isEmpty())
                        store.getBuyPolicyList().remove(complex_policy);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean remove_store_buyPolicy(String user_name, String store_name, int policy_id) throws Exception {
        if (checkPermission(user_name, store_name)) return false;
        Store store = find_store(store_name);
        if (store==null) return false;
        BuyPolicy policy = find_buy_policy(policy_id, store);
        if (policy == null) return false; //policy_id not exists;
        store.getBuyPolicyList().remove(policy);
        dB.deleteAndCommit(policy);
        return true;
    }

    //private functions for buy policy
    private static BuyPolicy find_buy_policy(int policy_id, Store store) {
        my_logInfo.logger.info("find_buy_policy in store by policy_id");
        for (BuyPolicy p : store.getBuyPolicyList())
            if ((p.getPolicy_id())==policy_id)
                return p;
        return null;
    }
    private static Store find_store(String store_name) throws Exception {
        List<Store> store_list= System.getSystem().getStore_list();
        for (Store s: store_list) {
            if(s.getName().equals(store_name))
                return s;
        }
        return null;
    }
    private static boolean checkPermission(String user_name, String store_name) throws Exception {
        Subscriber requester = System.getSystem().get_subscriber(user_name);
        StoreRole store_role = requester.get_role_at_store(store_name);
        if (store_role == null) return true;
        // Store store_to_add = store_role.store;
        if (!(store_role instanceof StoreOwner || (store_role instanceof StoreManger && ((StoreManger) store_role).havePermission("ADD_BUY_POLICY"))))
            return true;
        return false;
    }
    private static boolean build_and_add_policy_to_store(int type, String description, int policy_id, String product_name, int minProducts, int maxProducts, int minCost, int maxCost, int min_quantity, int max_quantity, int day, Store store) {
        BuyPolicy policy;
        switch (type) {   //1=bag; 2=product; 3=system;
            case 1:
                policy = new BagBuyPolicy(policy_id,description, minCost, maxCost,minProducts ,maxProducts );
                store.getBuyPolicyList().add(policy);
                policy.setStore(store);
                dB.updateAndCommit(policy);
                return true;
            case 2:
                policy = new ProductBuyPolicy(policy_id,description, product_name,min_quantity ,max_quantity );
                store.getBuyPolicyList().add(policy);
                policy.setStore(store);
                dB.updateAndCommit(policy);
                return true;
            case 3:
                policy = new SystemBuyPolicy(policy_id,description, day);
                store.getBuyPolicyList().add(policy);
                policy.setStore(store);
                dB.updateAndCommit(policy);
                return true;
            default:
                my_logError.logger.severe("failed to build_and_add_policy_to_store");
                return false;
        }
    }
    private static Logicaloperation int_to_logic(int op) {
        Logicaloperation logic_op = Logicaloperation.and;
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
        return logic_op;
    }



    public static List<JSONObject> getNotifications(String userName) throws Exception {
//        Subscriber requester = System.getSystem().get_subscriber(userName);
        ArrayList<JSONObject> Json = new ArrayList<JSONObject>();
        for (JSONObject O : Observer.GetObserver().getObservers()){
            if(O.get("username") == userName){
                Json.add(O);
            }
        }
        for (JSONObject O : Observer.GetObserver().getObserversSeen()){
            if(O.get("username") == userName){
                Json.add(O);
            }
        }
        return Json;
    }

    public static void addNotification(JSONObject o) throws Exception {
        Observer.GetObserver().getObservers().add(o);
    }

    public static HashMap<Integer,String> getBuyPolicyIdsList(String store_name) throws Exception {
        HashMap<Integer,String> policies = new HashMap<>();
        Store store = find_store(store_name);
        if (store==null) return null;
        int i = 0;
        for (BuyPolicy p : store.getBuyPolicyList()) {
            policies.put(p.getPolicy_id(),p.getDescription());
        }
        return policies;
    }

    public static int[] getPoliciesInComplex(String store_name, int policy_id) throws Exception {
        int[] exists_policies = new int[100];
        Store store = find_store(store_name);
        if (store==null) return null;
        int i = 0;
        for (BuyPolicy c : store.getBuyPolicyList()) {
            if (c.getPolicy_id()==policy_id && c instanceof ComplexBuyPolicy ){
                for (BuyPolicy p : ((ComplexBuyPolicy) c).getPolicies_list()) {
                    exists_policies[i] = p.getPolicy_id();
                    i++;
                }
            }
        }
        return exists_policies;
    }
}
