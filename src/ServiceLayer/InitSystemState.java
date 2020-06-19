package ServiceLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class InitSystemState {

    private static final SubscriberImp subscriberImp = new SubscriberImp();
    private static final GuestImp guestImp = new GuestImp();
    private static final StoreRoleImp storeRoleImp = new StoreRoleImp();


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
                    return guestImp.sign_up(method_args[0],method_args[1]);
                case "login":
                    return guestImp.login(method_args[0],method_args[1]);
                case "openstore":
                    return subscriberImp.open_store(method_args[0],method_args[1]);
                case "add_product_to_store":
                    return storeRoleImp.add_store_product(method_args[0],method_args[1],method_args[2],Integer.parseInt(method_args[3]),Integer.parseInt(method_args[4]));
                case "add_owner_to_store":
                   return storeRoleImp.assign_store_owner(method_args[0],method_args[1],method_args[2]);
                case "add_manager_to_store":
                    return storeRoleImp.assign_store_manager(method_args[0],method_args[1],method_args[2]);
                case "create_store_simple_policy":
                    return storeRoleImp.create_store_simple_buyPolicy(method_args[0],method_args[1],Integer.parseInt(method_args[2]), Integer.parseInt(method_args[3]),method_args[4],Integer.parseInt(method_args[5]),Integer.parseInt(method_args[6]), Integer.parseInt(method_args[7]), Integer.parseInt(method_args[8]), Integer.parseInt(method_args[9]), Integer.parseInt(method_args[10]), Integer.parseInt(method_args[11]));
                case "saveProductForGuest":
                    return guestImp.save_products(Integer.parseInt(method_args[0]),method_args[1],method_args[2], Integer.parseInt(method_args[3]));
                case "saveProductForSubscriber":
                    return subscriberImp.save_products(method_args[0],method_args[1],method_args[2], Integer.parseInt(method_args[3]));
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
