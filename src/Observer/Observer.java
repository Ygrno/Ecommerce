package Observer;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import NetworkLayer.main;
import NetworkLayer.passiveObjects.MessagingProtocol;
import NetworkLayer.passiveObjects.SubscriberMessageProccess;
import ServiceLayer.Log;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Observer {
    public static void update(JSONObject json) throws Exception {

        SubscribersManage_Facade.addNotification(json);
        SubscriberMessageProccess.send_notification(MessagingProtocol.getInstance());
    }
}
