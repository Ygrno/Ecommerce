package NetworkLayer;

import DAL.DBAccess;
import NetworkLayer.passiveObjects.connectionHandler;
import Stubs.Esimulation;
import ServiceLayer.ManagerImp;

import java.net.ServerSocket;

public class main {
    private static DBAccess db;
    public static void main(String[] args){
        //start the database
        db=DBAccess.getInstance();
        ManagerImp managerImp = new ManagerImp();
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
