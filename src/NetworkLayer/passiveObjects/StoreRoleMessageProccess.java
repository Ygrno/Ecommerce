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

        boolean b = storeRole.assign_store_owner(storename,user_assign);

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

    public static void add_store_conditioned_discount(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");
        String productname = request.getString("product");
        String discountName = request.getString("name");
        double discountPerc = request.getDouble("percentage");
        int amount = request.getInt("amount");
        int sum = request.getInt("sum");
        int dueDate = request.getInt("due_date");

        boolean b = storeRole.add_conditioned_discount(username,storename,productname,discountName,discountPerc,dueDate,amount,sum);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void add_store_complex_discount(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");
        String discountName = request.getString("name");
        int dueDate = request.getInt("due_date");
        String type = request.getString("type");
        String discounts = request.getString("discounts");
        String [] dis= discounts.split(" ");


        boolean b = storeRole.add_complex_discount(username,storename,discountName,dis,type,dueDate);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void delete_discount(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");
        String discountName = request.getString("name");

        boolean b = storeRole.delete_discount(username,storename,discountName);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void create_store_simple_buyPolicy(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");
        String productName = request.getString("product");
        int policyType = request.getInt("type");
        int minProducts = request.getInt("minProducts");
        int maxProducts = request.getInt("maxProducts");
        int minCost = request.getInt("minCost");
        int maxCost = request.getInt("maxCost");
        int maxQuantity = request.getInt("maxQuantity");
        int minQuantity = request.getInt("minQuantity");
        int day = request.getInt("day");
        String description = request.getString("desc");

        System.out.println(minProducts);
        System.out.println(maxProducts);
        System.out.println(minQuantity);
        System.out.println(maxQuantity);

        boolean b = storeRole.create_store_simple_buyPolicy(username,storename,policyType,description,0,productName,minProducts,maxProducts,minCost,maxCost,minQuantity,maxQuantity,day);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void edit_store_simple_buyPolicy(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");
        String productName = request.getString("product");
        int policy_id = request.getInt("policy_id");
        int policyType = request.getInt("type");
        int minProducts = request.getInt("minProducts");
        int maxProducts = request.getInt("maxProducts");
        int minCost = request.getInt("minCost");
        int maxCost = request.getInt("maxCost");
        int maxQuantity = request.getInt("maxQuantity");
        int minQuantity = request.getInt("minQuantity");
        int day = request.getInt("day");
        String description = request.getString("desc");

        boolean b = storeRole.edit_store_simple_buyPolicy(username,storename,policyType,description,policy_id,productName,minProducts,maxProducts,minCost,maxCost,minQuantity,maxQuantity,day);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void create_store_complex_buyPolicy(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");
        String policy_ids = request.getString("policy_ids");
        int type = request.getInt("type");
        String [] ids= policy_ids.split(" ");
        int[] int_ids=new int[ids.length];
        for(int i=0;i<ids.length;i++){
            int_ids[i] = Integer.parseInt(ids[i]);
        }
        String description = request.getString("desc");
        boolean b = storeRole.create_store_complex_buyPolicy(username,storename,description,0,int_ids,type);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void edit_store_complex_buyPolicy(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");
        int policy_id = request.getInt("policy_id");
        int new_policy_id = request.getInt("new_policy_id");
        String act =request.getString("act");

        boolean b = storeRole.edit_store_complex_buyPolicy(username,storename,policy_id,new_policy_id,act);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void remove_store_buyPolicy(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("user_name");
        String storename = request.getString("store");
        int policy_id = request.getInt("policy_id");

        boolean b = storeRole.remove_store_buyPolicy(username,storename,policy_id);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void get_policies_ids_in_store(MessagingProtocol protocol, JSONObject request) throws Exception{
        String store = request.getString("store");

        List<JSONObject> policies = storeRole.get_policies_ids_in_store(store);

        JSONArray arr = new JSONArray();
        for(JSONObject p : policies){
            arr.put(p);
        }

        JSONObject o = new JSONObject();
        o.put("policies", arr);
        o.put("req", request.get("req"));
        protocol.send(o);
    }

    public static void get_discounts_in_store(MessagingProtocol protocol, JSONObject request) throws Exception{
        String store = request.getString("store");
        String username = request.getString("user_name");
        List<JSONObject> policies = storeRole.view_all_discount(username,store);

        JSONArray arr = new JSONArray();
        for(JSONObject p : policies){
            arr.put(p);
        }

        JSONObject o = new JSONObject();
        o.put("policies", arr);
        o.put("req", request.get("req"));
        protocol.send(o);
    }

}
