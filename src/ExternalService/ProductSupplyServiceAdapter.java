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

    public boolean connect() {
        try{
            this.SupplyService.connect();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean order(OrderDetails orderDetails){
        return this.SupplyService.order(orderDetails);
    }
}
