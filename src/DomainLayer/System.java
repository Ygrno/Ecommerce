package DomainLayer;

import DAL.DBAccess;
import DomainLayer.ExternalSerivce.ProductFinanceService;
import DomainLayer.ExternalSerivce.ProductSupplyService;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Roles.SystemManger;
import DomainLayer.Store.DiscountPolicy;
import DomainLayer.Store.Store;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import Encryption.EncryptProxy;
import ServiceLayer.SubscriberImp;
import Stubs.ExternalFinanceServiceStub;
import Stubs.ExternalSupplyServiceStub;

import java.util.ArrayList;
import java.util.List;

public class System {

    private static System SYSTEM_SINGLETON = null;
    private SubscriberImp SubImp;
    private List<Subscriber> user_list;
    private List<Guest> guest_list;
    private int nextGuestId=0;
    public static int nextManagerId=0;
    public static int nextSystemManagerId=0;
    public static int nextOwnerId=0;
    public static int nextComplexDiscountId=0;
    public static int nextDiscountId=0;
    public static int nextConditionedDiscountId=0;
    public static int nextDiscountPolicyId = 0;
    public static int nextVisibleDiscountId=0;
    public static int nextPurchaseProcessId=0;
    public static int nextShoppingBagId=0;
    public static int nextShoppingCartId=0;
    public static int nextUserId=0;
    static int nextProductId=0;
    public static int nextStoreId = 0;
    public static DBAccess dbAccess=DBAccess.getInstance();
    private List<Store> store_list;
    public static boolean initialized = false;
    private ProductSupplyService productSupplyService;
    private ProductFinanceService productFinanceService;
    private StoreOwner storeowner;

    private System(){
        user_list = dbAccess.select(Subscriber.class);
        guest_list=new ArrayList<>();
        store_list = dbAccess.select(Store.class);

        //dummy
        store_list.add(new Store("store"));

        initialized = true;




        productFinanceService = new ProductFinanceService(new ExternalFinanceServiceStub());
        productSupplyService = new ProductSupplyService(new ExternalSupplyServiceStub());

        productFinanceService.connect();
        productSupplyService.connect();
        nextGuestId=0;
        SubImp = new SubscriberImp();
    }


    public static System getSystem() {
        if(SYSTEM_SINGLETON == null) SYSTEM_SINGLETON = new System();
        return SYSTEM_SINGLETON;
    }

    public List<Subscriber> getUser_list() {
        return user_list;
    }


    public List<Store> getStore_list() { return store_list; }

    public void add_subscriber(Subscriber s){
        user_list.add(s);
    }

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

    public List<Guest> getGuest_list() {
        return guest_list;
    }

    public SubscriberImp SubImp() {
        return SubImp;
    }

    public int getNextGuestId() {
        return nextGuestId;
    }

    public void increaseGuestId(){
        this.nextGuestId++;
    }

    public StoreOwner storeOwner(String username,String store ){
        Subscriber sub = get_subscriber(username);
        return new StoreOwner(sub , this.get_store(store));
    }
    public void clearSystem(){
        SYSTEM_SINGLETON = null;
    }
}
