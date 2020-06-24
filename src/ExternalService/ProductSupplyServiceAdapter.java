package ExternalService;

import DomainLayer.OrderDetails;
import ExternalService.Mockups.ExternalSupplyServiceMock;

public class ProductSupplyServiceAdapter {

    private ExternalSupplyService SupplyService;

    public ProductSupplyServiceAdapter(){
        this.SupplyService = new ExternalSupplyServiceMock();
    }

    public void setSupplyService(ExternalSupplyService supplyServiceImp) {
        this.SupplyService = supplyServiceImp;
    }

    public boolean connect() throws Exception {
           return this.SupplyService.connect();
    }

    public boolean order(OrderDetails orderDetails) throws Exception{
        return this.SupplyService.order(orderDetails);
    }
}
