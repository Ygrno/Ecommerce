package DomainLayer.InternalService;

import DomainLayer.Roles.StoreOwner;
import DomainLayer.Roles.StoreRole;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.desktop.AppReopenedEvent;
import java.util.ArrayList;
import java.util.List;

public class OwnersApproval {
    private static ArrayList<JSONObject> Approvals;

    public OwnersApproval(){
        Approvals= new ArrayList<JSONObject>();
    }

    public static boolean add_owner_to_store(String store_name, String user_assign) throws Exception {
        if(!CheckApprovalExist(store_name,user_assign))
            return false;
        Subscriber subscriber = System.getSystem().get_subscriber(user_assign);
        if(subscriber == null) return false;
        if(!checkStoreoOwnersResponse(store_name))
            return false;
        StoreOwner storeOwner = new StoreOwner(subscriber,System.getSystem().get_store(store_name));
        subscriber.getRole_list().add(storeOwner);
        storeOwner.store.getRoles().add(storeOwner);
        AssignStoreOwners(storeOwner, store_name);
        return true;
    }

    public static boolean checkStoreoOwnersResponse(String store_name) throws Exception {
        for(StoreOwner store_role : System.getSystem().get_store(store_name).GetStoreOwners())
            for(JSONObject json : ((StoreOwner)store_role).getResponse()){
                if(json.getString("response").equals("no"))
                    return false;
            }
        return true;
    }

    public static void AssignStoreOwners(StoreOwner storeOwner,String store_name) throws Exception {
        for (StoreOwner StoreOwners : System.getSystem().get_store(store_name).GetStoreOwners()) {
            StoreOwners.getAssigned_users().add(storeOwner);
            storeOwner.setAssigned_by(StoreOwners);
        }
    }

    public ArrayList<JSONObject> GetApprovals(){
        return Approvals;
    }
    public static boolean CheckApprovalExist(String store, String user) throws JSONException {
        for(JSONObject O : Approvals){
            if(O.getString("store").equals(store) && O.getString("user").equals(user))
                return false;
        }
        return true;
    }
}
