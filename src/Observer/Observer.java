package Observer;

import DomainLayer.InternalService.SubscribersManage_Facade;
import NetworkLayer.passiveObjects.MessagingProtocol;
import NetworkLayer.passiveObjects.SubscriberMessageProccess;
import org.json.JSONObject;

public abstract class Observer {
    public static void update(JSONObject json) throws Exception {

        SubscribersManage_Facade.addNotification(json);
        SubscriberMessageProccess.send_notification(MessagingProtocol.getInstance());
    }
}
