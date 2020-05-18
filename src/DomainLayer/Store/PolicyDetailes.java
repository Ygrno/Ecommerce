package DomainLayer.Store;

import DomainLayer.InternalService.Logicaloperation;
import DomainLayer.User.User;

public class PolicyDetailes {

    //enum Logicaloperation { or, and, xor};
    private int type;   //1=buy; 2=product; 3=system; 4=user
    private int policy_id;
    private String product_name; //product
    private int min;
    private int max;
    private User user; //user
    private int max_quantity;
    private Logicaloperation op;

    public PolicyDetailes(int type, int policy_id, String product_name, int min, int max, User user, int max_quantity){
        this.type = type;
        this.policy_id = policy_id;
        this.product_name = product_name;
        this.min = min;
        this.max = max;
        this.user = user;
        this.max_quantity = max_quantity;
    }

}
