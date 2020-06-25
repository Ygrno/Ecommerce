package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.System;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "conditioned_discounts")
public class ConditionedDiscount extends DiscountComponent {

    private double discount_percentage;
    private int end_of_use_date;     // { Format 12062020 = 12/06/2020 }
    @Column(name = "cond")
    private Condition cond;
    @OneToOne(targetEntity = Product.class,cascade = CascadeType.ALL)
    private Product product;
    @Transient
    private int required_amount, required_sum;

    public ConditionedDiscount() {
    }


    public Product getProduct() {
        return product;
    }

    public int getRequired_amount() {
        return required_amount;
    }

    public int getRequired_sum() {
        return required_sum;
    }

    //public double getFinal_price() {
       // return final_price;
    //}


    public void setProduct(Product p){
        this.product=p;
    }

    public void setRequired_amount(int required_amount){
        this.required_amount=required_amount;
    }

    public void setRequired_sum(int required_sum){
        this.required_sum=required_sum;
    }

    public void setDiscount_name(String name){
        this.discount_name=name;
    }
    public String getDiscount_name() {
        return discount_name;
    }

    public ConditionedDiscount(String discount_name, double discount_percentage, int end_of_use_date, Condition condition, Store store, Product product, int required_amount, int required_sum) {
        this.discount_name = discount_name;
        this.discount_percentage = discount_percentage;
        this.end_of_use_date = end_of_use_date;
        this.cond = condition;
        this.product = product;
        this.required_amount = required_amount;
        this.required_sum = required_sum;

    }

    public void setCond(Condition c){
        this.cond =c;
    }

    public Condition getCond() {
        return cond;
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
        java.lang.System.out.println("This product has discount percentage of: " + getDiscount_percentage() + " Due date: " + getEnd_of_use_date() + " Only if: " + getCond().toString());
    }

    @Override
    public boolean validate(ShoppingBag shoppingBag) {
        if(!check_date(this.end_of_use_date)) return false;
        List<Product> products = shoppingBag.getProducts();
        switch(cond){
            case IF_NUMBER_OF_PRODUCTS:
            {
                int count = 0;
                for(Product p:products){
                    if(p.getName().equals(this.product.getName())){
                        count++;
                        if(count >= required_amount)
                            return true;
                    }
                }

                break;

            }
            case IF_SUM_GREATER_THAN:
            {
                if(shoppingBag.getDiscounted_bag_price() >= required_sum)
                    return true;
                break;
            }
            default:
                return false;
        }
        return false;
    }

    @Override
    public void calculate_discount(ShoppingBag shoppingBag) {
        if(!isCalculated() && this.validate(shoppingBag)) {
            setCalculated(true);
            switch (cond) {
                case IF_NUMBER_OF_PRODUCTS: {
                    int count = 0;
                    for(Product p: shoppingBag.getProducts()){
                        final_price = p.getPrice() - (discount_percentage * p.getPrice());
                        if(p.getName().equals(product.getName())) {
                            count++;
                            if(count % (required_amount+1) == 0){
                                p.setPrice(final_price);
                            }
                        }

                    }

                }
                break;
                case IF_SUM_GREATER_THAN: {

                    double sum_price = shoppingBag.getDiscounted_bag_price();
                    final_price = sum_price - (discount_percentage * sum_price);
                    shoppingBag.setDiscounted_bag_price(final_price);

                }
                break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}
