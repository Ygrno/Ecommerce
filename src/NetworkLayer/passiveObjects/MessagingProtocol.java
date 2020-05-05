package NetworkLayer.passiveObjects;

public class MessagingProtocol {

    private connectionHandler connectionHandler;

    public MessagingProtocol(connectionHandler connectionHandler){
        this.connectionHandler = connectionHandler;
    }


    public void proccess(String msg){
        //TODO: implement msg protocol
        System.out.println(msg + " <<<<<<<<<<<");
    }


    public void send(String msg){
        connectionHandler.send(msg);
    }

}
