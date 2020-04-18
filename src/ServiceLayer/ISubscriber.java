package ServiceLayer;

public interface ISubscriber {

    public boolean sign_out(String user_name);  //3.1
    public boolean open_store(String user_name, String store_name); //3.2
    public boolean write_review(); //3.3
    public boolean rank_product(); //3.4
    public boolean rank_store();   //3.4
    public boolean send_query_to_store(); //3.5
    public boolean fill_complaint();      //3.6
    public boolean view_purchase_history();  //3.7
    public boolean edit_account();     //3.8


}
