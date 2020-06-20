package acceptance;

import DomainLayer.DealDetails;
import ExternalService.ExternalFinanceService;
import ExternalService.ProductFinanceServiceAdapter;
import DomainLayer.InternalService.SystemManage_Facade;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class uc_7_connect_finance_service {


    private static ProductFinanceServiceAdapter productFinanceService;

    @BeforeClass
    public static void setUp() throws Exception {
        SystemManage_Facade.init_system();
        productFinanceService = new ProductFinanceServiceAdapter();

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
