package DomainLayer.Roles;


import DomainLayer.Store.Store;
import DomainLayer.User.Subscriber;

import javax.persistence.*;

@Entity
@Table(name="stores_owners")
//@AttributeOverrides({
//        @AttributeOverride(name="id",column = @Column(name="id"))
//})
public class StoreOwner extends StoreRole {

    public StoreOwner(Subscriber user, Store store){
        this.user = user;
        this.store = store;
    }

    public StoreOwner() {
    }
}
