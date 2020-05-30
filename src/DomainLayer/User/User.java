package DomainLayer.User;

import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public abstract class User {

    private ShoppingCart shoppingCart;
    private List<PurchaseProcess> purchaseProcesslist;

    public User(){
        purchaseProcesslist = new ArrayList<>();
        shoppingCart = new ShoppingCart();
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public List<PurchaseProcess> getPurchaseProcesslist() {
        return purchaseProcesslist;
    }
}
