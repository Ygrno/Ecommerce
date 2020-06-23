package NetworkLayer.passiveObjects;

import ServiceLayer.IAdmin;
import ServiceLayer.AdminImp;
import org.json.JSONObject;

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

    }

    public static void view_history_costumer(MessagingProtocol protocol, JSONObject request) throws Exception{

    }

    public static void watch_system_log(MessagingProtocol protocol, JSONObject request) throws Exception{

    }
}
