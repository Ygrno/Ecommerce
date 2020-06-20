package DomainLayer.Roles;

import DomainLayer.User.Subscriber;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Role {

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public Subscriber user;
    @ManyToOne
    @JoinColumn(name = "storeRole_id")
    private StoreRole storeRole;
    @ManyToOne(targetEntity = StoreRole.class,cascade=CascadeType.ALL)
    protected Role assigned_by = null;
    public Role() {

    }
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
