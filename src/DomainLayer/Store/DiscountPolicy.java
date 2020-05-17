package DomainLayer.Store;

import java.util.ArrayList;

public class DiscountPolicy {

    ArrayList<ComplexDiscount> complexDiscounts = new ArrayList<>();

    public void add_discount(ComplexDiscount complexDiscount) {
        complexDiscounts.add(complexDiscount);
    }

}
