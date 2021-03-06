package NetworkLayer.passiveObjects;

import DomainLayer.System;
import Observer.Observer;
import ServiceLayer.ISubscriber;
import ServiceLayer.SubscriberImp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class SubscriberMessageProccess {
    private static ISubscriber subscriber=new SubscriberImp();

    public static void saveProductForSubscriber(MessagingProtocol protocol, JSONObject request) throws Exception {
        String storeName = request.getString("store");
        String userName = request.getString("username");
        String productName = request.getString("product");
        boolean b= subscriber.save_products(userName,productName,storeName,1);
        JSONObject o=new JSONObject();
//        System.out.println(request.get("req"));
        o.put("success", b);
        o.put("req", request.get("req"));
        protocol.send(o);
    }

    public static void viewCart(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("username");
        List<JSONObject> products=subscriber.watch_products_in_cart(username);
        Double totalPrice=subscriber.getTotalPriceOfCart(username);
        products=subscriber.watch_products_in_cart(username);

        if(products == null) return;

        JSONArray jarr = new JSONArray();
        for(JSONObject o : products){
            jarr.put(o);
        }

        JSONObject l = new JSONObject();
        l.put("productsInCart", jarr);
        l.put("price",totalPrice);
        l.put("req", request.get("req"));

        protocol.send(l);
    }

    public static void buyCart(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("username");
        String buyerName=request.getString("buyer_name");
        String creditCardNumber=request.getString("creditCardNumber");
        String expireDate=request.getString("expireDate");
        int cvv=request.getInt("cvv");
        String buyer_id=request.getString("buyer_id");
        String address = request.getString("address");
        String city = request.getString("city");
        String country = request.getString("country");
        String zip = request.getString("zip");

        boolean b = subscriber.buy_products_in_cart(username,buyerName,creditCardNumber,expireDate,cvv, buyer_id,address,city,country,zip);
        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void signOut(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("username");
        boolean b = subscriber.sign_out(username);
        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void open_store(MessagingProtocol protocol, JSONObject request) throws Exception {
        String userName = request.getString("username");
        String storeName = request.getString("store_name");

        boolean b = subscriber.open_store(userName,storeName);
        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void writeReview(MessagingProtocol protocol, JSONObject request) throws Exception {
        String userName = request.getString("username");
        String storeName = request.getString("store_name");
        String productName = request.getString("product_name");
        String review_data = request.getString("review_data");
        int rank = request.getInt("rank");

        boolean b = subscriber.write_review(userName,productName,storeName,review_data,rank);

        JSONObject o = new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void send_query_to_store(MessagingProtocol protocol, JSONObject request) throws Exception {
        String userName = request.getString("username");
        String query = request.getString("query");

        boolean b = subscriber.send_query_to_store(userName,query);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void view_purchase_history(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("username");
        String l = subscriber.view_purchase_history_string(username);

        JSONObject o = new JSONObject();
        o.put("req", request.get("req"));
        o.put("products_in_history", l);

        protocol.send(o);
    }

    public static void getNotifications(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("username");
        List<JSONObject> l = Observer.GetObserver().getNotifications(username);
        if(l == null) return;

        JSONArray jarr = new JSONArray();
        for(JSONObject o : l){
            jarr.put(o);
        }
        JSONObject o = new JSONObject();
        o.put("notifications", jarr);
        o.put("req", request.get("req"));
        protocol.send(o);
    }

    public static void send_notification(MessagingProtocol protocol,String username) throws Exception{
        JSONObject o = new JSONObject();
        o.put("req","new_notification");
        o.put("username",username);
        protocol.send(o);
    }

    public  static void remove_product_cart_subscriber(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("username");
        String productName=request.getString("product_name");
        String storeName=request.getString("store_name");

        boolean b = subscriber.remove_product_from_cart(username,productName,storeName);
        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


}
