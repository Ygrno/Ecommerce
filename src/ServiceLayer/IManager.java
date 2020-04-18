package ServiceLayer;

public interface IManager {

    public boolean close_store_permanently(String store_name); //6.1
    public boolean remove_subscriber(String user_name); //6.2
    public boolean respond_complaints (); //6.3
    public boolean view_history_store(String store_name);      //6.4
    public boolean view_history_costumer(String user_name);    //6.4
    public boolean watch_system_log(); //6.5




}
