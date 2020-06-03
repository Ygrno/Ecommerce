package NetworkLayer.passiveObjects;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import org.json.JSONObject;


public class MessagingProtocol {

    private connectionHandler connectionHandler;

    public MessagingProtocol(connectionHandler connectionHandler){
        this.connectionHandler = connectionHandler;
    }


    /**
     * process client incoming requests
     *
     * {@param msg} would be a JSON string in the following format
     *      { req:string, ...args:any }
     *
     * - req will define which action the
     *  client is requesting
     *
     * - args will hold the action arguments
     * **/
    public void proccess(String msg){
        //TODO: implement msg protocol
        try {
            JSONObject request = new JSONObject(msg);
            String req = (String) request.get("req");

            if(req.equals("login")){   //done
                GuestMessageProccess.Login(this, request);
            }else if(req.equals("get_stores")){  //done
                GuestMessageProccess.getStores(this, request);
            }else if(req.equals("get_products")){ //done
                GuestMessageProccess.searchProductStores(this, request);
            }else if(req.equals("get_store")){  //done
                GuestMessageProccess.getStore(this, request);
            }else if(req.equals("get_product_of_store")){  //done
                GuestMessageProccess.searchProductsOfStore(this, request);
            }else if(req.equals("signup")){
                GuestMessageProccess.Signup(this, request);
            }else if(req.equals("continue_as_a_guest")) {
                GuestMessageProccess.continueAsAguest(this, request);
            }else if(req.contains("save_product_for")){
                if(req.equals("save_product_for_guest")) {
                    GuestMessageProccess.saveProductForGuest(this, request);
                }else if(req.equals("save_product_for_subscriber")){
                    SubscriberMessageProccess.saveProductForSubscriber(this, request);
                }
            }else if(req.contains("view_cart")){
                if(req.equals("view_cart_guest")) {
                    GuestMessageProccess.viewCart(this, request);
                }else if(req.equals("view_cart_subscriber")){
                    SubscriberMessageProccess.viewCart(this, request);
                }
            }else if(req.contains("buy_cart")){
                if(req.equals("buy_cart_guest")) {
                    GuestMessageProccess.buyCart(this, request);
                }else if(req.equals("buy_cart_subscriber")){
                    SubscriberMessageProccess.buyCart(this, request);
                }
            }else if(req.equals("sign_out")){   //done
                SubscriberMessageProccess.signOut(this,request);
            }else if(req.equals("open_store")){ //done
                SubscriberMessageProccess.open_store(this,request);
            }else if(req.equals("write_review")){  //done
                SubscriberMessageProccess.writeReview(this,request);
            }else if(req.equals("send_query_to_store")){  //done
                SubscriberMessageProccess.send_query_to_store(this,request);
            }else if(req.equals("view_purchase_history")){  //done
                SubscriberMessageProccess.view_purchase_history(this,request);
            }else if(req.equals("close_store_permanently")){
                ManagerMessageProccess.close_store_permanently(this,request);
            }else if(req.equals("remove_subscriber")){
                ManagerMessageProccess.remove_subscriber(this,request);
            }else if(req.equals("respond_complaints")){
                ManagerMessageProccess.respond_complaints(this,request);
            }else if(req.equals("view_history_store")){
                ManagerMessageProccess.view_history_store(this,request);
            }else if(req.equals("view_history_costumer")){
                ManagerMessageProccess.view_history_costumer(this,request);
            }else if(req.equals("watch_system_log")){
                ManagerMessageProccess.watch_system_log(this,request);
            }else if(req.equals("add_store_product")){   //done
                StoreRoleMessageProccess.add_store_product(this,request);
            }else if(req.equals("edit_store_product")){ //done
                StoreRoleMessageProccess.edit_store_product(this,request);
            }else if(req.equals("remove_store_product")){  //done
                StoreRoleMessageProccess.remove_store_product(this,request);
            }else if(req.equals("add_store_visible_discount")){
                StoreRoleMessageProccess.add_store_visible_discount(this,request);
            }else if(req.equals("assign_store_owner")){   // done
                StoreRoleMessageProccess.assign_store_owner(this,request);
            }else if(req.equals("remove_store_owner")){     // done
                StoreRoleMessageProccess.remove_store_owner(this,request);
            }else if(req.equals("assign_store_manager")){  //done
                StoreRoleMessageProccess.assign_store_manager(this,request);
            }else if(req.equals("edit_manager_permissions")){
                StoreRoleMessageProccess.edit_manager_permissions(this,request);
            }else if(req.equals("remove_store_manager")){  //done
                StoreRoleMessageProccess.remove_store_manager(this,request);
            }else if(req.equals("close_store")){   //done
                StoreRoleMessageProccess.close_store(this,request);
            }else if(req.equals("view_and_respond_to_questions")){
                StoreRoleMessageProccess.view_and_respond_to_questions(this,request);
            }else if(req.equals("watch_store_history")){  //done
                StoreRoleMessageProccess.watch_store_history(this,request);
            }

        }catch (Exception e){
            System.out.println("Can't process " + msg);
            e.printStackTrace();
        }
    }


    public void send(JSONObject msg){
        send(msg.toString());
    }

    public void send(String msg){
        connectionHandler.send(msg);
    }

}
