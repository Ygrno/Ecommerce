package NetworkLayer.passiveObjects;

import ServiceLayer.GuestImp;
import ServiceLayer.IGuest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuestMessageProccess {

    private static IGuest guestImp = new GuestImp();

    public static void Login(MessagingProtocol protocol, JSONObject request) throws Exception {
        JSONObject response = new JSONObject();
        response.put("req", request.get("req"));
        if (guestImp.login((String) request.get("username"), (String) request.get("password")))
            response.put("res", "true");
        else
            response.put("res", "false");
        response.put("username", request.get("username"));
        protocol.send(response);
        //{req:"login", res:"true", username:"tamernassar"}
    }

    public static void continueAsAguest(MessagingProtocol protocol, JSONObject request)throws Exception{
        int id=guestImp.addGuest();
        JSONObject o = new JSONObject();
        o.put("req", request.getString("req"));
        o.put("id", id);
        protocol.send(o);
    }

    public static void getStores(MessagingProtocol protocol, JSONObject request) throws Exception {
        List<JSONObject> stores = guestImp.getAllStores();
        for (JSONObject o : stores) {
            o.put("req", request.get("req"));
            protocol.send(o);
        }
    }


    public static void getStore(MessagingProtocol protocol, JSONObject request) throws Exception {
        List<JSONObject> stores = guestImp.getAllStores();
        String store = request.getString("store");
        for (JSONObject o : stores) {
            if(o.getString("name").equals(store)) {
                o.put("req", request.get("req"));
                protocol.send(o);
                return;
            }
        }

    }

    public static void Signup(MessagingProtocol protocol, JSONObject request) throws Exception{
        JSONObject response = new JSONObject();
        response.put("req", request.get("req"));
        if(guestImp.sign_up((String)request.get("username"),(String)request.get("password")))
            response.put("res", "true");
        else
            response.put("res", "false");
        response.put("username", request.get("username"));
        protocol.send(response);
    }

    public static void searchProductStores(MessagingProtocol protocol, JSONObject request) throws Exception {
        String name = request.getString("name");
        HashMap<String, Double> stores_holding_product = guestImp.search_products(name);

        List<JSONObject> res = new ArrayList<>();

        for (String store : stores_holding_product.keySet()) {
            Double price = stores_holding_product.get(store);
            JSONObject o = new JSONObject();
            o.put("store", store);
            o.put("price", price);
            o.put("product", name);
            res.add(o);
        }

        for (JSONObject o : res) {
            o.put("req", request.get("req"));
            protocol.send(o);
        }
    }

    public static void searchProductsOfStore(MessagingProtocol protocol, JSONObject request) throws Exception {
        String store = request.getString("store");
        String[][] products = guestImp.view_products_information_store(store);

        if(products==null)
            return;

        for(String[] p : products){
            JSONObject o = new JSONObject();
            o.put("name",p[0]);
            o.put("price",p[1]);
            o.put("amount",p[2]);
            o.put("store", p[3]);
            o.put("req", request.get("req"));

            protocol.send(o);
        }
    }


    public static void saveProductForGuest(MessagingProtocol protocol, JSONObject request) throws Exception{
        String storeName = request.getString("store");
        String productName = request.getString("product");
        int id = Integer.parseInt(request.getString("id"));
        boolean b=guestImp.save_products(id,productName,storeName,1);
        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }



    public  static void viewCart(MessagingProtocol protocol, JSONObject request) throws Exception{

        int id = request.getInt("id");
        List<JSONObject> products=guestImp.watch_products_in_cart(id);
        Double totalPrice=guestImp.getTotalPriceOfCart(String.valueOf(id));
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


    public  static void buyCart(MessagingProtocol protocol, JSONObject request) throws Exception{
        int id = request.getInt("id");
        String buyerName=request.getString("buyer_name");
        String creditCardNumber=request.getString("creditCardNumber");
        String expireDate=request.getString("expireDate");
        int cvv=request.getInt("cvv");
        String buyer_id =request.getString("buyer_id");

        boolean b = guestImp.buy_products_in_cart(id,buyerName,creditCardNumber,expireDate,cvv, buyer_id);
        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public  static void remove_product_cart_guest(MessagingProtocol protocol, JSONObject request) throws Exception{
        int id = request.getInt("id");
        String productName=request.getString("product_name");
        String storeName=request.getString("store_name");

        boolean b = guestImp.remove_product_from_cart(id,productName,storeName);
        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }







}
