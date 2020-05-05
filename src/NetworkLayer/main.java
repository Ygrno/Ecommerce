package NetworkLayer;

import NetworkLayer.passiveObjects.connectionHandler;

import java.net.ServerSocket;

public class main {
    public static void main(String[] args){



        try {
            ServerSocket server = new ServerSocket(8112, 100);



            while (true){
                connectionHandler client = new connectionHandler(server.accept());
                new Thread(()->{
                    try {
                        System.out.println("Serving client");
                        client.serve();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }).start();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

