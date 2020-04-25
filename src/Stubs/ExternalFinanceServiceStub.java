package Stubs;

import DomainLayer.ExternalSerivce.PassiveObjects.ExternalFinanceService;

public class ExternalFinanceServiceStub implements ExternalFinanceService {


    @Override
    public boolean connect() throws Exception {
        return true;
    }

    @Override
    public boolean purchase(String accName, String ccn, String expireDate, int cvv) {
        return true;
    }


}
