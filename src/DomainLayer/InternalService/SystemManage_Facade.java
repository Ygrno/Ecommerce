package DomainLayer.InternalService;

import DomainLayer.*;
import DomainLayer.Roles.Permission;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import Encryption.EncryptImp;

import java.util.ArrayList;
import java.util.List;

import static DomainLayer.Roles.Permission.VIEW_AND_RESPOND_TO_USERS;

public class SystemManage_Facade implements InternalService {

    private static System system;

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
    public static List<Store> getAllStores(){
        return system.getStore_list();
    }

    ////////////////////////////////////////////////////////


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
                    if (p.equals(product_name) && pp.getStore().getName().equals(store_name)) {
                        isPurchased = true;
                        product_review = new ProductReview(subscriber,rank,review_data);
                        reviewedProduct.addReview(product_review);

                    }

            }

        }
        return isPurchased;
    }

    public static List<Permission> strings_to_permissions(List<String> strings) {
        List<Permission> permissions = new ArrayList<>();
        ;
        for (String s : strings) {
            permissions.add(string_to_permission(s));
        }
        return permissions;


    }
}
