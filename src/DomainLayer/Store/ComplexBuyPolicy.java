package DomainLayer.Store;

import DomainLayer.InternalService.Logicaloperation;
import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import DomainLayer.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "complex_buy_policies")
public class ComplexBuyPolicy extends BuyPolicy{
    @OneToMany(cascade = CascadeType.ALL)
    private List<BuyPolicy> policies_list = Collections.synchronizedList(new  ArrayList<>());
    private Logicaloperation op;

    public ComplexBuyPolicy() {
    }

    public List<BuyPolicy> getPolicies_list() {
        return policies_list;
    }

    public ComplexBuyPolicy(int policy_id,String description, Logicaloperation op) {
        super(policy_id, description);
        this.op = op;
        policies_list = new ArrayList<BuyPolicy>();
    }

    @Override
    public boolean validate(ShoppingBag shoppingBag) {
        switch (this.op){
            case and:
                validate_and(shoppingBag);
                break;
            case or:
                validate_or(shoppingBag);
                break;
            case xor://todo hila
                return true;
        }
        return false;
    }

    @Override
    public int getPolicy_id() {
        return super.id;
    }

    private boolean validate_and(ShoppingBag shoppingBag) {
        for(BuyPolicy p : policies_list){
            if(p.validate(shoppingBag) == false){
                return false;
            }
        }
        return true;
    }

    private boolean validate_or(ShoppingBag shoppingBag) {
        for(BuyPolicy p : policies_list){
            if(p.validate(shoppingBag)){
                return true;
            }
        }
        return false;
    }

}
