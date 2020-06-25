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
    @Transient
    List<JSONObject> response = new ArrayList<>();

    public StoreOwner(Subscriber user, Store store){
        this.user = user;
        this.store = store;
        assigned_by=new ArrayList<Role>();
        assigned=new ArrayList<Role>();

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

    public void addAssigned_by(ArrayList<Role> assigned_by) {
        for(Role R:assigned_by)this.assigned_by.add(R);
    }
    public void setAssigned_by(ArrayList<Role> assigned_by) {
        this.assigned_by=assigned_by;
    }

    public void addAssigned_by(Role assigned_by){
        this.assigned_by.add(assigned_by);
    }

    public ArrayList<Role> GetAssinged() {return assigned;}
    public ArrayList<Role> GetAssinged_by() {return assigned_by;}

}
