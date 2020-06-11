package DomainLayer;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "shoppings_bags")
public class ShoppingBag {

//    @ElementCollection
//    @CollectionTable(name="products_names_table", joinColumns=@JoinColumn(name="shooping_bag_id"))
//    @Column(name="products_names")
//    @Transient
    private List<String> products_names;
    private List<Product> products;
    @Column
    private double discounted_bag_price;


    public ShoppingBag(List<String> products_names) {
        this.products_names = products_names;
        this.products=new ArrayList<>();
        this.id=System.nextShoppingBagId++;
    }

    public ShoppingBag() {
    }

    public double getDiscounted_bag_price() {
        return discounted_bag_price;
    }
    @ElementCollection
    @CollectionTable(name="products_names_table", joinColumns=@JoinColumn(name="shooping_bag_id"))
    @Column(name="products_names")
    public List<String> getProducts_names() {
        return products_names;
    }
    @Transient
    public List<JSONObject> getAllProducts() throws JSONException {
        List<JSONObject> products=new ArrayList<>();
        for(Product p:this.products){
            JSONObject o=new JSONObject();
            o.put("name",p.getName());
            o.put("price",p.getPrice());
            o.put("store",p.getStore().getName());
            products.add(o);
        }
        return products;
    }

    public void setProducts_names(List<String> products_names) {
        this.products_names = products_names;
    }
    @OneToMany(cascade = CascadeType.ALL)
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products){
        this.products=products;
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


    private int id;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
