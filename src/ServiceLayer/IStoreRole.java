package ServiceLayer;



import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface IStoreRole {

    boolean add_store_product(String user_name, String store_name, String product_name, double product_price, int product_amount) throws Exception; //4.1 //5.1
    boolean edit_store_product(String user_name, String store_name, String product_name, String new_product_name, int product_price, int product_amount) throws Exception; //4.1 //5.1
    boolean remove_store_product(String user_name, String store_name, String product_name) throws Exception;  //4.1 //5.1


    boolean add_visible_discount(String user_name, String store_name, String discount_name, double discount_percentage, int end_of_use_date, String product_name) throws Exception;//4.2
    boolean add_conditioned_discount(String user_name, String store_name, String product_name, String discount_name, double discount_percentage, int due_date, int amount, int sum) throws Exception;//4.2
    boolean add_complex_discount(String user_name, String store_name, String discount_name, String[] discounts, String type, int end_of_use_date) throws Exception;
    boolean delete_discount(String user_name, String store_name, String discount_name) throws Exception;
    List<JSONObject> view_all_discount(String user_name, String store_name) throws Exception;
//
//    public boolean remove_store_discount_policy(String user_name, String store_name, String discount_name);    //4.2 //5.1
//
//    public boolean add_store_buying_policy(String user_name, String store_name);    //4.2 //5.1
//    public boolean remove_store_buying_policy(String user_name, String store_name);    //4.2 //5.1

    boolean assign_store_owner_approval(String store_name, String user_assign) throws Exception; //4.3
    boolean assign_store_owner(String user_name,String store_name, String user_assign) throws Exception; //4.3
    boolean remove_store_owner(String user_name, String store_name, String user_assign) throws Exception; //4.4
    boolean assign_store_manager(String user_name, String store_name, String user_assign) throws Exception; //4.5
    boolean edit_manager_permissions(String user_name, String store_name, String user_assign, ArrayList<String> permissions) throws Exception; //4.6
    List<JSONObject> get_user_permissions(String username, String store) throws Exception;


    boolean remove_store_manager(String user_name, String store_name, String user_assign) throws Exception; //4.7
    boolean close_store(String user_name, String store_name); //4.8
    boolean view_and_respond_to_questions(); //4.9 //5.1
    String watch_store_history(String user_name, String store_name) throws Exception; //4.10 //5.1
    //4.11

    boolean create_store_simple_buyPolicy(String user_name, String store_name, int policy_type, String description, int policy_id, String product_name, int minProducts, int maxProducts, int minCost, int maxCost, int min_quantity, int max_quantity, int day) throws Exception;
    boolean create_store_complex_buyPolicy(String user_name, String store_name, String description, int policy_id, int[] policy_ids, int op) throws Exception;
    boolean edit_store_simple_buyPolicy(String user_name, String store_name, int type, String description, int policy_id, String product_name, int minProducts, int maxProducts, int minCost, int maxCost, int min_quantity, int max_quantity, int day) throws Exception;
    boolean edit_store_complex_buyPolicy(String user_name, String store_name, int policy_id, int new_policy_id, String act) throws Exception;
    boolean remove_store_buyPolicy(String user_name, String store_name, int policy_id) throws Exception;
    List<JSONObject> get_policies_ids_in_store(String store_name) throws Exception;
    //need to add function for viewing exsisting policies
}
