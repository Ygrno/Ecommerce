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
    @OneToMany(cascade = CascadeType.ALL)
    private List<PurchaseProcess> purchaseProcesslist;
    @Transient
    private List<JSONObject> notifications;
    public User(){
        purchaseProcesslist = Collections.synchronizedList(new  ArrayList<>());
        shoppingCart = new ShoppingCart();
        notifications= Collections.synchronizedList(new  ArrayList<>());
        this.id = System.nextUserId++;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public List<PurchaseProcess> getPurchaseProcesslist() {
        return purchaseProcesslist;
    }


    public List<JSONObject> notifications(){return notifications;}


    @Id
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
