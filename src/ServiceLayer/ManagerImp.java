package ServiceLayer;

import DomainLayer.InternalService.SystemManage_Facade;
import Encryption.EncryptProxy;


public class ManagerImp implements IManager {

    @Override
    public boolean init_system(boolean file) {
        //Init System Flow:
        //---------------------------

        //Initialize System.
        SystemManage_Facade.init_system();

        //Sign-Up Admin
        GuestImp guestImp = new GuestImp();
        if(!guestImp.sign_up("Admin", "Password")) return false;

        //True = Read File, False = Don't read file
        if(file) {
            if (!InitSystemState.init()) {
                java.lang.System.out.println("failed to init system");
            }
        }

        //Connect Real Encryption
        EncryptProxy iEncrypt = EncryptionDriver.getEncryption();
        return iEncrypt.init();
    }

    @Override
    public boolean close_store_permanently(String store_name) {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean remove_subscriber(String user_name) {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean respond_complaints() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public String view_history_store(String store_name) {
        if(!SystemManage_Facade.is_initialized()) return null;
        return SystemManage_Facade.get_store_purchase_process(store_name);
    }

    @Override
    public String view_history_costumer(String user_name) {
        if(!SystemManage_Facade.is_initialized()) return  null;
        return SystemManage_Facade.get_subscriber_purchase_process(user_name);

    }

    @Override
    public boolean watch_system_log() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public String today_revenue() {
        if(!SystemManage_Facade.is_initialized()) return null;
        return SystemManage_Facade.today_revenue();

    }

    @Override
    public String date_revenue(String date) {
        if(!SystemManage_Facade.is_initialized()) return null;
        return SystemManage_Facade.date_revenue(date);

    }
}
