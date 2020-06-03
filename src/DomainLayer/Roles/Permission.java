package DomainLayer.Roles;

public enum Permission {

    //Right now only Store Manger can have different permissions that can be edited by a store owner

    //Starting Store Mangers will have these permission:
    VIEW_AND_RESPOND_TO_USERS,   //1
    VIEW_STORE_HISTORY,         //2

    //And can have these permissions as well if the store Owner would want that:
    ADD_PRODUCT,                //3
    EDIT_PRODUCT,               //4
    REMOVE_PRODUCT,             //5
    EDIT_STORE_POLICY,          //6
    ASSIGN_STORE_MANAGER,       //7
    REMOVE_STORE_MANAGER,       //8
    ADD_DISCOUNT,               //9
    ADD_BUY_POLICY              //10

}

