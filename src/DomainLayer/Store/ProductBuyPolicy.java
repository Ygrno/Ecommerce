package DomainLayer.Store;

import DomainLayer.InternalService.Logicaloperation;
import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.List;

public class ProductBuyPolicy extends SimpleBuyPolicy {
    private String product_name;
    private int min;
    private int max;
    private Logicaloperation op;


    public ProductBuyPolicy (int policy_id, String name, int minProducts, int maxProducts)
    {
        super(policy_id);
        this.product_name = name;
        this.min = minProducts;
        this.max = maxProducts;
    }

    @Override
    public boolean validate(PurchaseProcess purchaseProcess) {
        ShoppingBag shopBag= purchaseProcess.getShoppingBag();
        List<Product> products = shopBag.getProducts();
        for (Product p : products) {
            if (p.getName().equals(this.product_name)) {
                if (min != 0 && p.getSupplied_amount() < min)
                    return false;
                if (max != 0 && p.getSupplied_amount() > max)
                    return false;
            }
        }
        return true;
    }





}
