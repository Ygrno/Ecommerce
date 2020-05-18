package DomainLayer.Roles;

public enum Permission {

    //Right now only Store Manger can have different permissions that can be edited by a store owner

    //Starting Store Mangers will have these permission:
    VIEW_AND_RESPOND_TO_USERS,
    VIEW_STORE_HISTORY,

    //And can have these permissions as well if the store Owner would want that:
    ADD_PRODUCT,
    EDIT_PRODUCT,
    REMOVE_PRODUCT,
    EDIT_STORE_POLICY,
    ASSIGN_STORE_MANAGER,
    REMOVE_STORE_MANAGER,
    ADD_DISCOUNT

}

