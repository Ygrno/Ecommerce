package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;

public class VisibleDiscount extends DiscountComponent {

    private String discount_name;
    private double discount_percentage, final_price = -1;
    private int end_of_use_date; // { Format 12062020 = 12/06/2020 }
    private Product product;


    public VisibleDiscount(String discount_name, double discount_percentage, double final_price, int end_of_use_date, Product product) {
        this.discount_name = discount_name;
        this.discount_percentage = discount_percentage;
        this.final_price = final_price;
        this.end_of_use_date = end_of_use_date;
        this.product = product;
    }

    public double getFinal_price() {
        return final_price;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public int getEnd_of_use_date() {
        return end_of_use_date;
    }

    public void setEnd_of_use_date(int end_of_use_date) {
        this.end_of_use_date = end_of_use_date;
    }

    public void displayDiscountInfo(){
        System.out.println("This product has discount percentage of: " + getDiscount_percentage() + " Due date: " + getEnd_of_use_date());
    }

    @Override
    public boolean validate(PurchaseProcess purchaseProcess) {
        return true;
    }

    @Override
    public void calculate_discount(PurchaseProcess purchaseProcess) {
        this.final_price = product.getPrice() - (discount_percentage * product.getPrice());
    }
}
