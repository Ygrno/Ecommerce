package Unit.tests;

import DAL.DBAccess;
import ServiceLayer.GuestImp;
import ServiceLayer.AdminImp;
import ServiceLayer.StoreRoleImp;
import ServiceLayer.SubscriberImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DBTest {
    private static DBAccess dbAccess;
    private static GuestImp guestImp;
    private static AdminImp managerImp;
    private static SubscriberImp subscriberImp;
    private static StoreRoleImp storeRoleImp;

    @BeforeClass
    public static void setUp() throws Exception {
        dbAccess=DBAccess.getInstance();
        guestImp = new GuestImp();
        managerImp = new AdminImp();
        managerImp.init_system(false);
        subscriberImp = new SubscriberImp();
        storeRoleImp = new StoreRoleImp();
    }

    public String lastError() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("log_errors.txt"));
        String sCurrentLine;
        String lastLine = "";

        while ((sCurrentLine = br.readLine()) != null)
        {
            lastLine = sCurrentLine;
        }
        return lastLine;
    }

    @Test
    public void testCase() throws Exception {
         assert  guestImp.sign_up("mhmod","123");
         assert  subscriberImp.open_store("mhmod","x");
         assert  subscriberImp.open_store("mhmod","x1");
         assert  storeRoleImp.add_store_product("mhmod","x","pr",12,12);
         assert  guestImp.sign_up("tamer","123");
         assert  storeRoleImp.assign_store_manager("mhmod","x","tamer");
        if(dbAccess.closeSession()) {
            assert !guestImp.sign_up("asd", "123");
            assert lastError().contains("Failed To Connect To The DataBase!");
            assert !subscriberImp.open_store("mhmod","x2");
            assert lastError().contains("Failed To Connect To The DataBase!");
            assert !storeRoleImp.add_store_product("mhmod","x","pr1",12,12);
            assert lastError().contains("Failed To Connect To The DataBase!");
            assert !guestImp.sign_up("ahmad","123");
            assert lastError().contains("Failed To Connect To The DataBase!");
            assert !storeRoleImp.assign_store_manager("mhmod","x1","tamer");
            assert lastError().contains("Failed To Connect To The DataBase!");

        }
    }

}
