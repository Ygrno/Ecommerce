package NetworkLayer.passiveObjects;
import org.json.JSONObject;


public class MessagingProtocol {

    private connectionHandler connectionHandler;

    public MessagingProtocol(connectionHandler connectionHandler){
        this.connectionHandler = connectionHandler;
    }


    public void proccess(String msg){
        //TODO: implement msg protocol
        try {

            JSONObject request = new JSONObject(msg);
            String req = (String) request.get("req");


            if(req.equals("login")){
                MessageProccess.Login(this, request);
            }else if(req.equals("get_stores")){
                MessageProccess.getStores(this, request);
            }
        }catch (Exception e){
            System.out.println("Can't process " + msg);
            e.printStackTrace();
        }
    }


    public void send(JSONObject msg){
        connectionHandler.send(msg.toString());
    }

    public void send(String msg){
        connectionHandler.send(msg);
    }

}
