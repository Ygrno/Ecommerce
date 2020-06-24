package Unit.tests.ExternalServices;

import DomainLayer.DealDetails;
import ExternalService.ExternalFinanceService;
import ExternalService.Mockups.ExternalFinanceServiceMock;
import ExternalService.ProductFinanceServiceAdapter;
import DomainLayer.InternalService.SystemManage_Facade;
import ServiceLayer.AdminImp;
import ServiceLayer.GuestImp;
import Unit.tests.ExternalServices.Stubs.FailingFInanceServiceStub;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductFinanceServiceTest {


    private static ProductFinanceServiceAdapter productFinanceService;
    private static AdminImp managerImp;
    private static GuestImp guestImp;

    @BeforeClass
    public static void setUp() throws Exception {
        SystemManage_Facade.init_system();
        productFinanceService = new ProductFinanceServiceAdapter();

        //BGU Service
        ExternalFinanceService BGUservice = new ExternalFinanceServiceMock();
        productFinanceService.setFinanceService(BGUservice);

        //init resources
        managerImp = new AdminImp();
        managerImp.init_system(false);
        guestImp = new GuestImp();

    }

    @Test
    public void connect()  {
        try {
            assert productFinanceService.connect();
        }catch (Exception e){
            assert false;
        }

    }

    @Test
    public void tryToBuy() {
        //succeess
        try {
            assertEquals(true, productFinanceService.tryToBuy(new DealDetails("m", 100, "", "", "4/21", 0, "")));

        }catch (Exception e){
            assert false;
        }


        //failure scenario
        productFinanceService.setFinanceService(new FailingFInanceServiceStub());
        try{
            productFinanceService.tryToBuy(new DealDetails("m", 100, "", "", "4/21", 0, ""));
        }catch (Exception e){
            try {
                assert  guestImp.sign_up("mhmod","123");
            }catch (Exception e1){
                assert false;
            }
        }
    }



}