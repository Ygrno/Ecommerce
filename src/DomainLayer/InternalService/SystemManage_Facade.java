package DomainLayer.InternalService;

import DomainLayer.System;
import DomainLayer.User.Subscriber;
import Encryption.EncryptImp;

public class SystemManage_Facade implements InternalService {

    private static System system;

    public static void init_system(){
        system = System.getSystem();
    }

    public static boolean is_initialized() {
        return System.initialized;
    }

    public static boolean find_subscriber(String user_name){
        Subscriber subscriber = system.get_subscriber(user_name);
        return subscriber != null;
    }

    public static void add_subscriber(String user_name, String password)
    {
            Subscriber subscriber = new Subscriber(user_name, password);
            system.getUser_list().add(subscriber);
    }

    public static boolean check_password (String user_name, String password){
        EncryptImp encryptImp = system.getEncryptImp();
        Subscriber subscriber = system.get_subscriber(user_name);
        String password_dyc = encryptImp.decrypt(subscriber.getPassword());
        return password_dyc.equals(encryptImp.decrypt(password));
    }




}
