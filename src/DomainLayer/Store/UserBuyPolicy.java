package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.List;

public class UserBuyPolicy extends SimpleBuyPolicy {
    private int policy_id;
    private User user;

    public UserBuyPolicy(int policy_id, User user)
    {
        super(policy_id);
        this.policy_id = policy_id;
        this.user=user;
    }

    @Override
    public boolean validate(ShoppingBag shopBag, User user) {
        return true;
    }





}
