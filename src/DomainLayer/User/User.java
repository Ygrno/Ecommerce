package DomainLayer.User;

import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingCart;
import Observer.Observer;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class User {

    private ShoppingCart shoppingCart;
    private List<PurchaseProcess> purchaseProcesslist;
    private List<JSONObject> notifications;
    private Observer observer;
    public User(){
        purchaseProcesslist = new ArrayList<>();
        shoppingCart = new ShoppingCart();
        notifications= null;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public List<PurchaseProcess> getPurchaseProcesslist() {
        return purchaseProcesslist;
    }

    public Observer observer(){return observer;}

    public List<JSONObject> notifications(){return notifications;}
}
