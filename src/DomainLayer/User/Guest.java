package DomainLayer.User;

import DomainLayer.PurchaseProcess;
import DomainLayer.User.User;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User {


    private List<PurchaseProcess> purchaseProcesslist;

    public Guest(int id) {
        super();
        super.id=id;
        purchaseProcesslist = new ArrayList<>();

    }



    public List<PurchaseProcess> getPurchaseProcesslist() {
        return purchaseProcesslist;
    }


}
