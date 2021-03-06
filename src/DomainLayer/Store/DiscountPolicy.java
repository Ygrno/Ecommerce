package DomainLayer.Store;

import jdk.jfr.Enabled;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Discount_policies")
public class DiscountPolicy {


    public void setDiscounts(List<DiscountComponent> discounts) {
        this.discounts = discounts;
    }

    
   

   @OneToMany(mappedBy = "discountPolicy",cascade=CascadeType.ALL)
    private List<DiscountComponent> discounts = Collections.synchronizedList(new  ArrayList<>());


    public List<DiscountComponent> getDiscounts() {
        return discounts;
    }

    public void add_discount(DiscountComponent discountComponent) {
        discounts.add(discountComponent);
    }

    public boolean check_if_unique(String discount_name){
        for(DiscountComponent dc : discounts){
            if(dc instanceof VisibleDiscount && ((VisibleDiscount) dc).getDiscount_name().equals(discount_name))
                return false;
            if(dc instanceof ConditionedDiscount && ((ConditionedDiscount) dc).getDiscount_name().equals(discount_name))
                return false;
            if(dc instanceof ComplexDiscount && ((ComplexDiscount) dc).getDiscount_name().equals(discount_name))
                return false;
        }
        return true;
    }


    public DiscountComponent get_discount_by_name(String discount_name){
        for(DiscountComponent dc : discounts){
            if(dc instanceof VisibleDiscount && ((VisibleDiscount) dc).getDiscount_name().equals(discount_name))
                return dc;
            if(dc instanceof ConditionedDiscount && ((ConditionedDiscount) dc).getDiscount_name().equals(discount_name))
                return dc;
            if(dc instanceof ComplexDiscount && ((ComplexDiscount) dc).getDiscount_name().equals(discount_name))
                return dc;
        }
        return null;
    }

    public void delete_discount(String discount_name){
        List<DiscountComponent> toRemove = new ArrayList<DiscountComponent>();
        for(DiscountComponent dc : discounts){
            if(dc instanceof VisibleDiscount && ((VisibleDiscount) dc).getDiscount_name().equals(discount_name))
                toRemove.add(dc);
            if(dc instanceof ConditionedDiscount && ((ConditionedDiscount) dc).getDiscount_name().equals(discount_name))
                toRemove.add(dc);
            if(dc instanceof ComplexDiscount && ((ComplexDiscount) dc).getDiscount_name().equals(discount_name))
                toRemove.add(dc);
        }
        discounts.removeAll(toRemove);
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
