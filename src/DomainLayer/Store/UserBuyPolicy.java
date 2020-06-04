package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.List;

public class UserBuyPolicy extends SimpleBuyPolicy {
    private int policy_id;
    //private User user;

    public UserBuyPolicy(int policy_id)
    {
        super(policy_id);
        this.policy_id = policy_id;
      //  this.user=user;
    }

    @Override
    public boolean validate(PurchaseProcess purchaseProcess) {
        return true;
    }





}
