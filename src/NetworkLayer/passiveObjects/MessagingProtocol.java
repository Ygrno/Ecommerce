package NetworkLayer.passiveObjects;
import DomainLayer.User.Guest;
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


            if(req.equals("login")){
                GuestMessageProccess.Login(this, request);
            }else if(req.equals("get_stores")){
                GuestMessageProccess.getStores(this, request);
            }else if(req.equals("get_products")){
                GuestMessageProccess.searchProductStores(this, request);
            }else if(req.equals("get_store")){
                GuestMessageProccess.getStore(this, request);
            }else if(req.equals("get_product_of_store")){
                GuestMessageProccess.searchProductsOfStore(this, request);
            }else if(req.equals("signup")){
                GuestMessageProccess.Signup(this, request);
            }else if(req.equals("continue_as_a_guest")) {
                GuestMessageProccess.continueAsAguest(this, request);
            }else if(req.indexOf("save_product_for") >= 0){
                if(req.equals("save_product_for_guest")) {
                    GuestMessageProccess.saveProductForGuest(this, request);
                }else if(req.equals("save_product_for_subscriber")){
                    SubscriberMessageProccess.saveProductForSubscriber(this, request);
                }
            }else if(req.indexOf("view_cart") >= 0){
                if(req.equals("view_cart_guest")) {
                    GuestMessageProccess.viewCart(this, request);
                }else if(req.equals("view_cart_subscriber")){
                    SubscriberMessageProccess.viewCart(this, request);
                }
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
