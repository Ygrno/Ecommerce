package DomainLayer;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBag {

    private List<String> products_names;
    private List<Product> products;
    private double discounted_bag_price;


    public ShoppingBag(List<String> products_names) {
        this.products_names = products_names;
        this.products=new ArrayList<>();
    }

    public double getDiscounted_bag_price() {
        return discounted_bag_price;
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

    public void setDiscounted_bag_price(double discounted_bag_price) {
        this.discounted_bag_price = discounted_bag_price;
    }

    public void calculate_discounted_bag_price(){
        double sum = 0;
        for(Product p: products){
            sum = sum + p.getPrice();
        }
        discounted_bag_price = sum;
    }




}
