package ExternalService.Mockups;

import ExternalService.ExternalSupplyService;
import DomainLayer.OrderDetails;
import ExternalService.HTTPPostClient;

import java.net.ConnectException;

public class ExternalSupplyServiceMock implements ExternalSupplyService {
    @Override
    public boolean connect() throws Exception {
        String response = HTTPPostClient.send(HTTPPostClient.makeHandshakeParams());
        boolean success = response.equals("OK");
        if (!success)
            throw new ConnectException();

        return true;
    }

    @Override
    public boolean order(OrderDetails orderDetails) {
        try {
            String response = HTTPPostClient.send(
                    HTTPPostClient.makeSupplyParams(orderDetails.getName(), orderDetails.getAddress(), orderDetails.getCity(), orderDetails.getCountry(), orderDetails.getZip())
            );

            return !response.equals("-1");
        }catch (Exception e){
            return false;
        }

    }
}
