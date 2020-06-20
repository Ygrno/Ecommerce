package Logs;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogErrors {
    private static LogErrors log_error_instance = null;
    public Logger logger;
    FileHandler fh;

    private LogErrors(String file_name) {
        try {
            File f = new File(file_name);
            if (!f.exists()) f.createNewFile();
            fh = new FileHandler(file_name, true);
            logger = Logger.getLogger("log1");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        }catch (Exception e){
            System.out.println("Log can't be initialized");
        }
    }

    public static LogErrors getLogger()  {
        if(log_error_instance == null) log_error_instance = new LogErrors("log_errors.txt");
        return log_error_instance;
    }
}
