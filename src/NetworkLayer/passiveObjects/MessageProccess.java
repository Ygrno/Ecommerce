package NetworkLayer.passiveObjects;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Product;
import DomainLayer.Store.Store;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageProccess {


    public static void Login(MessagingProtocol protocol, JSONObject request) throws Exception {
        JSONObject response = new JSONObject();
        response.put("req", request.get("req"));
        if (SubscribersManage_Facade.login((String) request.get("username"), (String) request.get("password")))
            response.put("res", "true");
        else
            response.put("res", "false");
        response.put("username", request.get("username"));
        protocol.send(response);
        //{req:"login", res:"true", username:"tamernassar"}
    }

    public static void getStores(MessagingProtocol protocol, JSONObject request) throws Exception {
        List<Store> stores = SystemManage_Facade.getAllStores();

        List<JSONObject> res = new ArrayList<>();

        for (Store s : stores) {
            String name = s.getName();
            boolean is_open = s.isIs_open();

            JSONObject o = new JSONObject();
            o.put("name", name);
            o.put("is_open", is_open);

            res.add(o);
        }

        for (JSONObject o : res) {
            o.put("req", request.get("req"));
            protocol.send(o);
        }


    }


    public static void getStore(MessagingProtocol protocol, JSONObject request) throws Exception {
        List<Store> stores = SystemManage_Facade.getAllStores();
        String store = request.getString("store");

        for (Store s : stores) {
            if(s.getName().equals(store)) {
                JSONObject o = new JSONObject();
                o.put("name", store);
                o.put("is_open", s.isIs_open());
                o.put("req", request.get("req"));

                protocol.send(o);

                return;
            }
        }

    }

    public static void Signup(MessagingProtocol protocol, JSONObject request) throws Exception{
        JSONObject response = new JSONObject();
        response.put("req", request.get("req"));
        if(SubscribersManage_Facade.signup((String)request.get("username"),(String)request.get("password")))
            response.put("res", "true");
        else
            response.put("res", "false");
        response.put("username", request.get("username"));
        protocol.send(response);
    }

    public static void searchProductStores(MessagingProtocol protocol, JSONObject request) throws Exception {
        String name = request.getString("name");
        HashMap<String, Double> stores_holding_product = SystemManage_Facade.searchProductStores(name);

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
       // List<Product> products = SystemManage_Facade.get_products_of_store(store);
        String[][] products = SystemManage_Facade.get_products_of_store(store);
     /*   for(Product p : products){
            JSONObject o = new JSONObject();
            o.put("price",p.getPrice());
            o.put("amount",p.getAmount());
            o.put("name",p.getName());
            o.put("store", p.getStore().getName());
            o.put("req", request.get("req"));
*/

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


}
