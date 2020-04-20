package ServiceLayer;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import Encryption.EncryptImp;

import java.util.List;


public class GuestImp implements IGuest {

    @Override
    public boolean sign_up(String user_name, String password) {

        if(!SystemManage_Facade.is_initialized()) return false;
        EncryptImp encryption = new EncryptImp();
        if(!encryption.connect()) return false;
        password = encryption.encrypt(password);



        if(!SystemManage_Facade.find_subscriber(user_name)) {
            SystemManage_Facade.add_subscriber(user_name, password);
            return true;
        }

        return false;
    }

    @Override
    public boolean login(String user_name, String password) {

        if(user_name.equals("Admin") && password.equals("Password")) SystemManage_Facade.init_system();
        if(!SystemManage_Facade.is_initialized()) return false;
        if(SystemManage_Facade.find_subscriber(user_name) && SystemManage_Facade.check_password(user_name,password)){
            SubscribersManage_Facade.subscriber_login_state(user_name,true);
            return true;
        }
        return false;
    }

    @Override
    public boolean view_products_information_store(String store_name) {
        if(!SystemManage_Facade.is_initialized()) return false;
        List<Product> products = SystemManage_Facade.get_products_of_store(store_name);
        if (products==null) return false; //todo- change to massage for user- wrong store_name
        return true;
    }

    @Override
    public boolean search_products(String product_name) {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;

    }

    @Override
    public boolean save_products(String product_name, String store_name) {
        if(!SystemManage_Facade.is_initialized()) return false;
        return true;
    }

    @Override
    public boolean watch_products_in_cart() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return true;
    }

    @Override
    public boolean buy_products_in_cart() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return true;
    }
}
