package DomainLayer.InternalService;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class InitSystemState {


    public static boolean init(){
        //start the database
        boolean flag = true;

        try {
           String path = Paths.get("").toAbsolutePath().toString();
           path+="\\src\\init_file.txt";
            File file = new File(path);
            Scanner myReader = new Scanner(file);

            while (flag && myReader.hasNextLine()) {
                String data_line = myReader.nextLine();
                String[] method_args = new String[12];
                int Parenthes = data_line.indexOf('(');
                int Parenthesclose = data_line.indexOf(')');
                String method_name = data_line.substring(0,Parenthes);
                String arguments = data_line.substring(Parenthes+1,Parenthesclose);
                method_args = arguments.split(",");

                if(!call_method(method_name, method_args)) {
                    flag = false;
                }
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("init file is missing");
        }
        return flag;
    }



    private static boolean call_method(String method_name, String[] method_args) {
        try {
            switch (method_name) {
                case "signup":
                    return SubscribersManage_Facade.signup(method_args[0],method_args[1]);
                case "login":
                    return SubscribersManage_Facade.login(method_args[0],method_args[1]);
                case "openstore":
                    return SubscribersManage_Facade.openstore(method_args[0],method_args[1]);
                case "add_product_to_store":
                    return SubscribersManage_Facade.add_product_to_store(method_args[0],method_args[1],method_args[2],Integer.valueOf(method_args[3]),Integer.valueOf(method_args[4]));
                case "add_owner_to_store":
                   return SubscribersManage_Facade.add_owner_to_store(method_args[0],method_args[1],method_args[2]);
                case "add_manager_to_store":
                    return SubscribersManage_Facade.add_manager_to_store(method_args[0],method_args[1],method_args[2]);
                case "create_store_simple_policy":
                    return SubscribersManage_Facade.create_store_simple_buyPolicy(method_args[0],method_args[1],Integer.valueOf(method_args[2]), Integer.valueOf(method_args[3]),method_args[4],Integer.valueOf(method_args[5]),Integer.valueOf(method_args[6]), Integer.valueOf(method_args[7]), Integer.valueOf(method_args[8]), Integer.valueOf(method_args[9]), Integer.valueOf(method_args[10]), Integer.valueOf(method_args[11]));
                case "saveProductForGuest":
                    return SystemManage_Facade.saveProductForGuest(Integer.valueOf(method_args[0]),method_args[1],method_args[2], Integer.valueOf(method_args[3]));
                case "saveProductForSubscriber":
                    return SystemManage_Facade.saveProductForSubscriber(method_args[0],method_args[1],method_args[2], Integer.valueOf(method_args[3]));
                case "add_subscriber":
                    return SystemManage_Facade.add_subscriber(method_args[0],method_args[1]);
                default:
                    return false;
            }
        }catch(Exception e){
            System.out.println("invalid init file");
            e.printStackTrace();
            return false;
        }
    }


}
