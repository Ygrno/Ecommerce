package Observer;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Observer {
    public void update(JSONObject json) throws JSONException{
        JSONObject o = new JSONObject();
        o.put("username",json.getString("username"));
        o.put("message",json.getString("message"));
    }
}
