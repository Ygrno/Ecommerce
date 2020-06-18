package NetworkLayer.passiveObjects;

import DomainLayer.InternalService.SubscribersManage_Facade;
import ServiceLayer.IStoreRole;
import ServiceLayer.StoreRoleImp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoreRoleMessageProccess {
    private static IStoreRole storeRole= new StoreRoleImp();

    public static void add_store_product(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store_name");
        String productname = request.getString("product");
        int price = request.getInt("price");
        int amount = request.getInt("amount");

        boolean b = storeRole.add_store_product(username,storename,productname,price,amount);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void edit_store_product(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");
        String productname = request.getString("product");
        String newProductname = request.getString("newp");
        int price = request.getInt("price");
        int amount = request.getInt("amount");

        boolean b = storeRole.edit_store_product(username,storename,productname,newProductname,price,amount);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void remove_store_product(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store_name");
        String productname = request.getString("product_name");

        boolean b = storeRole.remove_store_product(username,storename,productname);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void add_store_visible_discount(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");
        String productname = request.getString("product");
        String discountName = request.getString("name");
        double discountPerc = request.getDouble("percentage");
        int dueDate = request.getInt("due_date");

        boolean b = storeRole.add_visible_discount(username,storename,discountName,discountPerc,dueDate,productname);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);

    }


    public static void assign_store_owner(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store_name");
        String user_assign = request.getString("user_assign");

        boolean b = storeRole.assign_store_owner(username,storename,user_assign);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void remove_store_owner(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store_name");
        String user_assign = request.getString("user_assign");

        boolean b = storeRole.remove_store_owner(username,storename,user_assign);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void assign_store_manager(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store_name");
        String user_assign = request.getString("user_assign");

        boolean b = storeRole.assign_store_manager(username,storename,user_assign);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void edit_manager_permissions(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store_name");
        String user_assign = request.getString("user_assign");
        JSONArray permissions = request.getJSONArray("permissions");
        ArrayList<String> pms = new ArrayList<>();
        for(int i=0;i<permissions.length();i++){
            pms.add(permissions.getString(i));
        }

        boolean b = storeRole.edit_manager_permissions(username,storename,user_assign,pms);

        System.out.println(b);


        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);

    }

    public static void get_user_permissions(MessagingProtocol protocol, JSONObject request) throws Exception {
        String username = request.getString("username");
        String store = request.getString("store");

        List<JSONObject> pms = storeRole.get_user_permissions(username, store);

        JSONArray arr = new JSONArray();
        for(JSONObject p : pms){
            arr.put(p);
        }

        JSONObject o = new JSONObject();
        o.put("req", request.get("req"));
        o.put("username", username);
        o.put("store", store);
        o.put("permissions", arr);
        protocol.send(o);
    }
    public static void remove_store_manager(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store_name");
        String user_assign = request.getString("user_assign");

        boolean b = storeRole.remove_store_manager(username,storename,user_assign);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void close_store(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store_name");

        boolean b = storeRole.close_store(username,storename);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void view_and_respond_to_questions(MessagingProtocol protocol, JSONObject request) throws Exception{

    }


    public static void watch_store_history(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");

        String s = storeRole.watch_store_history(username,storename);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("store_history", s);
        protocol.send(o);
    }

}
