package integration.tests;

import DomainLayer.ExternalSerivce.PassiveObjects.ExternalSupplyService;
import DomainLayer.ExternalSerivce.ProductSupplyService;
import DomainLayer.OrderDetails;
import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductSupplyServiceTest {

    private static ProductSupplyService productSupplyService;

    @BeforeClass
    public static void setUp() throws Exception {
        productSupplyService = new ProductSupplyService(new ExternalSupplyService() {
            @Override
            public boolean connect() throws Exception {
                return true;
            }

            @Override
            public boolean order(OrderDetails orderDetails) {
                return true;
            }
        });
    }

    @Test
    public void connect() {
        assert productSupplyService.connect();
    }

    @Test
    public void order() {
        assertEquals(true, productSupplyService.order(new OrderDetails()));
    }
}