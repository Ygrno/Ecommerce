package DomainLayer.InternalService;

import DAL.DBAccess;
import DomainLayer.*;
import DomainLayer.Roles.Permission;
import DomainLayer.Roles.StoreManger;
import DomainLayer.Roles.StoreRole;
import DomainLayer.Roles.SystemManger;
import DomainLayer.Store.DiscountComponent;
import DomainLayer.Store.DiscountPolicy;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import DomainLayer.User.User;

import ExternalService.ExternalFinanceService;
import ExternalService.ExternalSupplyService;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SystemManage_Facade implements InternalService {

    public static System system;
    private static DBAccess dB = DBAccess.getInstance();

    public static void init_system() throws Exception {
        system = System.getSystem();
    }

    public static boolean is_initialized() {
        return System.initialized;
    }

    /////////////// store methods///////////////////////////

    public static boolean find_store(String store_name) {
        Store store = system.get_store(store_name);
        return store != null;
    }

    public static Store get_store(String store_name) {

        return system.get_store(store_name);
    }

    public static List<Store> get_stores() {

        return system.getStore_list();
    }


    public static boolean checkIfCanBuy(String id){
        User u =getUser(id);
        for(PurchaseProcess process: u.getPurchaseProcesslist()){
            if(!process.isFinished()) {
                HashMap<Product, Integer> products = process.getShoppingBag().getProductsAmount();
                for (Product p : products.keySet()) {
                    if (p.getSupplied_amount() < products.get(p)) {
                        java.lang.System.out.println("her1");
                        return false;
                    }
                }
                if (!process.getStore().validatePurchasePolicies(process.getShoppingBag())) {
                    java.lang.System.out.println("her2");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean buy(String[] dd,String[] supplyDetails) throws Exception {
        DealDetails dd1 = new DealDetails(dd[0],Double.parseDouble(dd[1]),dd[2],dd[3],dd[4],Integer.parseInt(dd[5]), dd[6]);
        String tid =system.getProductFinanceService().tryToBuy(dd1);
        if (!tid.equals("-1")) {
            if(!system.getProductSupplyService().order(new OrderDetails(supplyDetails[0],supplyDetails[1],supplyDetails[2],supplyDetails[3],supplyDetails[4])))
            {
                system.getProductFinanceService().cancelPay(tid);
                return false;
            }

            User u = getUser(dd[0]);

            for(PurchaseProcess purchase: u.getPurchaseProcesslist()){
                if(!purchase.isFinished()) {
                    purchase.setDone(true, dd1); //User Bought the products therefore the process is finished.


                    Store store = purchase.getStore();
                    store.getPurchase_process_list().add(purchase); //Store now contains that history purchase.

                    DiscountPolicy discountPolicy = store.getDiscountPolicy(); //discounts can now be re-calculate.
                    for(DiscountComponent discount: discountPolicy.getDiscounts()){
                        discount.setCalculated(false);
                    }

                    //Update the supply amount of products in store
                    ShoppingBag shoppingBag = purchase.getShoppingBag();
                    for(Product prod: shoppingBag.getProductsAmount().keySet()){
                        Product store_product = getProductInStore(prod.getName(),store);
                        store_product.setSupplied_amount(store_product.getSupplied_amount() - shoppingBag.getProductsAmount().get(prod));
                        //dB.updateAndCommit(store_product);
                    }
                    //User bought his saved products so shopping bag no longer exists with store.
                    purchase.getUser().getShoppingCart().getShopping_bag_list().remove(purchase.getShoppingBag());
                    if(u instanceof Subscriber)
                        dB.updateAndCommit(purchase);
                }
            }
            return true;
        }
        return false;
    }


    private static User getUser(String id){
        User u;
        if(id.charAt(0)>='0' && id.charAt(0)<='9') {
            u = getGuest(Integer.parseInt(id));
        }
        else
            u=get_subscriber(id);
        return u;
    }



    /////////////////guest methods/////////////////////

    public static Guest getGuest(int id){

        for(Guest g : system.getGuest_list()){
            if(g.getId()==id)
                return g;
        }
        return null;
    }

    public static Guest addGuest(){
        int id = system.getNextGuestId();
        Guest guest = new Guest(id);
        system.getGuest_list().add(guest);
        system.increaseGuestId();
        return guest;
    }


    public static void setFinanceSystem(ExternalFinanceService financeSystemImp){
        system.getProductFinanceService().setFinanceService(financeSystemImp);
    }

    public static void setSupplySystem(ExternalSupplyService supplySystemImp){
        system.getProductSupplyService().setSupplyService(supplySystemImp);
    }

    public static HashMap<String, Double> searchProductStores(String product_name){
        HashMap<String, Double> stores = new HashMap<>();
        for(Store s : get_stores()){
            Product p=s.getProduct(product_name);
            if(p!=null)
                stores.put(s.getName(),p.getPrice());
        }
        return stores;
    }


    //todo - check updates DB
    public static boolean saveProductForGuest(int id,String product_name, String store_name,int amount){
        Guest g=SystemManage_Facade.getGuest(id);
        if(g==null)
            g=SystemManage_Facade.addGuest();

        boolean processExist=false;
        Product product=null;
        Store s = SystemManage_Facade.get_store(store_name);

        product = getProductInStore(product_name, s);

        if(product == null || amount > product.getSupplied_amount()) return false;

        Product buy_product = new Product(product.getName(),product.getPrice(),product.getSupplied_amount(),product.getStore());
//        dB.updateAndCommit(buy_product); //todo - the product already exists in DB, but not connected to the buyer.
//        buy_product.setBuy_amount(amount);

        for(PurchaseProcess p:g.getPurchaseProcesslist()){
            if(!p.isFinished() && p.getStore().getName().equals(store_name)){
                p.getShoppingBag().getProducts_names().add(product_name);
                p.getShoppingBag().getProducts().add(buy_product);
                processExist=true;
                product.setShoppingBag(p.getShoppingBag());


            }
        }

        if(!processExist){
            ShoppingBag sb= new ShoppingBag(new ArrayList<>());
            PurchaseProcess p = new PurchaseProcess(g,s,sb);
            g.getShoppingCart().getShopping_bag_list().add(p.getShoppingBag());
            p.getShoppingBag().getProducts_names().add(product_name);
            p.getShoppingBag().getProducts().add(buy_product);
            g.getPurchaseProcesslist().add(p);
            sb.setShoppingCart(g.getShoppingCart());
            product.setShoppingBag(sb);
        }
        return true;
    }

    private static Product getProductInStore(String product_name, Store s) {
        Product product = null;
        for (Product prod : s.getProduct_list()) {
            if (prod.getName().equals(product_name)) {
                product = prod;
            }
        }
        return product;
    }


    public static List<JSONObject> getProductsInCartForGuest(int id) throws JSONException {
        Guest g=getGuest(id);
        assert g != null;
        return g.getShoppingCart().getProducts();
    }


    public static double getPriceOfCart(String id){
        User u = getUser(id);
        assert u != null;
        return u.getShoppingCart().getTotalPrice();
    }

    /////////////// subscriber methods//////////////////////

    public static boolean find_subscriber(String user_name) {
        Subscriber subscriber = system.get_subscriber(user_name);
        return subscriber != null;
    }

    public static Subscriber get_subscriber(String user_name) {

        return system.get_subscriber(user_name);
    }

    public static boolean add_subscriber(String user_name, String password) {
        Subscriber subscriber = new Subscriber(user_name, password);
        system.getUser_list().add(subscriber);
        return dB.updateAndCommit(subscriber);
    }

    public static boolean remove_subscriber(String username) {
        return system.remove_subscriber(username);
    }

        public static  List<JSONObject> View_purchase(String user_name) throws JSONException { //3.7
        Subscriber subscriber = system.get_subscriber(user_name);
        List<JSONObject> l = new ArrayList<>();
        for(PurchaseProcess pp : subscriber.getPurchaseProcesslist()){
            JSONObject o = new JSONObject();
            JSONArray lProducts = new JSONArray();
            o.put("store_name",pp.getStore().getName());
            for(String p : pp.getShoppingBag().getProducts_names()){
                JSONObject o1 =new JSONObject();
                o1.put("product_name",p);
                lProducts.put(o1);
            }
            o.put("products",lProducts);
            l.add(o);
        }
        return l;
    }

    //todo - DB
    public static void Add_Query(String user_name,String query) { //3.5  -- #TODO add test that the query inserted
        Subscriber subscriber = system.get_subscriber(user_name);
        subscriber.getQueries().add(query);
    }
    //todo - check updates DB
    public static boolean saveProductForSubscriber(String userName,String product_name, String store_name,int amount){

        Subscriber s = SystemManage_Facade.get_subscriber(userName);
        boolean processExist = false;
        Product product=null;
        Store store = SystemManage_Facade.get_store(store_name);
        if(store == null) return false;

        product = getProductInStore(product_name, store);

        if(product == null || amount > product.getSupplied_amount()) return false;

          Product buy_product = new Product(product.getName(),product.getPrice(),product.getSupplied_amount(),product.getStore());

          //        dB.updateAndCommit(buy_product); //todo - the product already exists in DB, but not connected to the buyer.
//        buy_product.setBuy_amount(amount);

        for(PurchaseProcess p:s.getPurchaseProcesslist()){
            if(!p.isFinished() && p.getStore().getName().equals(store_name)){
                p.getShoppingBag().getProducts_names().add(product_name);
                p.getShoppingBag().getProducts().add(buy_product);
                processExist=true;
                product.setShoppingBag(p.getShoppingBag());
                dB.updateAndCommit(s.getShoppingCart());
                dB.updateAndCommit(p);
                dB.updateAndCommit(p.getShoppingBag());
                dB.updateAndCommit(product);

            }
        }

        if(!processExist){
            ShoppingBag sb= new ShoppingBag(new ArrayList<>());
            PurchaseProcess p = new PurchaseProcess(s,store,sb);
            s.getShoppingCart().getShopping_bag_list().add(p.getShoppingBag());
            p.getShoppingBag().getProducts_names().add(product_name);
            p.getShoppingBag().getProducts().add(buy_product);
            s.getPurchaseProcesslist().add(p);
            sb.setShoppingCart(s.getShoppingCart());
            product.setShoppingBag(sb);
//            dB.updateAndCommit(s.getShoppingCart());
            dB.updateAndCommit(p);
            //   dB.updateAndCommit(p);
            dB.updateAndCommit(sb);
            dB.updateAndCommit(product);
        }
        return true;
    }

    public static List<JSONObject> getProductsInCartForSubscriber(String id) throws JSONException {
        Subscriber g=get_subscriber(id);
        assert g != null;
        return g.getShoppingCart().getProducts();
    }

    ////////////////////////////////////////////////////////
    public static boolean check_password(String user_name, String password) {
        Subscriber subscriber = system.get_subscriber(user_name);
        String password_dyc = subscriber.getPassword();
        return password_dyc.equals(password);
    }

    public static List<String> get_user_permissions(String username, String store){

        Subscriber s = system.get_subscriber(username);
        StoreRole storeRole = s.get_role_at_store(store);

        if(storeRole instanceof StoreManger){
            StoreManger MstoreRole = (StoreManger)storeRole;
            List<Permission> pms = MstoreRole.getPermissions();
            List<String> res = new ArrayList<>();
            for(Permission p : pms){
                res.add(permissionToString(p));
            }
            return res;
        }else{
            return null;
        }
    }

    public static Permission string_to_permission(String s) {
        Permission permission[] = Permission.values();
        for (Permission p : permission)
            if (s.equalsIgnoreCase(String.valueOf(p)))
                return p;

        return null;
    }

    public static String permissionToString(Permission p){
        return p.toString();
    }

    public static List<Permission> strings_to_permissions(List<String> strings) {
        List<Permission> permissions = new ArrayList<>();

        for (String s : strings) {
            permissions.add(string_to_permission(s));
        }
        return permissions;


    }

    public static String[][] get_products_of_store(String store_name) {
        List<Store> storeList = system.getStore_list();
        List<Product> productList = null;
        for (Store s : storeList) {
            if (s.getName().equals(store_name))
                productList = s.getProduct_list();
        }
        if(productList==null)
            return null;
        int size = productList.size();

        String[][] products_arr = new String[size][4];
        for (int i = 0; i<size; i++){
            products_arr[i][0]=productList.get(i).getName();
            products_arr[i][1]=String.valueOf(productList.get(i).getPrice());
            products_arr[i][2]=String.valueOf(productList.get(i).getSupplied_amount());
            products_arr[i][3]=productList.get(i).getStore().getName();
        }
        return products_arr;
    }

    public static List<JSONObject> get_store_purchase_process(String store_name) throws Exception{
        List<JSONObject> history = new ArrayList<>();
        Store store = system.get_store(store_name);
        if (store != null) {
            for(PurchaseProcess purchase: store.getPurchase_process_list()) {
                if (purchase.isFinished()) {
                    //history.append("\n").append("Store Name: ").append(purchase.getStore().getName()).append("\nList of products: ").append(purchase.getShoppingBag().getProducts_names().toString()).append("\n sum: ").append(purchase.getDetails().getPrice());
                    JSONObject o = new JSONObject();
                    o.put("store_name", purchase.getStore().getName());
                    o.put("price", purchase.getDetails().getPrice());
                    //set username
                    User buyer = purchase.getUser();
                    if(buyer instanceof Guest){
                        o.put("username", "Guest");
                    }else {
                        o.put("username", ((Subscriber)buyer).getName());
                    }

                    //set products
                    JSONArray arr = new JSONArray();
                    for(String productName : purchase.getShoppingBag().getProducts_names()){
                        JSONObject p = new JSONObject();
                        p.put("name", productName);
                        arr.put(p);
                    }
                    o.put("products", arr);
                    history.add(o);
                }
            }
        }

        return history;
    }

    public static List<JSONObject> get_subscriber_purchase_process(String user_name) throws Exception {
        List<JSONObject> history = new ArrayList<>();

        Subscriber sub = system.get_subscriber(user_name);
        if (sub != null) {
            for(PurchaseProcess purchase: sub.getPurchaseProcesslist()) {
                if (purchase.isFinished()) {
                    //history.append("\n").append("Customer Name: ").append(purchase.getDetails().getBuyer_name()).append("Store Name: ").append(purchase.getStore().getName()).append("\nList of products: ").append(purchase.getShoppingBag().getProducts_names().toString()).append("\n sum: ").append(purchase.getDetails().getPrice());
                    JSONObject o = new JSONObject();
                    o.put("store_name", purchase.getStore().getName());
                    o.put("price", purchase.getDetails().getPrice());

                    JSONArray arr = new JSONArray();
                    for(String productName : purchase.getShoppingBag().getProducts_names()){
                        JSONObject p = new JSONObject();
                        p.put("name", productName);
                        arr.put(p);
                    }
                    o.put("products", arr);
                    history.add(o);

                }
            }
        }
        return history;
    }

    public static String get_subscriber_purchase_process_string(String user_name) {
        StringBuilder history = new StringBuilder();
        Subscriber sub = system.get_subscriber(user_name);
        if (sub != null) {
            for(PurchaseProcess purchase: sub.getPurchaseProcesslist()){
                if(purchase.isFinished())
                    history.append("\n").append("Customer Name: ").append(purchase.getDetails().getBuyer_name()).append(" Store Name: ").append(purchase.getStore().getName()).append("\nList of products: ").append(purchase.getShoppingBag().getProducts_names().toString()).append("\n sum: ").append(purchase.getDetails().getPrice()).append("\n");
            }
        }

        return history.toString();
    }

    public static List<JSONObject> getAllStores() throws JSONException {
        List<JSONObject> stores=new ArrayList<>();
        for(Store s: system.getStore_list()){
            JSONObject o=new JSONObject();
            o.put("name",s.getName());
            o.put("is_open", s.isIs_open());
            stores.add(o);
        }
        return stores;
    }

/*    public static String get_subscriber_purchase_process(String user_name) {
        StringBuilder history = new StringBuilder();
        Subscriber sub = system.get_subscriber(user_name);
        if (sub != null) {
            for(PurchaseProcess purchase: sub.getPurchaseProcesslist()){
                if(purchase.isFinished())
                    history.append("\n").append("Customer Name: ").append(purchase.getDetails().getBuyer_name()).append(" Store Name: ").append(purchase.getStore().getName()).append("\nList of products: ").append(purchase.getShoppingBag().getProducts_names().toString()).append("\n sum: ").append(purchase.getDetails().getPrice()).append("\n");
            }
        }

        return history.toString();
    }*/

    public static String today_revenue() {
        double sum = 0;
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String dateString = format.format( new Date()   );
        int today=Integer.parseInt(dateString);
        List<Store> stores =system.getStore_list(); // TODO in to get the information from date base for today
        for(Store store : stores){
            List<PurchaseProcess> purchases = store.getPurchase_process_list();
            for( PurchaseProcess ps : purchases){
                if(ps.isFinished()&& ps.getFinished_date()==today);
                    sum = sum+ps.getShoppingBag().getDiscounted_bag_price();
            }
        }
        return String.valueOf(sum);
    }
    
    public static String date_revenue(String date) {
        double sum=0;
        int today=Integer.parseInt(date);
        List<Store> stores =system.getStore_list();  // TODO in to get the information from date base for specific date
        for(Store store : stores){
            List<PurchaseProcess> purchases = store.getPurchase_process_list();
            for( PurchaseProcess ps : purchases){
                if(ps.isFinished()&& ps.getFinished_date()==today);
                sum = sum+ps.getShoppingBag().getDiscounted_bag_price();
            }
        }
        return String.valueOf(sum);
    }

    public static boolean removeProductFromCart(String id, String product_name, String store_name) {
        User g= getUser(id);
        Product toRemove=null;
        for(PurchaseProcess pp:g.getPurchaseProcesslist()){
            if(pp.getStore().getName().equals(store_name)){
                for (Product p : pp.getShoppingBag().getProducts()){
                    if(p.getName().equals(product_name))
                        toRemove=p;
                }
                if(toRemove!=null) {
                    java.lang.System.out.println("im here");
                    pp.getShoppingBag().getProducts().remove(toRemove);
                    if(!(g instanceof Guest)) {
                        dB.updateAndCommit(pp.getShoppingBag());
                        dB.updateAndCommit(pp);
                    }
                }
            }
        }
        return true;
    }


    public static void promote_to_manager(String user_name, String password) {
        Subscriber admin = get_subscriber(user_name);
        SystemManger systemManger = new SystemManger(admin);
        admin.getRole_list().add(systemManger);
    }
}
