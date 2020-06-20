package ExternalService.Mockups;

import ExternalService.ExternalSupplyService;
import DomainLayer.OrderDetails;

public class ExternalSupplyServiceMock implements ExternalSupplyService {
    @Override
    public boolean connect() throws Exception {
        return true;
    }

    @Override
    public boolean order(OrderDetails orderDetails) {
        return true;
    }
}
