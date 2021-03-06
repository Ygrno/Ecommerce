package DomainLayer;

import DomainLayer.Store.Store;
import DomainLayer.User.ProductReview;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//persistence

@Entity
@Table(name="products")
public class Product implements Serializable {

    @Column
    private String name;
    @Column
    private double price;
    @Column
    private int supplied_amount;
/*    @Column
    private int Buy_amount;*/
    @ManyToOne(targetEntity = Store.class)
    @JoinColumn(name ="store_id")
    private Store store;
    @OneToMany(mappedBy = "product",cascade=CascadeType.ALL)
    private List<ProductReview> product_review_list;

    private boolean discountCalculate;


    @ManyToOne(targetEntity = ShoppingBag.class)
    @JoinColumn(name = "shoppingBag_id")
    private ShoppingBag shoppingBag;
    public Product() {
    }


//    public int getBuy_amount() {
//        return Buy_amount;
//    }

//    public void setBuy_amount(int buy_amount) {
//        Buy_amount = buy_amount;
//    }

    public Product(String name, double price, int supplied_amount, Store store) {
        this.name = name;
        this.price = price;
        this.supplied_amount = supplied_amount;
        this.store = store;


        product_review_list = Collections.synchronizedList(new  ArrayList<>());
        this.discountCalculate=false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSupplied_amount() {
        return supplied_amount;
    }

    public void setSupplied_amount(int supplied_amount) {
        this.supplied_amount = supplied_amount;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    public void  addReview(ProductReview pr){
        product_review_list.add(pr);
    }
    public ShoppingBag getShoppingBag() {
        return shoppingBag;
    }

    public void setShoppingBag(ShoppingBag shoppingBag) {
        this.shoppingBag = shoppingBag;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDiscountCalculate() {
        return discountCalculate;
    }

    public void setDiscountCalculate(boolean discountCalculate) {
        this.discountCalculate = discountCalculate;
    }


//    @Override
//    public boolean equals(Object obj) {
//        return ((Product)obj).getName().equals(this.name);
//    }
}
