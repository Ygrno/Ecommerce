package DomainLayer.ExternalSerivce;

import DomainLayer.DealDetails;
import DomainLayer.ExternalSerivce.PassiveObjects.ExternalFinanceService;

public class ProductFinanceService implements ExternalService {

    private ExternalFinanceService FinanceService;

    @Override
    public boolean connect() {
        try{
            this.FinanceService.connect();
            return true;
        }catch(Exception e){
            return false;
        }
    }


    public boolean tryToBuy(DealDetails details){
        return true;
    }
}
