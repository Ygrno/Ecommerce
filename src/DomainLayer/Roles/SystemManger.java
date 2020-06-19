package DomainLayer.Roles;

import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.PurchaseProcess;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="system_managers")
public class SystemManger extends Role {

    public SystemManger(Subscriber user){

        this.user = user;
        this.id = System.nextOwnerId++;
    }

    public SystemManger(){

    }


//    public List<PurchaseProcess> allPurchases() {
//        List<PurchaseProcess> all_purchases_processes = new ArrayList<>();
//        List<Store> all_stores = SystemManage_Facade.getAllStores();
//        for(Store s :all_stores){
//            for(PurchaseProcess pp : s.getPurchase_process_list())
//                if(pp.isFinished())
//                    all_purchases_processes.add(pp);
//        }
//        return all_purchases_processes;
//    }

}
