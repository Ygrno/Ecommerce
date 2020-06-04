package DomainLayer.Store;

import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;

public abstract class DiscountComponent {

    private boolean calculated = false;
    public double final_price = -1;

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


}
