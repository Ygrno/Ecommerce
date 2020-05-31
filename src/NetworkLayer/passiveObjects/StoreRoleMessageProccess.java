package NetworkLayer.passiveObjects;

import ServiceLayer.IStoreRole;
import ServiceLayer.StoreRoleImp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class StoreRoleMessageProccess {
    private static IStoreRole storeRole= new StoreRoleImp();

    public static void add_store_product(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store_name");
        String productname = request.getString("product_name");
        int price = request.getInt("product_price");
        int amount = request.getInt("product_amount");

        boolean b = storeRole.add_store_product(username,storename,productname,price,amount);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }


    public static void edit_store_product(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store_name");
        String productname = request.getString("product_name");
        String newProductname = request.getString("new_product_name");
        int price = request.getInt("product_price");
        int amount = request.getInt("product_amount");

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
        String storename = request.getString("store_name");
        String productname = request.getString("product_name");
        String discountName = request.getString("discount_name");
        double discountPerc = request.getDouble("discount_percentage");
        int dueDate = request.getInt("due_date");

        boolean b = storeRole.add_store_visible_discount(username,storename,productname,discountName,discountPerc,dueDate);

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

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
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
        String storename = request.getString("store_name");

        String s = storeRole.watch_store_history(username,storename);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("store_history", s);
        protocol.send(o);
    }

}
