package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user_buy_policies")
public class UserBuyPolicy extends SimpleBuyPolicy {
    private int policy_id;
    //private User user;

    public UserBuyPolicy(int policy_id)
    {
        super(policy_id);
        this.policy_id = policy_id;
      //  this.user=user;
    }

    public UserBuyPolicy() {
    }

    @Override
    public boolean validate(ShoppingBag shoppingBag, User user) {
        return true;
    }





}
