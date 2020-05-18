package ServiceLayer;

import java.util.HashMap;
import java.util.List;

public interface IGuest {

    //1.1 is done with login.
    //2.1 is done with GUI

    public boolean sign_up(String user_name, String password); //2.2
    public boolean login(String user_name, String password);   //2.3
    public String[][] view_products_information_store (String store_name); //2.4

    public HashMap<String, Double> search_products(String product_name);  //2.5 (Right now, search via name only).

    public boolean save_products(int id,String product_name, String store_name); //2.6
    public List<String> watch_products_in_cart(int id);  //2.7.1
    //remove_product_from cart                //2.7.2
    public boolean buy_products_in_cart(int id,String buyerName,String creditCardNumber,String expireDate,int cvv,double discount);   //2.8 //7

}
