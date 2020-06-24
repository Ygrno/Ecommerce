package DomainLayer.Store;

import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "discount_components")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DiscountComponent {

    @Column
    private boolean calculated = false;
    @Column
    public double final_price = -1;

    @Column
    public String discount_name;

    public void setDiscount_name(String discount_name) {
        this.discount_name = discount_name;
    }

    public String getDiscount_name() {
        return discount_name;
    }

    protected DiscountPolicy discountPolicy;

    public void setFinal_price(int final_price){
        this.final_price=final_price;
    }
    public double getFinal_price() { return final_price; }

    public boolean isCalculated() { return calculated; }

    public void setCalculated(boolean calculated) { this.calculated = calculated; }

    public void add_and(DiscountComponent newDiscountComponent) {
        throw new UnsupportedOperationException();
    }

    public void remove_and(DiscountComponent newDiscountComponent) {
        throw new UnsupportedOperationException();
    }

    public void add_or(DiscountComponent newDiscountComponent) {
        throw new UnsupportedOperationException();
    }

    public void remove_or(DiscountComponent newDiscountComponent) {
        throw new UnsupportedOperationException();
    }

    public void add_OnlyOne(DiscountComponent newDiscountComponent) {
        throw new UnsupportedOperationException();
    }

    public void remove_OnlyOne(DiscountComponent newDiscountComponent) {
        throw new UnsupportedOperationException();
    }

    public boolean check_date(int due_date){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        String current_date = formatter.format((date));

        String end_date = String.valueOf(due_date);

        String current_year = current_date.substring(4);
        String end_year = end_date.substring(4);

        if(Integer.parseInt(end_year) < Integer.parseInt(current_year)) return false;
        else if(Integer.parseInt(end_year) > Integer.parseInt(current_year)) return true;
        else
        {
            String current_month = current_date.substring(2,4);
            String end_month = end_date.substring(2,4);

            if(Integer.parseInt(end_month) < Integer.parseInt(current_month)) return false;
            else if(Integer.parseInt(end_month) > Integer.parseInt(current_month)) return true;
            else {

                String current_day = current_date.substring(0,2);
                String end_day = end_date.substring(0,2);
                return Integer.parseInt(end_day) >= Integer.parseInt(current_day);
            }

        }
    }

    public boolean validate(ShoppingBag shoppingBag){
        throw new UnsupportedOperationException();
    }

    public void calculate_discount(ShoppingBag shoppingBag){
        throw new UnsupportedOperationException();
    }

    public void displayDiscountInfo() {
        throw new UnsupportedOperationException();
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "discountPolicy_id")
    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    protected int id;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
