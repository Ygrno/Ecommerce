package Logs;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogInfo {
    private static LogInfo log_Info_instance = null;
    public Logger logger;
    FileHandler fh;

    private LogInfo(String file_name) {
        try {
            File f = new File(file_name);
            if (!f.exists()) f.createNewFile();
            fh = new FileHandler(file_name, true);
            logger = Logger.getLogger("test");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        }catch (Exception e){
            System.out.println("Log can't be initialized");
        }
    }

    public static LogInfo getLogger()  {
        if(log_Info_instance == null) log_Info_instance = new LogInfo("log_info.txt");
        return log_Info_instance;
    }

}
