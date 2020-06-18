package DomainLayer.User;


import DomainLayer.System;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;


@Entity
@Table(name="Guests")
public class Guest extends User {

    @Id
    private int id;

    public Guest()
    {

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

//private List<PurchaseProcess> purchaseProcesslist;

    public Guest(int id) {
        super();
        this.id= System.nextUserId++;
       // purchaseProcesslist = new ArrayList<>();

    }



/*    public List<PurchaseProcess> getPurchaseProcesslist() {
        return purchaseProcesslist;
    }*/


}
