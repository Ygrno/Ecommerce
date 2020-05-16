package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.List;

public class ProductBuyPolicy extends SimpleBuyPolicy {
    private int name;
    private int min;
    private int max;
    private Logicaloperation op;


    public ProductBuyPolicy (int policy_id, int name, int min, int max)
    {
        super(policy_id);
        this.name = name;
        this.min = min;
        this.max = max;
        //this.op = op;
    }

    @Override
    public boolean validate(ShoppingBag shopBag, User user) {
        List<Product> products = shopBag.getProducts();
        for (Product p : products) {
            if (p.getName().equals(this.name)) {
                if (min != 0 && p.getAmount() < min)
                    return false;
                if (max != 0 && p.getAmount() > max)
                    return false;
            }
        }
        return true;
    }





}
