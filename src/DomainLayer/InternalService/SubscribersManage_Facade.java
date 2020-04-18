package DomainLayer.InternalService;

import DomainLayer.Product;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreManger;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Roles.StoreRole;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;

import java.util.List;

public class SubscribersManage_Facade implements InternalService {

    private static System system;

    public static void subscriber_login_state (String user_name, boolean state){
        system.get_subscriber(user_name).setLogged_in(state);
    }

    public static boolean check_if_logged_in(String user_name){
        if(System.initialized){
            return system.get_subscriber(user_name).isLogged_in();
        }
        return false;
    }

    public static void create_store(String user_name, String store_name) {

            Subscriber subscriber = system.get_subscriber(user_name);

            Store store = new Store(store_name);

            StoreManger storeManger = new StoreManger(subscriber, store);

            store.getRoles().add(storeManger);

            system.getStore_list().add(store);
    }

    public static void add_product_to_store(String user_name, String store_name, String product_name, int product_price, int product_amount) {
        Subscriber subscriber = system.get_subscriber(user_name);
        StoreRole store_role = subscriber.get_role_at_store(store_name);
        store_role.store.getProduct_list().add(new Product(product_name,product_price,product_amount,store_role.store));

    }


}
