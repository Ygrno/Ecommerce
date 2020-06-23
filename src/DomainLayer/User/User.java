package DomainLayer.User;

import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingCart;
import DomainLayer.System;
import Observer.Observer;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @OneToOne(targetEntity = ShoppingCart.class,cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    private List<PurchaseProcess> purchaseProcesslist;
    public User(){
        purchaseProcesslist = Collections.synchronizedList(new  ArrayList<>());
        shoppingCart = new ShoppingCart();

    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public List<PurchaseProcess> getPurchaseProcesslist() {
        return purchaseProcesslist;
    }




    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
