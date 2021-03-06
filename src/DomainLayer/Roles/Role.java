package DomainLayer.Roles;

import DomainLayer.User.Subscriber;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Role {

    @ManyToOne
    @JoinColumn(name = "user_id")
    public Subscriber user;
    @ManyToOne
    @JoinColumn(name = "storeRole_id")
    private StoreRole storeRole;
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


    public Subscriber getUser() {
        return user;
    }

    public void setUser(Subscriber user) {
        this.user = user;
    }

}
