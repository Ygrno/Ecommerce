package Unit.tests.ExternalServices.Stubs;

import DomainLayer.OrderDetails;
import ExternalService.ExternalFinanceService;
import ExternalService.ExternalSupplyService;

import java.net.ConnectException;

public class FailingSupplyServiceStub implements ExternalSupplyService {

    @Override
    public boolean connect() throws Exception {
        throw new ConnectException();
    }

    @Override
    public boolean order(OrderDetails orderDetails) throws Exception{
        throw new ConnectException();
    }
}
