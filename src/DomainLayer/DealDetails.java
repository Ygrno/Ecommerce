package DomainLayer;

public class DealDetails {
    private int price;
    private String buyer_name;
    private String creditCardNumber;
    private String expireDate;
    private int cvv;


    public DealDetails(int price, String buyer_name, String creditCardNumber, String expireDate, int cvv) {
        this.price = price;
        this.buyer_name = buyer_name;
        this.creditCardNumber = creditCardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
    }

    public int getPrice() {
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

    public int getCvv() {
        return cvv;
    }
}
