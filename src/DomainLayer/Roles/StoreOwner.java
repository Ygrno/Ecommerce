package DomainLayer.Roles;


import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="stores_owners")
//@AttributeOverrides({
//        @AttributeOverride(name="id",column = @Column(name="id"))
//})
public class StoreOwner extends StoreRole {
    List<JSONObject> response = new ArrayList<>();
    public StoreOwner(Subscriber user, Store store){
        this.user = user;
        this.store = store;

    }
    public List<JSONObject> getResponse(){return response;}

    public boolean changeApproval(String user,String status ) throws JSONException {
        for(JSONObject O : response)
            if(O.getString("username").equals(user)) {
                O.put("status",status);
                return true;
            }

            return false;
    }
    public StoreOwner() {
    }
}
