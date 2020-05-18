package NetworkLayer.passiveObjects;
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
                MessageProccess.Login(this, request);
            }else if(req.equals("get_stores")){
                MessageProccess.getStores(this, request);
            }else if(req.equals("get_products")){
                MessageProccess.searchProductStores(this, request);
            }else if(req.equals("get_store")){
                MessageProccess.getStore(this, request);
            }else if(req.equals("get_product_of_store")){
                MessageProccess.searchProductsOfStore(this, request);
            }else if(req.equals("signup")){
                MessageProccess.Signup(this, request);
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
