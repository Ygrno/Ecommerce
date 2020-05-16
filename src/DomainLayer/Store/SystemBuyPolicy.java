package DomainLayer.Store;

import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

public class SystemBuyPolicy extends SimpleBuyPolicy {

    public SystemBuyPolicy (int policy_id)
    {
        super(policy_id);
    }

    @Override
    public boolean validate (ShoppingBag shopBag, User user) {
        return true;
    }





}
