package ExternalService.Mockups;

import ExternalService.ExternalFinanceService;
import Logs.LogErrors;

public class ExternalFinanceServiceMock implements ExternalFinanceService {


    @Override
    public boolean connect() throws Exception {
        return true;
    }

    @Override
    public boolean purchase(String accName, String ccn, String expireDate, int cvv) {
        /**
         * logic
         * **/
        return true;
    }


}
