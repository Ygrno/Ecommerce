package Observer;

import DomainLayer.InternalService.SubscribersManage_Facade;
import NetworkLayer.passiveObjects.MessagingProtocol;
import NetworkLayer.passiveObjects.SubscriberMessageProccess;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Observer {
    private ArrayList<JSONObject>ObserverSeen;
    private ArrayList<JSONObject>Observers;
    private static Observer _Observer;
    private Observer(){
        ObserverSeen = new ArrayList<JSONObject>();
        Observers = new ArrayList<JSONObject>();
    }

    public static Observer GetObserver(){
        if(_Observer == null) _Observer = new Observer();
            return _Observer;
    }

    public ArrayList<JSONObject> getObservers(){
        return Observers;
    }

    public ArrayList<JSONObject> getObserversSeen() throws Exception {
        return ObserverSeen;
    }

    public boolean CheckNotification(String username) throws Exception {
        for(JSONObject O : Observers)
            if(O.getString("username").equals(username))
                return true;
        return false;
    }

    //updates the Observers data
    public void update(JSONObject json) throws Exception {
        Observers.add(json);
        SubscriberMessageProccess.send_notification(MessagingProtocol.getInstance(), json.getString("username"));
    }

    //in a case that the user signs in again to the store then he receives a notifiacion (if needed)
    public void Notify(String username) throws Exception {
        SubscriberMessageProccess.send_notification(MessagingProtocol.getInstance(), username);
    }

    //returns all the notificaions
    public ArrayList<JSONObject> getNotifications(String username) throws Exception {
        ArrayList<JSONObject> notifications = new ArrayList<JSONObject>();
        for (JSONObject O : Observers)
            if(username.equals(O.getString("username")) ) {
                notifications.add(O);
            }
        for (JSONObject O : ObserverSeen)
            if(username.equals(O.getString("username"))) {
                notifications.add(O);
            }

        if(Observers!= null && Observers.size()>0) {
            Seen(username);
        }

        return notifications;
    }


    public void Seen(String username) throws JSONException {

        for (JSONObject O : Observers)
            if(username== O.get("username")) {
                Observers.remove(O);
                ObserverSeen.add(O);
            }
    }
}
