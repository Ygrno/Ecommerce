package ServiceLayer;

import DAL.DBAccess;
import DomainLayer.InternalService.SystemManage_Facade;
import Encryption.EncryptProxy;
import ExternalService.ExternalSupplyService;
import ExternalService.Mockups.ExternalFinanceServiceMock;
import ExternalService.Mockups.ExternalSupplyServiceMock;
import Logs.LogErrors;
import Logs.LogInfo;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.util.List;


public class AdminImp implements IAdmin {

    LogErrors my_logError = LogErrors.getLogger();
    LogInfo my_logInfo = LogInfo.getLogger();
    private static DBAccess db;

    @Override
    public boolean init_system(boolean file) throws Exception {

        my_logInfo.logger.info("Init System");
        //Init System Flow:
        //---------------------------
        // init DB
        db= DBAccess.getInstance();
        //Initialize System.
        SystemManage_Facade.init_system();


        //Sign-Up Admin
        GuestImp guestImp = new GuestImp();
        if(!guestImp.sign_up("A1", "Password")) return false;

        //True = Read File, False = Don't read file
        if(file) {
            if (!InitSystemState.init()) {
                java.lang.System.out.println("failed to init system");
            }
        }

        //Connection to external Finance Service:
        ExternalFinanceServiceMock externalFinanceServiceMock = new ExternalFinanceServiceMock();
        try {
            externalFinanceServiceMock.connect();
        } catch (Exception e) {
            my_logError.logger.severe("Connection to externalFinanceService Failed!");
            return false;
        }
        //Connection to external supply System:
        ExternalSupplyService externalSupplyServiceMock = new ExternalSupplyServiceMock();
        try {
            externalSupplyServiceMock.connect();
        } catch (Exception e) {
            my_logError.logger.severe("Connection to ExternalSupplyService Failed!");
            return false;
        }
        //Providing
        SystemManage_Facade.setFinanceSystem(externalFinanceServiceMock);
        SystemManage_Facade.setSupplySystem(externalSupplyServiceMock);

        //Connect Real Encryption
        EncryptProxy iEncrypt = EncryptionDriver.getEncryption();
        return iEncrypt.init();
    }

    @Override
    public boolean close_store_permanently(String store_name) {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean remove_subscriber(String user_name) {
        if(!SystemManage_Facade.is_initialized()) return false;
        return SystemManage_Facade.remove_subscriber(user_name);
    }

    @Override
    public boolean respond_complaints() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public List<JSONObject> view_history_store(String store_name) throws Exception {
        if(!SystemManage_Facade.is_initialized()) return null;
        return SystemManage_Facade.get_store_purchase_process(store_name);
    }

    @Override
    public List<JSONObject> view_history_costumer(String user_name) throws Exception{
        if(!SystemManage_Facade.is_initialized()) return  null;
        return SystemManage_Facade.get_subscriber_purchase_process(user_name);

    }

    @Override
    public boolean watch_system_log() {
        if(!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public String today_revenue() {
        if(!SystemManage_Facade.is_initialized()) return null;
        return SystemManage_Facade.today_revenue();

    }

    @Override
    public String date_revenue(String date) {
        if(!SystemManage_Facade.is_initialized()) return null;
        return SystemManage_Facade.date_revenue(date);

    }
}
