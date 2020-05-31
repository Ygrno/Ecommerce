package NetworkLayer.passiveObjects;

import ServiceLayer.ISubscriber;
import ServiceLayer.SubscriberImp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SubscriberMessageProccess {
    private static ISubscriber subscriber=new SubscriberImp();

    public static void saveProductForSubscriber(MessagingProtocol protocol, JSONObject request) throws Exception {
        String storeName = request.getString("store");
        String userName = request.getString("username");
        String productName = request.getString("product");
        boolean b= subscriber.save_products(userName,productName,storeName);
        JSONObject o=new JSONObject();
        System.out.println(request.get("req"));
        o.put("success", b);
        o.put("req", request.get("req"));
        protocol.send(o);
    }

    public static void viewCart(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("username");
        List<JSONObject> products=subscriber.watch_products_in_cart(username);

        if(products == null) return;

        JSONArray jarr = new JSONArray();
        for(JSONObject o : products){
            jarr.put(o);
        }

        JSONObject l = new JSONObject();
        l.put("productsInCart", jarr);
        l.put("req", request.get("req"));

        protocol.send(l);
    }

    public static void buyCart(MessagingProtocol protocol, JSONObject request) throws Exception{
        
    }
}
