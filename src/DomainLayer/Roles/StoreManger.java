package DomainLayer.Roles;

import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Subscriber;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Entity
@Table(name="stores_managers")
//@AttributeOverrides({
//        @AttributeOverride(name="id",column = @Column(name="id"))
//
//})
public class StoreManger extends StoreRole {
    @ElementCollection(targetClass = Permission.class)
    @JoinTable(name = "manager_permissions", joinColumns = @JoinColumn(name = "manager_id"))
    //@Column(name = "permissions", nullable = false)
    @Enumerated(EnumType.STRING)
    private   List<Permission> permissions;

    public StoreManger(Subscriber user, Store store){
        this.store = store;
        this.user = user;
        permissions = Collections.synchronizedList(new  ArrayList<>());
        permissions.add(Permission.VIEW_AND_RESPOND_TO_USERS);
        permissions.add(Permission.VIEW_STORE_HISTORY);

    }

    public StoreManger() {
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setAssigned_by(Role R){
        assigned_by= new ArrayList<Role>();
        assigned_by.add(R);
    }
    public boolean havePermission (String permission) {
        for(Permission p: permissions){
            if(p.toString().equals(permission)) return true;
        }
        return false;
    }

}
