package DomainLayer.Store;

import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;

import javax.persistence.*;

@Entity
@Table(name = "discount_components")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DiscountComponent {

    @Column
    private boolean calculated = false;
    @Column
    public double final_price = -1;


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
