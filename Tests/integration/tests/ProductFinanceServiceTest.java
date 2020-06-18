package integration.tests;

import DomainLayer.DealDetails;
import DomainLayer.ExternalSerivce.PassiveObjects.ExternalFinanceService;
import DomainLayer.ExternalSerivce.ProductFinanceService;
import DomainLayer.InternalService.SystemManage_Facade;
import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductFinanceServiceTest {


    private static ProductFinanceService productFinanceService;

    @BeforeClass
    public static void setUp() throws Exception {
        SystemManage_Facade.init_system(false);
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
        assertEquals(true, productFinanceService.tryToBuy(new DealDetails("m",100, "mahmoud", "1291823", "21/9", 977)));
        assertEquals(false, productFinanceService.tryToBuy(new DealDetails("t",100, "tamer", "1291823", "21/9", 977)));
    }
}