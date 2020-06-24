package Unit.tests.ExternalServices;

import DomainLayer.DealDetails;
import ExternalService.ExternalSupplyService;
import ExternalService.Mockups.ExternalSupplyServiceMock;
import ExternalService.ProductSupplyServiceAdapter;
import DomainLayer.OrderDetails;
import ServiceLayer.AdminImp;
import ServiceLayer.GuestImp;
import Unit.tests.ExternalServices.Stubs.FailingFInanceServiceStub;
import Unit.tests.ExternalServices.Stubs.FailingSupplyServiceStub;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductSupplyServiceTest {

    private static ProductSupplyServiceAdapter productSupplyService;
    private static AdminImp managerImp;
    private static GuestImp guestImp;


    @BeforeClass
    public static void setUp() throws Exception {
        productSupplyService = new ProductSupplyServiceAdapter();
        ExternalSupplyService service = new ExternalSupplyServiceMock();

        productSupplyService.setSupplyService(service);

        //init resources
        managerImp = new AdminImp();
        managerImp.init_system(false);
        guestImp = new GuestImp();

    }

    @Test
    public void connect() throws Exception {
        try {
            assert productSupplyService.connect();
        }catch (Exception e){
            assert false;
        }
    }

    @Test
    public void order() {
    //succeess
        try {
            assertEquals(true, productSupplyService.order(new OrderDetails("IsraelIsraelovice","Rager Blvd12", "Beer Sheva", "Israel", "8458527")));

        } catch (Exception e) {
            assert false;
        }


        //failure scenario
        productSupplyService.setSupplyService(new FailingSupplyServiceStub());
        try {
            productSupplyService.order(new OrderDetails("IsraelIsraelovice","Rager Blvd12", "Beer Sheva", "Israel", "8458527"));
        } catch (Exception e) {
            try {
                assert guestImp.sign_up("mhmod", "123");
            } catch (Exception e1) {
                assert false;
            }
        }
    }
}