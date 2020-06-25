package ExternalService.Mockups;

import ExternalService.ExternalFinanceService;
import ExternalService.HTTPPostClient;
import Logs.LogErrors;

import java.io.IOException;
import java.net.ConnectException;

public class ExternalFinanceServiceMock implements ExternalFinanceService {


    @Override
    public boolean connect() throws Exception {
        String response = HTTPPostClient.send(HTTPPostClient.makeHandshakeParams());
        boolean success = response.equals("OK");
        if (!success)
            throw new ConnectException();

        return true;
    }

    @Override
    public String purchase(String accName, String ccn, String expireDate, int cvv, String id) throws Exception {
        String[] date = expireDate.split("/");
        try {
            String response = HTTPPostClient.send(HTTPPostClient.makePayParams(ccn, date[0], date[1], accName, String.valueOf(cvv), id));
            return response;
        }catch (IOException e){
            System.out.println("Time out !");
            return "-1";
        }

    }

    @Override
    public String cancelPay(String tId) throws Exception {

        return HTTPPostClient.send(HTTPPostClient.makeCancelPayParams(tId));
    }

}
