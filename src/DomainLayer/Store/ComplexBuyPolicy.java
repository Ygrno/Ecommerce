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
    public boolean validate(PurchaseProcess purchaseProcess) {
        switch (this.op){
            case and:
                validate_and(purchaseProcess);
                break;
            case or:
                validate_or(purchaseProcess);
                break;
            case xor://todo hila
                return true;
        }
        return false;
    }

    private boolean validate_and(PurchaseProcess purchaseProcess) {
        for(BuyPolicy p : policies_list){
            if(p.validate(purchaseProcess) == false){
                return false;
            }
        }
        return true;
    }

    private boolean validate_or(PurchaseProcess purchaseProcess) {
        for(BuyPolicy p : policies_list){
            if(p.validate(purchaseProcess)){
                return true;
            }
        }
        return false;
    }

}
