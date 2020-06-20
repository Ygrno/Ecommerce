package DomainLayer;
import DomainLayer.Store.DiscountComponent;
import DomainLayer.Store.DiscountPolicy;
import DomainLayer.Store.Store;
import org.json.JSONObject;
import org.json.JSONException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {

    @OneToMany(cascade = CascadeType.ALL)
    private List<ShoppingBag> shopping_bag_list;

    public ShoppingCart(){

        shopping_bag_list = Collections.synchronizedList(new  ArrayList<>());
        this.id=System.nextShoppingCartId++;
    }

    public List<ShoppingBag> getShopping_bag_list() {
        return shopping_bag_list;
    }

    public void setShopping_bag_list(List<ShoppingBag> shopping_bag_list) {
        this.shopping_bag_list = shopping_bag_list;
    }

    public List<String> getProductsNames(){
        List<String> res= new ArrayList<>();
        for(ShoppingBag sb : getShopping_bag_list()){
            res.addAll(sb.getProducts_names());
        }
        return  res;
    }

    public List<JSONObject> getProducts() throws JSONException {
        List<JSONObject> res= new ArrayList<>();
        for(ShoppingBag sb : getShopping_bag_list()){
            res.addAll(sb.getAllProducts());
        }
        return  res;
    }

    public synchronized double getTotalPrice(){

        double cartTotalPrice=0;

        //First call will calculate all product based discounts.
        CalculateDiscounts();

        //Calculate discounted price of each bag (without sum conditions).
        for(ShoppingBag sb : getShopping_bag_list()){
            sb.calculate_discounted_bag_price();
        }

        //Second call now Contains discounted bags price so , we can now compute sum discounts we couldn't before.
        CalculateDiscounts();


        for(ShoppingBag sb : getShopping_bag_list()){
            cartTotalPrice += sb.getDiscounted_bag_price();
        }

        return cartTotalPrice;
    }

    private synchronized void CalculateDiscounts() {
        for(ShoppingBag sb: getShopping_bag_list()){
            if(!sb.getProducts().isEmpty()) {
                Store store = sb.getProducts().get(0).getStore();
                DiscountPolicy discountPolicy = store.getDiscountPolicy();
                List<DiscountComponent> discountComponentList = discountPolicy.getDiscounts();
                for (DiscountComponent discount : discountComponentList) {
                    discount.calculate_discount(sb);
                }
            }
        }
    }

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
