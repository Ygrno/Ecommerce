package DomainLayer.Store;

import java.util.ArrayList;

public class DiscountPolicy {

    ArrayList<DiscountComponent> discounts = new ArrayList<>();

    public void add_discount(DiscountComponent discountComponent) {
        discounts.add(discountComponent);
    }

}
