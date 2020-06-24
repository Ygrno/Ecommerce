package Unit.tests.ExternalServices.Stubs;

import ExternalService.ExternalFinanceService;

import java.net.ConnectException;

public class FailingFInanceServiceStub implements ExternalFinanceService {
    @Override
    public boolean connect() throws Exception {
        throw new ConnectException();
    }

    @Override
    public boolean purchase(String accName, String ccn, String expireDate, int cvv, String id) throws Exception{
        throw new ConnectException();
    }
}
