package ServiceLayer;

public interface ISubscriber {

    public boolean view_products_information_store (String store_name); //2.4
    public boolean search_products(String product_name);  //2.5 (Right now, search via name only).

    public boolean save_products(String product_name, String store_name); //2.6
    public boolean watch_products_in_cart();  //2.7.1
    //remove_product_from cart                //2.7.2
    public boolean buy_products_in_cart();   //2.8 //7

    public boolean sign_out(String user_name);  //3.1
    public boolean open_store(String user_name, String store_name); //3.2
    public boolean write_review(String user_name, String product_name, String store_name, String review_data, int rank) ; //3.3
    public boolean rank_product(); //3.4
    public boolean rank_store();   //3.4
    public boolean send_query_to_store(); //3.5
    public boolean fill_complaint();      //3.6
    public boolean view_purchase_history();  //3.7
    public boolean edit_account();     //3.8


}
