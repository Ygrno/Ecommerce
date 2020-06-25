package ExternalService;

import DomainLayer.DealDetails;
import ExternalService.Mockups.ExternalFinanceServiceMock;

public class ProductFinanceServiceAdapter {

    private ExternalFinanceService FinanceService;


    public ProductFinanceServiceAdapter(){
        this.FinanceService = new ExternalFinanceServiceMock();
    }

    public void setFinanceService(ExternalFinanceService financeService) {
        FinanceService = financeService;
    }

    public boolean connect() throws Exception {
            return this.FinanceService.connect();
    }


    public String tryToBuy(DealDetails details) throws Exception{
        return FinanceService.purchase(
                details.getBuyer_name(),
                details.getCreditCardNumber(),
                details.getExpireDate(),
                details.getCvv(),
                details.getBuyer_id()
        );
    }

    public String cancelPay(String tId) throws Exception {
        return FinanceService.cancelPay(tId);
    }
}
