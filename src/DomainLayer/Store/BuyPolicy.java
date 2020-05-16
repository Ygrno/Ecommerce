package DomainLayer.Store;

import DomainLayer.Product;
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
    public boolean validate(ShoppingBag shopBag, User user) {
        return false;
    }

}
