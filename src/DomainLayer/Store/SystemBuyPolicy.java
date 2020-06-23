package DomainLayer.Store;

import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "system_buy_policies")
public class SystemBuyPolicy extends SimpleBuyPolicy {

    private int day;
    public SystemBuyPolicy (int policy_id, String description, int day)
    {
        super(policy_id,description);
        this.day = day;
    }

    public SystemBuyPolicy() {
    }

    @Override
    public boolean validate (ShoppingBag shoppingBag) {
        return true;
    }





}
