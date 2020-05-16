package NetworkLayer.passiveObjects;

import DomainLayer.InternalService.SubscribersManage_Facade;
import DomainLayer.InternalService.SystemManage_Facade;
import DomainLayer.Store.Store;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageProccess {

    public static void Login(MessagingProtocol protocol, JSONObject request) throws Exception{
        JSONObject response = new JSONObject();
        response.put("req", request.get("req"));
        if(SubscribersManage_Facade.login((String)request.get("username"),(String)request.get("password")))
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

}
