package DomainLayer.Roles;


import DomainLayer.Store.Store;
import DomainLayer.User.Subscriber;
import org.json.JSONObject;
import org.json.JSONString;

import java.util.ArrayList;
import java.util.List;

public class StoreOwner extends StoreRole {
    List<JSONObject> response = new ArrayList<>();
    public StoreOwner(Subscriber user, Store store){
        this.user = user;
        this.store = store;
    }
    public List<JSONObject> getResponse(){return response;}
}
