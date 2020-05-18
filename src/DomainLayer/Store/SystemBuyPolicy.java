package DomainLayer.Store;

import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

public class SystemBuyPolicy extends SimpleBuyPolicy {

    private int day;
    public SystemBuyPolicy (int policy_id, int day)
    {
        super(policy_id);
        this.day = day;
    }

    @Override
    public boolean validate (ShoppingBag shopBag, User user) {
        return true;
    }





}