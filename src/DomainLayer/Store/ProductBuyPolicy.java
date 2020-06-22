package DomainLayer.Store;

import DomainLayer.InternalService.Logicaloperation;
import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "product_buy_policies")
public class ProductBuyPolicy extends SimpleBuyPolicy {
    private String product_name;
    private int minProducts;
    private int maxProducts;

    private Logicaloperation op;

    public ProductBuyPolicy() {
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getMinProducts() {
        return minProducts;
    }

    public void setMinProducts(int minProducts) {
        this.minProducts = minProducts;
    }

    public int getMaxProducts() {
        return maxProducts;
    }

    public void setMaxProducts(int maxProducts) {
        this.maxProducts = maxProducts;
    }

    public Logicaloperation getOp() {
        return op;
    }

    public void setOp(Logicaloperation op) {
        this.op = op;
    }




    public ProductBuyPolicy (int policy_id, String description, String name, int minProducts, int maxProducts)
    {
        super(policy_id,description);
        this.product_name = name;
        this.minProducts = minProducts;
        this.maxProducts = maxProducts;
    }

    @Override
    public boolean validate(ShoppingBag shoppingBag, User user) {
        //ShoppingBag shopBag= purchaseProcess.getShoppingBag();
        HashMap<Product,Integer> products = shoppingBag.getProductsAmounts();
        for (Product p : products.keySet()) {
            if (p.getName().equals(this.product_name)) {
                System.out.println(products.get(p));
                System.out.println(minProducts);
                if (minProducts != 0 && products.get(p) < minProducts)
                    return false;
                if (maxProducts != 0 && products.get(p) > maxProducts)
                    return false;
            }
        }
        return true;
    }





}
