package DomainLayer.Roles;

import DomainLayer.Store.Store;
import Observer.Observer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="store_roles")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

//@AttributeOverrides({
//        @AttributeOverride(name="id",column = @Column(name="id"))
//})
public abstract class StoreRole extends Role {

    @ManyToOne(cascade = CascadeType.ALL)
    public Store store;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Role> assigned_users = new ArrayList<>();
    @OneToOne(targetEntity = Role.class)
    private Role assigned_by = null;
    @Transient
    private List<String> Upadtes = new ArrayList<String>();

    protected StoreRole() {
    }

    @Transient
    public List<Role> getAssigned_users() {
        return assigned_users;
    }
    @Transient
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
