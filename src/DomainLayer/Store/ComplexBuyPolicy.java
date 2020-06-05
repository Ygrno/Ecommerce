package DomainLayer.Store;

import DomainLayer.InternalService.Logicaloperation;
import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.List;
import DomainLayer.*;

public class ComplexBuyPolicy extends BuyPolicy{
    private List<BuyPolicy> policies_list;
    private Logicaloperation op;

    public List<BuyPolicy> getPolicies_list() {
        return policies_list;
    }

    public ComplexBuyPolicy(int policy_id, Logicaloperation op) {
        super(policy_id);
        this.op = op;
    }

    @Override
    public boolean validate(ShoppingBag shoppingBag, User user) {
        switch (this.op){
            case and:
                validate_and(shoppingBag, user);
                break;
            case or:
                validate_or(shoppingBag, user);
                break;
            case xor://todo hila
                return true;
        }
        return false;
    }

    private boolean validate_and(ShoppingBag shoppingBag, User user) {
        for(BuyPolicy p : policies_list){
            if(p.validate(shoppingBag, user) == false){
                return false;
            }
        }
        return true;
    }

    private boolean validate_or(ShoppingBag shoppingBag, User user) {
        for(BuyPolicy p : policies_list){
            if(p.validate(shoppingBag, user)){
                return true;
            }
        }
        return false;
    }

}
