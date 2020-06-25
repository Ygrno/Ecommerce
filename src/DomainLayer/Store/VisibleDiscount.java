package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "visible_discount")

public class VisibleDiscount extends DiscountComponent implements Serializable {


    private double discount_percentage;
    private int end_of_use_date; // { Format 12062020 = 12/06/2020 }
    @OneToOne(targetEntity = Product.class,cascade = CascadeType.ALL)
    private Product product;




    public VisibleDiscount(String discount_name, double discount_percentage, int end_of_use_date, Product product) {
        this.discount_name = discount_name;
        this.discount_percentage = discount_percentage;
        this.end_of_use_date = end_of_use_date;
        this.product = product;
    }

    public VisibleDiscount() {
    }

    public Product getProduct() {
        return product;
    }

    public void setDiscount_name(String discount_name){
        this.discount_name=discount_name;
    }

    public void setProduct(Product p){
        this.product=p;
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
        java.lang.System.out.println("This product has discount percentage of: " + getDiscount_percentage() + " Due date: " + getEnd_of_use_date());
    }

    @Override
    public boolean validate(ShoppingBag shoppingBag) {
        return check_date(end_of_use_date);
    }

    @Override
    public void calculate_discount(ShoppingBag shoppingBag) {


        if(check_date(this.end_of_use_date) && !isCalculated()) {
            for(Product p: shoppingBag.getProducts()){
                final_price = p.getPrice() - (discount_percentage * p.getPrice());
                if(!p.isDiscountCalculate()  && p.getName().equals(product.getName())){
                    p.setPrice(final_price);
                    p.setDiscountCalculate(true);
                }
            }
            setCalculated(true);
        }

    }

}
