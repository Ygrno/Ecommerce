package NetworkLayer.passiveObjects;

import ServiceLayer.ISubscriber;
import ServiceLayer.SubscriberImp;
import org.json.JSONObject;

import java.util.List;

public class SubscriberMessageProccess {
    private static ISubscriber subscriber=new SubscriberImp();

    public static void saveProducts(MessagingProtocol protocol, JSONObject request) throws Exception {
        String storeName = request.getString("store");
        String userName = request.getString("userName");
        String productName = request.getString("product");
        boolean b= subscriber.save_products(userName,productName,storeName);
        JSONObject o=new JSONObject();
        o.put("success", b);
        o.put("req", request.get("req"));
        protocol.send(o);
    }

    public static void viewCart(MessagingProtocol protocol, JSONObject request) throws Exception{
        String userName = request.getString("userName");
        List<JSONObject> products=subscriber.watch_products_in_cart(userName);
        for(JSONObject o:products){
            o.put("req", request.get("req"));
            protocol.send(o);
        }
    }

    public static void buyCart(MessagingProtocol protocol, JSONObject request) throws Exception{
        
    }
}
