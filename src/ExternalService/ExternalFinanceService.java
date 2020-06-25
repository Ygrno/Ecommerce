package ExternalService;

public interface ExternalFinanceService {

    public boolean connect() throws Exception;

    public String purchase(String accName, String ccn, String expireDate, int cvv, String id) throws Exception;

    public String cancelPay(String tId) throws Exception;
}
