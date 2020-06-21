package NetworkLayer.passiveObjects;

import ServiceLayer.IAdmin;
import ServiceLayer.AdminImp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ManagerMessageProccess {
    private static IAdmin manager = new AdminImp();

    public static void close_store_permanently(MessagingProtocol protocol, JSONObject request) throws Exception{
        String storename = request.getString("store_name");

        boolean b = manager.close_store_permanently(storename);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void remove_subscriber(MessagingProtocol protocol, JSONObject request) throws Exception{
        String username = request.getString("username");
        boolean b = manager.remove_subscriber(username);

        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("success", b);
        protocol.send(o);
    }

    public static void respond_complaints(MessagingProtocol protocol, JSONObject request) throws Exception{

    }

    public static void view_history_store(MessagingProtocol protocol, JSONObject request) throws Exception{
        String store=request.getString("store_name");

        List<JSONObject> l = manager.view_history_store(store);
        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));

        JSONArray arr = new JSONArray();
        for(JSONObject j : l){
            arr.put(j);
        }
        o.put("history", arr);

        protocol.send(o);
    }

    public static void view_history_costumer(MessagingProtocol protocol, JSONObject request) throws Exception{
        String store=request.getString("user_name");
        System.out.println(store);
        List<JSONObject> history = manager.view_history_costumer(store);
        JSONObject o=new JSONObject();

        JSONArray arr = new JSONArray();
        for(JSONObject h : history){
            arr.put(h);
        }

        o.put("req", request.get("req"));
        o.put("history", arr);
        protocol.send(o);
    }

    public static void watch_system_log(MessagingProtocol protocol, JSONObject request) throws Exception{

    }

    public static void today_revenue(MessagingProtocol protocol, JSONObject request) throws Exception{
        String s =manager.today_revenue();
        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("today_rev", s);
        protocol.send(o);
    }

    public static void date_revenue(MessagingProtocol protocol, JSONObject request) throws Exception{
        String date = request.getString("date");
        String s =manager.today_revenue();
        JSONObject o=new JSONObject();
        o.put("req", request.get("req"));
        o.put("date_rev", s);
        protocol.send(o);
    }


}
