package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.List;

public interface Policy {
    //enum Logicaloperation { or, and, xor};
    public boolean validate (ShoppingBag shopBag, User user);
}
