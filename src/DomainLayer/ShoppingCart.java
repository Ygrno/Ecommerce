package DomainLayer;

import DomainLayer.Store.DiscountComponent;
import DomainLayer.Store.DiscountPolicy;
import DomainLayer.Store.Store;

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

    public double getTotalPrice(){

        double cartTotalPrice=0;

        //First call will calculate all product based discounts.
        CalculateDiscounts();

        //Calculate discounted price of each bag (without sum conditions).
        for(ShoppingBag sb : getShopping_bag_list()){
            sb.calculate_discounted_bag_price();
        }

        //Second call now Contains discounted bags price so , we can now compute sum discounts we couldn't before.
        CalculateDiscounts();


        for(ShoppingBag sb : getShopping_bag_list()){
            cartTotalPrice += sb.getDiscounted_bag_price();
        }

        return cartTotalPrice;
    }

    private void CalculateDiscounts() {
        for(ShoppingBag sb: getShopping_bag_list()){
            if(!sb.getProducts().isEmpty()) {
                Store store = sb.getProducts().get(0).getStore();
                DiscountPolicy discountPolicy = store.getDiscountPolicy();
                List<DiscountComponent> discountComponentList = discountPolicy.getDiscounts();
                for (DiscountComponent discount : discountComponentList) {
                    discount.calculate_discount(sb);
                }
            }
        }
    }
}
