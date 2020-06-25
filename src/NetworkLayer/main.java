package NetworkLayer;

import DAL.DBAccess;
import NetworkLayer.passiveObjects.connectionHandler;
import ServiceLayer.AdminImp;

import java.net.ServerSocket;

public class main {
    private static DBAccess db;
    public static void main(String[] args) throws Exception {
        //start the database
        //db=DBAccess.getInstance();https://docs.microsoft.com/en-us/archive/msdn-magazine/2014/november/async-programming-unit-testing-asynchronous-code-three-solutions-for-better-tests
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
