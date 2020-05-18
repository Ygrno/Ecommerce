package DomainLayer.Store;

import DomainLayer.PurchaseProcess;

import java.util.ArrayList;

public class ComplexDiscount extends DiscountComponent {

    private String discount_name;

    public String getDiscount_name() {
        return discount_name;
    }

    public void setDiscount_name(String discount_name) {
        this.discount_name = discount_name;
    }

    public ComplexDiscount(String discount_name){
        this.discount_name = discount_name;
    }

    ArrayList<DiscountComponent> and_discountComponents = new ArrayList<>();
    ArrayList<DiscountComponent> or_discountComponents = new ArrayList<>();
    ArrayList<DiscountComponent> onlyOne_discountComponents = new ArrayList<>();

    ArrayList<DiscountComponent> valid_and_discountComponents = new ArrayList<>();
    ArrayList<DiscountComponent> valid_or_discountComponents = new ArrayList<>();
    ArrayList<DiscountComponent> valid_onlyOne_discountComponents = new ArrayList<>();

    //FORMAT {[A,B,C]} => { (A && B && C) }
    //FORMAT {[A,B,C], [D,E] , [F,G]} => { (A && B && C) && (D || E) && (F ^ G) }


    public void add_and(DiscountComponent newDiscountComponent) {
        and_discountComponents.add(newDiscountComponent);
    }

    public void remove_and(DiscountComponent newDiscountComponent) {
        and_discountComponents.remove(newDiscountComponent);
    }

    public void add_or(DiscountComponent newDiscountComponent) {
        or_discountComponents.add(newDiscountComponent);
    }

    public void remove_or(DiscountComponent newDiscountComponent) {
        or_discountComponents.remove(newDiscountComponent);
    }

    public void add_xor(DiscountComponent newDiscountComponent) {
        onlyOne_discountComponents.add(newDiscountComponent);
    }

    public void remove_xor(DiscountComponent newDiscountComponent) {
        onlyOne_discountComponents.remove(newDiscountComponent);
    }

    @Override
    public boolean validate(PurchaseProcess purchaseProcess) {
        boolean and_predicate = true;
        boolean or_predicate = false;
        boolean or_predicate2 = false;
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;

        for(DiscountComponent discountComponent: and_discountComponents){
            boolean validate = discountComponent.validate(purchaseProcess);
            if(validate) valid_and_discountComponents.add(discountComponent);
            and_predicate = and_predicate && validate;
            count1++;
        }
        for(DiscountComponent discountComponent: or_discountComponents){
            boolean validate = discountComponent.validate(purchaseProcess);
            if(validate) valid_or_discountComponents.add(discountComponent);
            or_predicate = or_predicate || validate;
            count2++;
        }
        for(DiscountComponent discountComponent: onlyOne_discountComponents){
            boolean validate = discountComponent.validate(purchaseProcess);
            if(validate) valid_onlyOne_discountComponents.add(discountComponent);
            or_predicate2 = or_predicate2 || validate;
            count3++;
        }

        if(count1 == 0) and_predicate = true;
        if(count2 == 0) or_predicate = true;
        if(count3 == 0) or_predicate2 = true;

        return and_predicate && or_predicate && or_predicate2;
    }


    public void calculate_discount(PurchaseProcess purchaseProcess) {
        if(this.validate(purchaseProcess)){

            //Calculate all from And
            for(DiscountComponent discountComponent: valid_and_discountComponents){
                discountComponent.calculate_discount(purchaseProcess);
            }

            //Calculate all from Or
            for(DiscountComponent discountComponent: valid_or_discountComponents){
                discountComponent.calculate_discount(purchaseProcess);
            }

            //Calculate exactly one from Only One
            valid_onlyOne_discountComponents.get(0).calculate_discount(purchaseProcess);

        }

    }

    public void displayDiscountInfo(){
        for (DiscountComponent discount_info : and_discountComponents) {
            discount_info.displayDiscountInfo();
        }
    }

}
