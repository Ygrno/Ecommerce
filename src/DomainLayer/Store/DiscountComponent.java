package DomainLayer.Store;

import DomainLayer.PurchaseProcess;

public abstract class DiscountComponent {


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
