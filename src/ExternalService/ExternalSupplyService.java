package ExternalService;

import DomainLayer.OrderDetails;

public interface ExternalSupplyService {

    public boolean connect() throws Exception;

    public boolean order(OrderDetails orderDetails);

}
