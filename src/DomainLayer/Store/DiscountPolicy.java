package DomainLayer.Store;

import java.util.ArrayList;

public class DiscountPolicy {

    ArrayList<DiscountComponent> discounts = new ArrayList<>();

    public ArrayList<DiscountComponent> getDiscounts() {
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
        for(DiscountComponent dc : discounts){
            if(dc instanceof VisibleDiscount && ((VisibleDiscount) dc).getDiscount_name().equals(discount_name))
                discounts.remove(dc);
            if(dc instanceof ConditionedDiscount && ((ConditionedDiscount) dc).getDiscount_name().equals(discount_name))
                discounts.remove(dc);
            if(dc instanceof ComplexDiscount && ((ComplexDiscount) dc).getDiscount_name().equals(discount_name))
                discounts.remove(dc);
        }
    }

}
