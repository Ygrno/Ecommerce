package ServiceLayer;

public interface IGuest {

    public boolean sign_up(String user_name, String password);
    public boolean login(String user_name, String password);
    public boolean search_products(String product_name);
    public boolean save_products(String product_name);
    public boolean watch_products_in_cart();
    public boolean buy_products_in_cart();

}
