package DomainLayer.InternalService;

import DomainLayer.*;
import DomainLayer.Roles.Permission;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import DomainLayer.User.ProductReview;
import DomainLayer.User.Subscriber;
import DomainLayer.User.User;
import Encryption.EncryptImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemManage_Facade implements InternalService {

    public static System system;



    public static void init_system() {
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



    public static boolean buy(String[] dd){
        DealDetails dd1 = new DealDetails(dd[0],Double.parseDouble(dd[1]),dd[2],dd[3],dd[4],Integer.parseInt(dd[5]));
        if (system.getProductFinanceService().tryToBuy(dd1)) {
            User u = getUser(dd[0]);

            for(PurchaseProcess purchase: u.getPurchaseProcesslist()){
                if(!purchase.isFinished()) {
                    purchase.setDone(true, dd1); //User Bought the products therefore the process is finished.

                    Store store = purchase.getStore();
                    store.getPurchase_process_list().add(purchase); //Store now contains that history purchase.

                    //Update the supply amount of products in store
                    ShoppingBag shoppingBag = purchase.getShoppingBag();
                    for(Product prod: shoppingBag.getProducts()){
                        Product store_product = getProductInStore(prod.getName(),store);
                        store_product.setSupplied_amount(store_product.getSupplied_amount() - prod.getBuy_amount());
                    }

                    //User bought his saved products so shopping bag no longer exists with store.
                    purchase.getUser().getShoppingCart().getShopping_bag_list().remove(purchase.getShoppingBag());
                }
            }

            return true;
        }
        return false;
    }


    private static User getUser(String id){
        User u;
        if(id.charAt(0)>='0' && id.charAt(0)<='9')
            u=getGuest(Integer.parseInt(id));
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
        Guest guest=new Guest(system.getNextGuestId());
        system.getGuest_list().add(guest);
        system.increaseGuestId();
        return guest;
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
        buy_product.setBuy_amount(amount);


        for(PurchaseProcess p:g.getPurchaseProcesslist()){
            if(p.getStore().getName().equals(store_name)){
                p.getShoppingBag().getProducts_names().add(product_name);
                p.getShoppingBag().getProducts().add(buy_product);
                processExist=true;
            }
        }

        if(!processExist){
            PurchaseProcess p=new PurchaseProcess(g,SystemManage_Facade.get_store(store_name),new ShoppingBag(new ArrayList<>()));
            g.getShoppingCart().getShopping_bag_list().add(p.getShoppingBag());
            p.getShoppingBag().getProducts_names().add(product_name);
            p.getShoppingBag().getProducts().add(buy_product);

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


    public static List<String> getProductsInCartForGuest(int id){
        Guest g=getGuest(id);
        assert g != null;
        return g.getShoppingCart().getProductsNames();
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

    public static void add_subscriber(String user_name, String password) {
        Subscriber subscriber = new Subscriber(user_name, password);
        system.getUser_list().add(subscriber);
    }

    public static  List<PurchaseProcess> View_purchase(String user_name) { //3.7
        Subscriber subscriber = system.get_subscriber(user_name);
        return subscriber.getPurchaseProcesslist();
    }

    public static void Add_Query(String user_name,String query) { //3.5  -- #TODO add test that the query inserted
        Subscriber subscriber = system.get_subscriber(user_name);
        subscriber.getQuries().add(query);
    }

    public static boolean addProductReview(String user_name, String product_name, String store_name, String review_data, int rank) {
        Subscriber subscriber = system.get_subscriber(user_name);
        Product reviewedProduct = system.get_store(store_name).getProduct(product_name);
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
                        reviewedProduct.addReview(product_review);
                    }
            }
        }
        return isPurchased;
    }


    public static boolean saveProductForSubscriber(String userName,String product_name, String store_name,int amount){

        Subscriber s = SystemManage_Facade.get_subscriber(userName);
        boolean processExist = false;
        Product product=null;
        Store store = SystemManage_Facade.get_store(store_name);
        if(store == null) return false;

        product = getProductInStore(product_name, store);

        if(product == null || amount > product.getSupplied_amount()) return false;

        Product buy_product = new Product(product.getName(),product.getPrice(),product.getSupplied_amount(),product.getStore());
        buy_product.setBuy_amount(amount);

        for(PurchaseProcess p:s.getPurchaseProcesslist()){
            if(!p.isFinished() && p.getStore().getName().equals(store_name)){
                p.getShoppingBag().getProducts_names().add(product_name);
                p.getShoppingBag().getProducts().add(buy_product);
                processExist=true;
            }
        }

        if(!processExist){
            PurchaseProcess p = new PurchaseProcess(s,store,new ShoppingBag(new ArrayList<>()));
            s.getShoppingCart().getShopping_bag_list().add(p.getShoppingBag());
            p.getShoppingBag().getProducts_names().add(product_name);
            p.getShoppingBag().getProducts().add(buy_product);
            s.getPurchaseProcesslist().add(p);
        }
        return true;
    }

    public static List<String> getProductsInCartForSubscriber(String id){
        Subscriber g=get_subscriber(id);
        assert g != null;
        return g.getShoppingCart().getProductsNames();
    }

    ////////////////////////////////////////////////////////
    public static boolean check_password(String user_name, String password) {
        EncryptImp encryptImp = system.getEncryptImp();
        Subscriber subscriber = system.get_subscriber(user_name);
        String password_dyc = encryptImp.decrypt(subscriber.getPassword());
        return password_dyc.equals(encryptImp.decrypt(password));
    }

    public static Permission string_to_permission(String s) {
        Permission permission[] = Permission.values();
        for (Permission p : permission)
            if (s.equalsIgnoreCase(String.valueOf(p)))
                return p;


        return null;
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
    public static List<Store> getAllStores(){
        return system.getStore_list();
    }

}
