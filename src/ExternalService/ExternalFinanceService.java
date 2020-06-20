package ExternalService;

public interface ExternalFinanceService {

    public boolean connect() throws Exception;

    public boolean purchase(String accName, String ccn, String expireDate, int cvv);
}
