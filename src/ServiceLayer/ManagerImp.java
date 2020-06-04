package ServiceLayer;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.PurchaseProcess;

import java.util.List;

public class ManagerImp implements IManager {

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
    public boolean view_history_store(String store_name) {
        if(!SystemManage_Facade.is_initialized()) return false;
        List<PurchaseProcess> ps = SystemManage_Facade.get_store_purchase_process(store_name);
        if(ps!= null){
            return true;
        }
        return false;
    }

    @Override
    public boolean view_history_costumer(String user_name) {
        if(!SystemManage_Facade.is_initialized()) return false;
        List<PurchaseProcess> ps = SystemManage_Facade.get_subscriber_purchase_process(user_name);
        if(ps!= null){
            return true;
        }
        return false;
    }

    @Override
    public boolean watch_system_log() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }
}
