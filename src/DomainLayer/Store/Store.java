package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreManger;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Roles.StoreRole;
import DomainLayer.ShoppingBag;
import DomainLayer.System;
import DomainLayer.User.User;
import Observer.Observer;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Entity
@Table(name = "stores")
public class Store {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne(targetEntity = Policy.class,cascade = CascadeType.ALL)
    private Policy purchasePolicy;
    @OneToOne(targetEntity = DiscountPolicy.class,cascade = CascadeType.ALL)
    private DiscountPolicy discountPolicy;
    @Column(length = 50)
    private String name;

    private boolean is_open = true;
    @OneToMany(mappedBy = "store",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    private List<Product> product_list = Collections.synchronizedList(new  ArrayList<>());
    @OneToMany(mappedBy = "store",cascade=CascadeType.ALL)
    private List<PurchaseProcess> purchase_process_list = Collections.synchronizedList(new  ArrayList<>());
    @OneToMany(mappedBy = "store",cascade=CascadeType.ALL)
    private List<StoreRole> roles = Collections.synchronizedList(new  ArrayList<>());
    //hila
    @OneToMany(mappedBy = "store",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    private List<BuyPolicy> buyPolicyList = Collections.synchronizedList(new  ArrayList<>());

    public Store(String name) {
        //TODO: require policy
        List<Product> p=System.dbAccess.select(Product.class);
//        for(Product pr: p){
//            java.lang.System.out.println(pr.getStore().getId());
//            if(pr.getStore().getId()==this.id)
//                this.product_list.add(pr);
//        }

        this.name = name;
       // this.id = System.nextStoreId++;
        discountPolicy = new DiscountPolicy();
    }

    public Store() {
    }

    public List<BuyPolicy> getBuyPolicyList() {
        return buyPolicyList;
    }


    public void setPurchasePolicy(BuyPolicy buyPolicy) {
        buyPolicyList.add(buyPolicy);
    }
    public void removePurchasePolicy(BuyPolicy buyPolicy) {
        buyPolicyList.remove(buyPolicy);
    }


    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) throws Exception {
        //notification Ahmad
        for(Role role : roles){
            if(role instanceof StoreOwner) {
                JSONObject o = new JSONObject();
                o.put("username",role.user.getName());
                o.put("message", "store: "+this.name+" is open");
                role.user.notifications().add(o);
                Observer.update(o);
            }
        }
        //notification

        this.is_open = is_open;
    }

    public List<Product> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<Product> product_list) {
        this.product_list = product_list;
    }

    public List<PurchaseProcess> getPurchase_process_list() {
        return purchase_process_list;
    }

    public void setPurchase_process_list(List<PurchaseProcess> purchase_process_list) {
        this.purchase_process_list = purchase_process_list;
    }

    public List<StoreRole> getRoles() {
        return roles;
    }

    public void setRoles(List<StoreRole> roles) {
        this.roles = roles;
    }
    public void removeRole(List<StoreRole> roles) {
        this.roles = roles;
    }
    public Product getProduct(String product_name){
        Product product = null;
        for(Product p : product_list){
            if(product_name.equals(p.getName())){
                product = p;
            }
        }
        return product;
    }

    public StoreOwner find_store_owner_by_name(String user_name){
        for(Role role : roles){
            if(role instanceof StoreOwner && ((StoreOwner)role).user.getName().equals(user_name)) return (StoreOwner) role;
        }
        return null;
    }

    public StoreManger find_store_manager_by_name(String user_name){
        for(Role role : roles){
            if(role instanceof StoreManger && ((StoreManger)role).user.getName().equals(user_name)) return (StoreManger) role;
        }
        return null;
    }

    public boolean validatePurchasePolicies(ShoppingBag shoppingBag, User user) {
        for(Policy p: buyPolicyList){
            if (!p.validate(shoppingBag, user))
                return false;
        }
        return true;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
