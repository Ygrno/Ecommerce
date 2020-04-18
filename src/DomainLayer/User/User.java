package DomainLayer.User;

import DomainLayer.ShoppingCart;

public abstract class User {

    private ShoppingCart shoppingCart;

    public User(){
        shoppingCart = new ShoppingCart();

    }

}
