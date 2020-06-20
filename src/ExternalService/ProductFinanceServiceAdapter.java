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

    public boolean connect() {
        try{
            this.FinanceService.connect();
            return true;
        }catch(Exception e){
            return false;
        }
    }


    public boolean tryToBuy(DealDetails details){
        return FinanceService.purchase(
                details.getBuyer_name(),
                details.getCreditCardNumber(),
                details.getExpireDate(),
                details.getCvv()
        );
    }
}
