package DomainLayer.Store;

import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

public class BagBuyPolicy extends SimpleBuyPolicy {
    private String product_name;
    private int max_quantity;

    public BagBuyPolicy(int policy_id, String product_name, int max_quantity)
    {
        super(policy_id);
        this.product_name = product_name;
        this.max_quantity = max_quantity;
    }

    @Override
    public boolean validate(ShoppingBag shopBag, User user) {
        return true;
    }





}
