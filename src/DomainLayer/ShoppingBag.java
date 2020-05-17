package DomainLayer;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBag {

    private List<String> products_names;
    private List<Product> products;

    public ShoppingBag(List<String> products_names) {
        this.products_names = products_names;
        this.products=new ArrayList<>();
    }

    public List<String> getProducts_names() {
        return products_names;
    }

    public void setProducts_names(List<String> products_names) {
        this.products_names = products_names;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double get_SumPrice(){
        double sum = 0;
        for(Product p: products){
            sum = sum + p.getPrice();
        }
        return sum;
    }
}
