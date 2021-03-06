package DomainLayer;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
@Entity
@Table(name = "shoppings_bags")
public class ShoppingBag {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @ElementCollection
    @CollectionTable(name="products_names_table", joinColumns=@JoinColumn(name="shooping_bag_id"))
    @Column(name="products_names")
    private List<String> products_names;
    @OneToMany(mappedBy = "shoppingBag",cascade=CascadeType.ALL)
    private List<Product> products;
    @Column
    private double discounted_bag_price;
    @ManyToOne(targetEntity = ShoppingCart.class)
    @JoinColumn(name = "shoppingCart_id")
    private ShoppingCart shoppingCart;





    public ShoppingBag() {
    }
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ShoppingBag(List<String> products_names) {
        this.products_names = products_names;

        

        this.products = Collections.synchronizedList(new  ArrayList<>());
        
    }






    

    public synchronized double getDiscounted_bag_price() {

        return discounted_bag_price;
    }

    public List<String> getProducts_names() {
        return products_names;
    }

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

    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products){
        this.products=products;
    }

    public void setDiscounted_bag_price(double discounted_bag_price) {
        this.discounted_bag_price = discounted_bag_price;
    }

    public synchronized void calculate_discounted_bag_price(){
        double sum = 0;
        for(Product p: products){
            sum = sum + p.getPrice();
        }
        discounted_bag_price = sum;
    }


    public HashMap<String,Integer> getProductsAmounts(){

        HashMap<String,Integer> map = new HashMap<>();
        for(Product p:products){
            if(map.containsKey(p.getName())){

                map.replace(p.getName(),map.get(p.getName()),map.get(p.getName())+1);
            }
            else
            {

                map.put(p.getName(),1);
            }
        }
        return map;
    }

    public HashMap<Product,Integer> getProductsAmount(){
        HashMap<Product,Integer> map = new HashMap<>();
        for(Product p:products){
            if(map.containsKey(p)){
                map.replace(p,map.get(p),map.get(p)+1);
            }
            else
            {
                map.put(p,1);
            }
        }
        return map;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
