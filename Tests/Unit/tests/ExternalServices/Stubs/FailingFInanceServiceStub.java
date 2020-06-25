package Unit.tests.ExternalServices.Stubs;

import ExternalService.ExternalFinanceService;

import java.net.ConnectException;

public class FailingFInanceServiceStub implements ExternalFinanceService {
    @Override
    public boolean connect() throws Exception {
        throw new ConnectException();
    }

    @Override
    public String purchase(String accName, String ccn, String expireDate, int cvv, String id) throws Exception{
        throw new ConnectException();
    }

    @Override
    public String cancelPay(String tId) throws Exception {
        throw new Exception();
    }
}
