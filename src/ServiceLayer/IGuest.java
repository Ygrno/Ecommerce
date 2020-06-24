package ServiceLayer;

import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

public interface IGuest {

    //1.1 is done with login.
    //2.1 is done with GUI

    boolean sign_up(String user_name, String password) throws Exception; //2.2
    boolean login(String user_name, String password) throws Exception;   //2.3
    String[][] view_products_information_store (String store_name); //2.4
    List<JSONObject> getAllStores() throws JSONException;
    HashMap<String, Double> search_products(String product_name);  //2.5 (Right now, search via name only).
    int addGuest();
    boolean save_products(int id,String product_name, String store_name,int amount); //2.6
    List<JSONObject> watch_products_in_cart(int id) throws JSONException;  //2.7.1
    boolean remove_product_from_cart(int id,String product_name,String store_name);                //2.7.2
    public boolean buy_products_in_cart(int id,String buyerName,String creditCardNumber,String expireDate,int cvv, String buyer_id) throws Exception;   //2.8 //7
    public double getTotalPriceOfCart(String userName);
}
