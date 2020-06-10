package DomainLayer.Roles;


import DomainLayer.Store.Store;
import DomainLayer.System;
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
        this.id= System.nextOwnerId++;
    }

    public StoreOwner() {
    }
}
