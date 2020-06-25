package DomainLayer.Roles;

import DomainLayer.Store.Store;
import Observer.Observer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="store_roles")
@Inheritance(strategy = InheritanceType.JOINED)

//@AttributeOverrides({
//        @AttributeOverride(name="id",column = @Column(name="id"))
//})
public abstract class StoreRole extends Role {
    @Transient
    protected ArrayList<Role> assigned = null;
    @Transient
    protected ArrayList<Role> assigned_by = null;

    @ManyToOne
    @JoinColumn(name = "store_id")
    public Store store;

//    @OneToMany(mappedBy = "assigned_users" )
    @Transient
    private List<Role> assigned_users = new ArrayList<>();

    @Transient
    private List<String> Updates = Collections.synchronizedList(new  ArrayList<>());

    protected StoreRole() {
    }

    public List<Role> getAssigned_users() {
        return assigned_users;
    }
    @Transient
    protected Observer observer;//notification

    public void setAssigned_users(List<Role> assigned_users) {
        this.assigned_users = assigned_users;
    }



    //Notification
    public Observer observer(){return observer;}



}
