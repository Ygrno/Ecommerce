package DomainLayer.Store;

import DomainLayer.InternalService.Logicaloperation;
import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.List;

public class ProductBuyPolicy extends SimpleBuyPolicy {
    private String product_name;
    private int min;
    private int max;
    private Logicaloperation op;


    public ProductBuyPolicy (int policy_id, String name, int min, int max)
    {
        super(policy_id);
        this.product_name = name;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean validate(ShoppingBag shopBag, User user) {
        List<Product> products = shopBag.getProducts();
        for (Product p : products) {
            if (p.getName().equals(this.product_name)) {
                if (min != 0 && p.getAmount() < min)
                    return false;
                if (max != 0 && p.getAmount() > max)
                    return false;
            }
        }
        return true;
    }





}
