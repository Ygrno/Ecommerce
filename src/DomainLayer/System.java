package DomainLayer;


import DAL.DBAccess;

import DomainLayer.Roles.StoreOwner;


import ExternalService.ProductFinanceServiceAdapter;
import ExternalService.ProductSupplyServiceAdapter;
import DomainLayer.Roles.StoreOwner;

import DomainLayer.Store.Store;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import ExternalService.Mockups.ExternalFinanceServiceMock;
import ExternalService.Mockups.ExternalSupplyServiceMock;
import Observer.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class System {

    private static System SYSTEM_SINGLETON = null;
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
    private ProductSupplyServiceAdapter productSupplyService = new ProductSupplyServiceAdapter();
    private ProductFinanceServiceAdapter productFinanceService = new ProductFinanceServiceAdapter();
    private StoreOwner storeowner;

    private System() throws Exception {

        user_list = Collections.synchronizedList(new  ArrayList<>());
        guest_list = Collections.synchronizedList(new  ArrayList<>());
        store_list = Collections.synchronizedList(new  ArrayList<>());
        user_list = dbAccess.select(Subscriber.class);
        guest_list=new ArrayList<>();
        store_list = dbAccess.select(Store.class);


        //dummy
        store_list.add(new Store("store"));

        initialized = true;

        nextGuestId=0;
        //SubImp = new SubscriberImp();
        Observer.GetObserver();
    }


    public static System getSystem() throws Exception {
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


    public ProductSupplyServiceAdapter getProductSupplyService() {
        return productSupplyService;
    }

    public void setProductSupplyService(ProductSupplyServiceAdapter productSupplyService) {
        this.productSupplyService = productSupplyService;
    }

    public ProductFinanceServiceAdapter getProductFinanceService() {
        return productFinanceService;
    }

    public void setProductFinanceService(ProductFinanceServiceAdapter productFinanceService) {
        this.productFinanceService = productFinanceService;
    }

    public List<Guest> getGuest_list() {
        return guest_list;
    }

/*    public SubscriberImp SubImp() {
        return SubImp;
    }*/

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
