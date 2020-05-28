package DomainLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<ShoppingBag> shopping_bag_list;

    public ShoppingCart(){
        shopping_bag_list = new ArrayList<>();
    }

    public List<ShoppingBag> getShopping_bag_list() {
        return shopping_bag_list;
    }

    public void setShopping_bag_list(List<ShoppingBag> shopping_bag_list) {
        this.shopping_bag_list = shopping_bag_list;
    }

    public List<String> getProductsNames(){
        List<String> res= new ArrayList<>();
        for(ShoppingBag sb : getShopping_bag_list()){
            res.addAll(sb.getProducts_names());
        }
        return  res;
    }

    public List<JSONObject> getProducts() throws JSONException {
        List<JSONObject> res= new ArrayList<>();
        for(ShoppingBag sb : getShopping_bag_list()){
            res.addAll(sb.getAllProducts());
        }
        return  res;
    }

    public double getTotalPrice(){
        double price=0;
        for(ShoppingBag sb : getShopping_bag_list()){
            for(Product prod : sb.getProducts()){
                price+=prod.getPrice();
            }
        }
        return price;
    }
}
