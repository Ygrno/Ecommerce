package integration.tests;

import ExternalService.ExternalSupplyService;
import ExternalService.ProductSupplyServiceAdapter;
import DomainLayer.OrderDetails;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductSupplyServiceTest {

    private static ProductSupplyServiceAdapter productSupplyService;

    @BeforeClass
    public static void setUp() throws Exception {
        productSupplyService = new ProductSupplyServiceAdapter();

    }

    @Test
    public void connect() throws Exception {
        assert productSupplyService.connect();
    }

    @Test
    public void order() {
        //assertEquals(true, productSupplyService.order(new OrderDetails()));
    }
}