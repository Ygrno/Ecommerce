package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;


import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "policies")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Policy {
    //enum Logicaloperation { or, and, xor};
    public abstract boolean validate (ShoppingBag shoppingBag, User user);

    @Id
    protected int id;




    public void setId(int id) {
        this.id = id;
    }

    public abstract int getPolicy_id();
}
