package DomainLayer;

import DomainLayer.Store.Store;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String name;
    private int price;
    private int amount;
    private Store store;
    private List<ProductReview> product_rivew_list;


    public Product(String name, int price, int amount, Store store) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.store = store;
        product_rivew_list = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<ProductReview> getProduct_rivew_list() {
        return product_rivew_list;
    }
    public void  addReview(ProductReview pr){
        product_rivew_list.add(pr);
    }
}
