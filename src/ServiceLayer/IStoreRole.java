package ServiceLayer;

import DomainLayer.PurchaseProcess;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface IStoreRole {

    public boolean add_store_product(String user_name, String store_name, String product_name, int product_price, int product_amount); //4.1 //5.1
    public boolean edit_store_product(String user_name, String store_name, String product_name,String new_product_name ,int product_price, int product_amount); //4.1 //5.1
    public boolean remove_store_product(String user_name, String store_name, String product_name);  //4.1 //5.1


      public boolean add_store_visible_discount (String user_name, String store_name,String product_name,String discount_name, double discount_percentage, int due_date);    //4.2 //5.1
//    public boolean remove_store_discount_policy(String user_name, String store_name, String discount_name);    //4.2 //5.1
//
//    public boolean add_store_buying_policy(String user_name, String store_name);    //4.2 //5.1
//    public boolean remove_store_buying_policy(String user_name, String store_name);    //4.2 //5.1


    public boolean assign_store_owner(String user_name, String store_name, String user_assign); //4.3
    public boolean remove_store_owner(String user_name, String store_name, String user_assign); //4.4
    public boolean assign_store_manager(String user_name, String store_name, String user_assign); //4.5
    public boolean edit_manager_permissions(String user_name, String store_name,String user_assign , ArrayList<String> permissions); //4.6
    public List<JSONObject> get_permissions(String user_name);


    public boolean remove_store_manager(String user_name, String store_name, String user_assign); //4.7
    public boolean close_store(String user_name, String store_name); //4.8
    public boolean view_and_respond_to_questions(); //4.9 //5.1
    public String watch_store_history(String user_name, String store_name); //4.10 //5.1
    //4.11


}
