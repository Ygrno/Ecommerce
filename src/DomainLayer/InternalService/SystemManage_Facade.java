package DomainLayer.InternalService;

import DomainLayer.DealDetails;
import DomainLayer.ExternalSerivce.ProductFinanceService;
import DomainLayer.Product;
import DomainLayer.Roles.Permission;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
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

    public static List<Store> get_stores() {

        return system.getStore_list();
    }



    public static boolean buy(DealDetails dd){
        return system.getProductFinanceService().tryToBuy(dd);
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

    public static List<Permission> strings_to_permissions(List<String> strings) {
        List<Permission> permissions = new ArrayList<>();
        ;
        for (String s : strings) {
            permissions.add(string_to_permission(s));
        }
        return permissions;


    }

    public static List<Product> get_products_of_store(String store_name) {
        List<Store> storeList = system.getStore_list();
        List<Product> productList = null;
        for (Store s : storeList) {
            if (s.getName().equals(store_name))
                productList = s.getProduct_list();
        }
        return productList;
    }
}
