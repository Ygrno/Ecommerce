package ServiceLayer;

import DomainLayer.PurchaseProcess;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public interface ISubscriber {

    public String[][] view_products_information_store (String store_name); //2.4
    public HashMap<String, Double> search_products(String product_name);  //2.5 (Right now, search via name only).

    public boolean save_products(String userName,String product_name, String store_name,int amount); //2.6
    public List<JSONObject> watch_products_in_cart(String userName) throws JSONException;  //2.7.1
    boolean remove_product_from_cart(String id,String product_name,String store_name);                 //2.7.2
    public boolean buy_products_in_cart(String id,String buyerName,String creditCardNumber,String expireDate,int cvv) throws Exception;   //2.8 //7

    public boolean sign_out(String user_name);  //3.1
    public boolean open_store(String user_name, String store_name); //3.2
    public boolean write_review(String user_name, String product_name, String store_name, String review_data, int rank); //3.3
    public boolean rank_product(); //3.4
    public boolean rank_store();   //3.4
    public boolean send_query_to_store(String user_name,String Query);//3.5
    public boolean fill_complaint();      //3.6
    public List<JSONObject> view_purchase_history(String user_name) throws Exception;  //3.7    //changed the return boolean type to List<PurchaseProcess> and the input from none to string user_name
    public boolean edit_account();     //3.8

    public double getTotalPriceOfCart(String userName);
    public List<JSONObject> getNotifications(String userName);
    public String view_purchase_history_string(String user_name);

}
