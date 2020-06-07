package NetworkLayer;

import DomainLayer.InternalService.InitSystemState;
import DomainLayer.InternalService.SystemManage_Facade;
import NetworkLayer.passiveObjects.connectionHandler;
import Stubs.Esimulation;

import java.net.ServerSocket;

public class main {

    public static void main(String[] args){
        //start the database

        if(!SystemManage_Facade.is_initialized()) {
            SystemManage_Facade.init_system();
            if (!InitSystemState.init()) {
                System.out.println("failed to init system");
            }
        }

        Esimulation.Init();
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
