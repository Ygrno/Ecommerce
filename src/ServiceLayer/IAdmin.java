package ServiceLayer;
import org.json.JSONObject;

import java.util.List;
import org.json.JSONObject;


public interface IAdmin {

    boolean init_system(boolean file) throws Exception; //1.1
    boolean close_store_permanently(String store_name); //6.1
    boolean remove_subscriber(String user_name); //6.2
    boolean respond_complaints(); //6.3
    List<JSONObject> view_history_store(String store_name) throws Exception;      //6.4
    List<JSONObject> view_history_costumer(String user_name) throws Exception;    //6.4
    boolean watch_system_log(); //6.5
    String  today_revenue();// version 3 d
    String  date_revenue(String date);// version 3 d


}
