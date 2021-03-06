package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "bag_buy_policies")
public class BagBuyPolicy extends SimpleBuyPolicy {
   // private String product_name;

    private int minCost; //minimum cost of bag
    private int maxCost;
    private int min_quantity; //minimum number of products in the bag
    private int max_quantity;

    public BagBuyPolicy(int policy_id,String description, int minCost, int maxCost, int min_quantity, int max_quantity)
    {
        super(policy_id,description);
        this.minCost=minCost;
        this.maxCost=maxCost;
        this.min_quantity=min_quantity;
        this.max_quantity=max_quantity;
    }

    public BagBuyPolicy() {

    }

    @Override
    public boolean validate(ShoppingBag shoppingBag) {

        List<Product> products = shoppingBag.getProducts();
        if( products.size() < min_quantity)
            return false;
        if( products.size() > max_quantity)
            return false;

        return true;
    }


/*
  //HashMap<Product,Integer> products = shoppingBag.getProductsAmounts();
        HashMap<Product,Integer> products = shoppingBag.getProductsAmounts();
        int countProduct = 0;
        double total_price=0;
        for (Product p : products.keySet()) {
            countProduct = countProduct+products.get(p);
            total_price = total_price + p.getPrice();
        }
 if(products.size() < min_quantity)
            return false;
        if(products.size() > max_quantity)
            return false;
        for(Product p :products)
            total_price = total_price + p.getPrice();
        if (total_price < minCost)
            return false;
        if (total_price > maxCost)
            return false;
 */


    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }



}
