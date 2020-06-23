package ServiceLayer;

public interface IManager {

    public boolean init_system(boolean file) throws Exception; //1.1
    public boolean close_store_permanently(String store_name); //6.1
    public boolean remove_subscriber(String user_name); //6.2
    public boolean respond_complaints (); //6.3
    public String view_history_store(String store_name);      //6.4
    public String view_history_costumer(String user_name);    //6.4
    public boolean watch_system_log(); //6.5
    public String  today_revenue();// version 3 d
    public String  date_revenue(String date);// version 3 d




}
