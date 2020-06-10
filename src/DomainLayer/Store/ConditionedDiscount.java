package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.System;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "conditioned_discounts")
public class ConditionedDiscount extends DiscountComponent {

    private String discount_name;
    private double discount_percentage;
    private int end_of_use_date;     // { Format 12062020 = 12/06/2020 }
    @Column(name = "cond")
    private Condition cond;
    @OneToOne(targetEntity = Store.class,cascade = CascadeType.ALL)
    private Store store;
    @OneToOne(targetEntity = Product.class,cascade = CascadeType.ALL)
    private Product product;
    private int required_amount, required_sum;

    public ConditionedDiscount() {
    }

    //public double getFinal_price() {
       // return final_price;
   // }

    public void setStore(Store s){
        this.store=s;
    }

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
        this.store = store;
        this.product = product;
        this.required_amount = required_amount;
        this.required_sum = required_sum;
        this.id= System.nextConditionedDiscountId++;
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
        List<Product> products = shoppingBag.getProducts();
        switch(cond){
            case IF_NUMBER_OF_PRODUCTS:
            {
                for(Product p:products){
                    if(p.getName().equals(this.product.getName())){
                        if(p.getBuy_amount() >= required_amount)
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

                    final_price = product.getPrice() - (discount_percentage * product.getPrice());
                    Product discountedProduct = new Product(product.getName(),final_price , product.getSupplied_amount(), product.getStore());
                    shoppingBag.getProducts().remove(product);
                    shoppingBag.getProducts().add(discountedProduct);

                }
                case IF_SUM_GREATER_THAN: {

                    double sum_price = shoppingBag.getDiscounted_bag_price();
                    final_price = sum_price - (discount_percentage * sum_price);
                    shoppingBag.setDiscounted_bag_price(final_price);

                }
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}
