package DomainLayer;

import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Store.Store;
import DomainLayer.User.User;
import Observer.Observer;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity
@Table(name = "purchases_processes")
public class PurchaseProcess {

    @Id
    private int id;
    @OneToOne(targetEntity = User.class)
    private User user;
    @OneToOne(targetEntity = Store.class)
    private Store store;
    @OneToOne(targetEntity = ShoppingBag.class)
    private ShoppingBag shoppingBag;
    @Column
    private boolean isDone;
    @Transient
    private DealDetails details;
    @Column
    private int finished_date;

    public PurchaseProcess() {
    }

    public DealDetails getDetails() {
        return details;
    }

    public void setDetails(DealDetails details) {
        this.details = details;
    }

    public void setDone(boolean done, DealDetails dealDetails) throws Exception {

        update_details(dealDetails);
        isDone = done;
        for (Role role : store.getRoles()) {
            if (role instanceof StoreOwner) {
                JSONObject o = new JSONObject();
                o.put("username", role.user.getName());
                o.put("message", "Pruchase Process is done");
                Observer.update(o);
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String dateString = format.format( new Date()   );
        finished_date=Integer.parseInt(dateString);

    }



    public int getFinished_date() {
        return finished_date;
    }

    private void update_details(DealDetails dealDetails){

        details = new DealDetails(dealDetails.getUser_id(),shoppingBag.getDiscounted_bag_price(),dealDetails.getBuyer_name(),dealDetails.getCreditCardNumber(),dealDetails.getExpireDate(),dealDetails.getCvv());

    }
    public PurchaseProcess(User user, Store store, ShoppingBag shoppingBag) {
        this.user = user;
        this.store = store;
        this.shoppingBag = shoppingBag;
        this.isDone = false;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ShoppingBag getShoppingBag() {
        return shoppingBag;
    }

    public void setShoppingBag(ShoppingBag shoppingBag) {
        this.shoppingBag = shoppingBag;
    }

    public boolean isFinished(){
        return isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
