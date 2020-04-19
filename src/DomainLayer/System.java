package DomainLayer;

import DomainLayer.ExternalSerivce.ProductFinanceService;
import DomainLayer.ExternalSerivce.ProductSupplyService;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.SystemManger;
import DomainLayer.Store.Store;
import DomainLayer.User.Subscriber;
import DomainLayer.User.User;
import Encryption.EncryptImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class System {

    private static System SYSTEM_SINGLETON = null;

    private List<Subscriber> user_list;
    private List<Store> store_list;
    public static boolean initialized = false;
    private ProductSupplyService productSupplyService;
    private ProductFinanceService productFinanceService;
    private EncryptImp encryptImp;

    private System(){
        user_list = new ArrayList<>();
        store_list = new ArrayList<>();
        initialized = true;
        Subscriber admin = new Subscriber("Admin","Password");
        SystemManger systemManger = new SystemManger(admin);
        user_list.add(admin);
        productFinanceService = new ProductFinanceService();
        productSupplyService = new ProductSupplyService();
        encryptImp = new EncryptImp();
        encryptImp.connect();
        productFinanceService.connect();
        productSupplyService.connect();
    }

    public static System getSystem() {
        if(SYSTEM_SINGLETON == null) SYSTEM_SINGLETON = new System();
        return SYSTEM_SINGLETON;
    }

    public List<Subscriber> getUser_list() {
        return user_list;
    }

    public List<Store> getStore_list() { return store_list; }

    public Subscriber get_subscriber(String user_name){
        Subscriber subscriber = null;
        for (Subscriber user : user_list){
            if(user.getName().equals(user_name)) subscriber = user;
        }
        return subscriber;
    }
    public Store get_store(String store_name){
        Store store = null;
        for (Store s : store_list){
            if(s.getName().equals(store_name)) store = s;
        }
        return store;
    }


    public ProductSupplyService getProductSupplyService() {
        return productSupplyService;
    }

    public void setProductSupplyService(ProductSupplyService productSupplyService) {
        this.productSupplyService = productSupplyService;
    }

    public ProductFinanceService getProductFinanceService() {
        return productFinanceService;
    }

    public void setProductFinanceService(ProductFinanceService productFinanceService) {
        this.productFinanceService = productFinanceService;
    }

    public EncryptImp getEncryptImp() {
        return encryptImp;
    }

    public void setEncryptImp(EncryptImp encryptImp) {
        this.encryptImp = encryptImp;
    }
}
