package acceptance;

import DomainLayer.DealDetails;
import DomainLayer.ExternalSerivce.PassiveObjects.ExternalFinanceService;
import DomainLayer.ExternalSerivce.ProductFinanceService;
import DomainLayer.InternalService.SystemManage_Facade;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class uc_7_connect_finance_service {


    private static ProductFinanceService productFinanceService;

    @BeforeClass
    public static void setUp() throws Exception {
        SystemManage_Facade.init_system();
        productFinanceService = new ProductFinanceService(new ExternalFinanceService() {
            @Override
            public boolean connect() throws Exception {
                return true;
            }

            @Override
            public boolean purchase(String accName, String ccn, String expireDate, int cvv) {
                if(accName.equals("mahmoud"))
                    return true;
                else
                    return false;
            }
        });


    }

    @Test
    public void connect() {
        assert productFinanceService.connect();
    }

    @Test
    public void tryToBuy() {
        assertEquals(true, productFinanceService.tryToBuy(new DealDetails("mahmoud",100, "mahmoud", "1291823", "21/9", 977)));
        assertEquals(false, productFinanceService.tryToBuy(new DealDetails("tamer",100, "tamer", "1291823", "21/9", 977)));
    }
}
