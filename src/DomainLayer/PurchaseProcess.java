package DomainLayer;

import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Store.Store;
import DomainLayer.User.User;
import org.json.JSONException;
import org.json.JSONObject;
//persistent
public class PurchaseProcess {

    private User user;
    private Store store;
    private ShoppingBag shoppingBag;
    boolean isDone;
    private DealDetails details;

    public DealDetails getDetails() {
        return details;
    }

    public void setDetails(DealDetails details) {
        this.details = details;
    }

    public void setDone(boolean done, DealDetails dealDetails) throws JSONException {

        update_details(dealDetails);
        isDone = done;
        for (Role role : store.getRoles()) {
            if (role instanceof StoreOwner) {
                JSONObject o = new JSONObject();
                o.put("username", role.user.getName());
                o.put("message", "Pruchase Process is done");
                role.user.notifications().add(o);
                role.user.observer().update(o);
            }
        }
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
}
