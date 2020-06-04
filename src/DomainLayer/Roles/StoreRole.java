package DomainLayer.Roles;

import DomainLayer.Store.Store;
import Observer.Observer;

import java.util.ArrayList;
import java.util.List;


public abstract class StoreRole extends Role {
    public Store store;
    private List<Role> assigned_users = new ArrayList<>();
    private Role assigned_by = null;
    private List<String> Upadtes = new ArrayList<String>();
    public List<Role> getAssigned_users() {
        return assigned_users;
    }
    protected Observer observer;//notification

    public void setAssigned_users(List<Role> assigned_users) {
        this.assigned_users = assigned_users;
    }

    public Role getAssigned_by() {
        return assigned_by;
    }

    public void setAssigned_by(Role assigned_by) {
        this.assigned_by = assigned_by;
    }

    //Notification
    public Observer observer(){return observer;}



}
