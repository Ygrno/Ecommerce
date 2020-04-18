package ServiceLayer;

import java.util.ArrayList;

public interface IStoreRole {

    public boolean add_store_product(String user_name, String store_name, String product_name, int product_price, int product_amount); //4.1
    public boolean edit_store_product(String user_name, String store_name, String product_name, int product_price, int product_amount);
    public boolean remove_store_product(String user_name, String store_name);


    public boolean edit_store_policy(String user_name, String store_name);    //4.2
    public boolean assign_store_owner(String user_name, String store_name, String user_assign); //4.3
    public boolean remove_store_owner(String user_name, String store_name, String user_assign); //4.4
    public boolean assign_store_manager(String user_name, String store_name, String user_assign); //4.5
    public boolean edit_manager_permissions(String user_name, String store_name, ArrayList<String> permissions); //4.6
    public boolean remove_store_manager(String user_name, String store_name, String user_assign); //4.7
    public boolean close_store(String user_name, String store_name); //4.8
    public boolean view_and_respond_to_questions(); //4.9
    public boolean watch_store_history(String user_name, String store_name); //4.10

}
