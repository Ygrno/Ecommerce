package DomainLayer;
//persistence
public class DealDetails {
    private double price;
    private String user_id;
    private String buyer_name;
    private String creditCardNumber;
    private String expireDate;
    private int cvv;
    private String buyer_id;


    public DealDetails(String id, double price, String buyer_name, String creditCardNumber, String expireDate, int cvv, String buyer_id) {
        this.user_id = id;
        this.price = price;
        this.buyer_name = buyer_name;
        this.creditCardNumber = creditCardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
        this.buyer_id = buyer_id;
    }

    public double getPrice() {
        return price;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getUser_id() { return user_id; }

    public String getBuyer_id(){return this.buyer_id;}

    public int getCvv() {
        return cvv;
    }
}
