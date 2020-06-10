package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "buy_policies")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BuyPolicy extends Policy {

   // enum Logicaloperation { or, and, xor};


    public BuyPolicy(int policy_id)
    {
        super.id = policy_id;
    }

    public BuyPolicy() {
    }

    @Override
    public boolean validate(ShoppingBag shoppingBag, User user) {
        return true;
    }

//    public int getPolicy_id(){
//        return policy_id;
//    }
//    public void setPolicy_id(int id){
//        this.policy_id=id;
//    }

}
