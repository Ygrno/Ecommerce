package DomainLayer.User;


import DomainLayer.System;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;


public class Guest extends User {


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
        this.id=id;
       // purchaseProcesslist = new ArrayList<>();

    }



/*    public List<PurchaseProcess> getPurchaseProcesslist() {
        return purchaseProcesslist;
    }*/


}
