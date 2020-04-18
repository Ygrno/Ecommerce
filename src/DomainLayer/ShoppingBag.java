package DomainLayer;

import java.util.List;

public class ShoppingBag {

    private List<String> products_names;

    public ShoppingBag(List<String> products_names) {
        this.products_names = products_names;
    }

    public List<String> getProducts_names() {
        return products_names;
    }

    public void setProducts_names(List<String> products_names) {
        this.products_names = products_names;
    }
}
