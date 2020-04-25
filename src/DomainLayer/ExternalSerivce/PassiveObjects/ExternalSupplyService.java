package DomainLayer.ExternalSerivce.PassiveObjects;

import DomainLayer.OrderDetails;

public interface ExternalSupplyService {

    public boolean connect() throws Exception;

    public boolean order(OrderDetails orderDetails);

}
