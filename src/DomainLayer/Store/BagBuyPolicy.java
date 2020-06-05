package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.List;

public class BagBuyPolicy extends SimpleBuyPolicy {
   // private String product_name;
    private int minCost;
    private int maxCost;
    private int min_quantity;
    private int max_quantity;

    public BagBuyPolicy(int policy_id, int minCost, int maxCost, int min_quantity, int max_quantity)
    {
        super(policy_id);
        this.minCost=minCost;
        this.maxCost=maxCost;
        this.min_quantity=min_quantity;
        this.max_quantity=max_quantity;
    }

    @Override
    public boolean validate(ShoppingBag shoppingBag, User user) {

        //ShoppingBag shopBag= purchaseProcess.getShoppingBag();
        List<Product> products = shoppingBag.getProducts();
        if(products.size() < min_quantity)
            return false;
        if(products.size() > max_quantity)
            return false;
        return true;
    }


    //@Override
   // public boolean validate(ShoppingBag shoppingBag, User user) {
       // return false;
    //}
}
