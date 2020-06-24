package ServiceLayer;

import DomainLayer.PurchaseProcess;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public interface ISubscriber {

    String[][] view_products_information_store(String store_name); //2.4
    HashMap<String, Double> search_products(String product_name);  //2.5 (Right now, search via name only).

    boolean save_products(String userName, String product_name, String store_name, int amount); //2.6
    List<JSONObject> watch_products_in_cart(String userName) throws JSONException;  //2.7.1
    boolean remove_product_from_cart(String id,String product_name,String store_name);                 //2.7.2
    public boolean buy_products_in_cart(String id,String buyerName,String creditCardNumber,String expireDate,int cvv, String buyer_id) throws Exception;   //2.8 //7

    boolean sign_out(String user_name) throws Exception;  //3.1
    boolean open_store(String user_name, String store_name) throws Exception; //3.2
    boolean write_review(String user_name, String product_name, String store_name, String review_data, int rank) throws Exception; //3.3
    boolean rank_product(); //3.4
    boolean rank_store();   //3.4
    boolean send_query_to_store(String user_name, String Query) throws Exception;//3.5
    boolean fill_complaint();      //3.6
    List<JSONObject> view_purchase_history(String user_name) throws Exception;  //3.7    //changed the return boolean type to List<PurchaseProcess> and the input from none to string user_name
    boolean edit_account();     //3.8

    double getTotalPriceOfCart(String userName);
    String view_purchase_history_string(String user_name) throws Exception;
    List<JSONObject> getNotifications(String userName) throws Exception;

}
