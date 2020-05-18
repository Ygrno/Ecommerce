package DomainLayer.Store;

import DomainLayer.InternalService.Logicaloperation;
import DomainLayer.Product;
import DomainLayer.ShoppingBag;
import DomainLayer.User.User;

import java.util.List;
import DomainLayer.*;

public class ComplexBuyPolicy extends BuyPolicy{
    private List<SimpleBuyPolicy> policies_list;
    private Logicaloperation op;

    public List<SimpleBuyPolicy> getPolicies_list() {
        return policies_list;
    }

    public ComplexBuyPolicy(int policy_id, Logicaloperation op) {
        super(policy_id);
        this.op = op;
    }

    @Override
    public boolean validate(ShoppingBag shopBag, User user) {
        switch (this.op){
            case and:
                validate_and(shopBag, user);
                break;
            case or:
                validate_or(shopBag, user);
                break;
            case xor://todo hila
                return true;
        }
        return false;
    }

    private boolean validate_and(ShoppingBag shopBag, User user) {
        for(SimpleBuyPolicy p : policies_list){
            if(p.validate(shopBag, user) == false){
                return false;
            }
        }
        return true;
    }

    private boolean validate_or(ShoppingBag shopBag, User user) {
        for(SimpleBuyPolicy p : policies_list){
            if(p.validate(shopBag, user)){
                return true;
            }
        }
        return false;
    }

}
