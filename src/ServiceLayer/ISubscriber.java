package ServiceLayer;

public interface ISubscriber {

    public boolean sign_out(String user_name);
    public boolean open_store(String user_name, String store_name);
    public boolean write_review();
    public boolean rank_product();
    public boolean rank_store();
    public boolean send_query_to_store();
    public boolean fill_complaint();
    public boolean view_purchase_history();
    public boolean edit_account();


}
