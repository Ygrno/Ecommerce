package DomainLayer.Store;

import DomainLayer.PurchaseProcess;

public abstract class DiscountComponent {


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

    public double getFinal_price() {
        throw new UnsupportedOperationException();
    }


    public boolean validate(PurchaseProcess purchaseProcess){
        throw new UnsupportedOperationException();
    }

    public void calculate_discount(PurchaseProcess purchaseProcess){
        throw new UnsupportedOperationException();
    }

    public void displayDiscountInfo() {
        throw new UnsupportedOperationException();
    }


}
