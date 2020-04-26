package Stubs;

import DomainLayer.ExternalSerivce.PassiveObjects.ExternalSupplyService;
import DomainLayer.OrderDetails;

public class ExternalSupplyServiceStub implements ExternalSupplyService {
    @Override
    public boolean connect() throws Exception {
        return true;
    }

    @Override
    public boolean order(OrderDetails orderDetails) {
        return true;
    }
}
