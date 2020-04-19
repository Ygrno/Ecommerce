package DomainLayer.Roles;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.PurchaseProcess;
import DomainLayer.Store.Store;
import DomainLayer.User.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class SystemManger extends Role {

    public SystemManger(Subscriber user) {
        this.user = user;
    }
//all purches from evrery store
    public List<PurchaseProcess> allpurchases() {
        List<PurchaseProcess> all_purchases_processes = new ArrayList<>();
        List<Store> all_stores = SystemManage_Facade.getAllStores();
        for(Store s :all_stores){
            for(PurchaseProcess pp : s.getPurchase_process_list())
                all_purchases_processes.add(pp);
        }
        return all_purchases_processes;
    }
}
