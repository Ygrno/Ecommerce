package DomainLayer.ExternalSerivce;

import DomainLayer.DealDetails;

public class ProductFinanceService implements ExternalService {


    @Override
    public boolean connect() {
        return true;
    }


    public boolean tryToBuy(DealDetails details){
        return true;
    }
}
