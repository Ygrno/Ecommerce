package DomainLayer.Store;

import DomainLayer.ShoppingBag;
import DomainLayer.User.User;
import jdk.jfr.Enabled;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "simple_buy_policies")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SimpleBuyPolicy extends BuyPolicy{

    public SimpleBuyPolicy(int policy_id, String description) {
        super(policy_id,description);
    }


    public SimpleBuyPolicy() {
    }

    @Override
    public int getPolicy_id() {
        return super.id;
    }
}
