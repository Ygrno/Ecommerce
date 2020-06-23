package NetworkLayer;

import DAL.DBAccess;
import NetworkLayer.passiveObjects.connectionHandler;
import ServiceLayer.AdminImp;

import java.net.ServerSocket;

public class main {
    private static DBAccess db;
    public static void main(String[] args){
        //start the database
        //db=DBAccess.getInstance();
        AdminImp managerImp = new AdminImp();
        managerImp.init_system(true);

        //Esimulation.Init();
        try {
            ServerSocket server = new ServerSocket(8112, 200);

            while (true){
                connectionHandler client = new connectionHandler(server.accept());
                new Thread(()->{
                    try {
                        System.out.println("Serving client");
                        client.serve();
                    }catch (Exception e){

                    }
                }).start();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
