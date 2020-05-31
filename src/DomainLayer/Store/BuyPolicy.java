package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.List;

public class BuyPolicy implements Policy {

   // enum Logicaloperation { or, and, xor};
    private int policy_id;

    public BuyPolicy(int policy_id)
    {
        this.policy_id = policy_id;
    }

    @Override
    public boolean validate(PurchaseProcess purchaseProcess) {
        return true;
    }

    public int getPolicy_id(){
        return policy_id;
    }

}
