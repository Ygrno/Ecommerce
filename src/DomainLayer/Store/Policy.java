package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;


import java.util.List;

public interface Policy {
    //enum Logicaloperation { or, and, xor};
    public boolean validate (PurchaseProcess purchaseProcess);
    public int getPolicy_id();
}
