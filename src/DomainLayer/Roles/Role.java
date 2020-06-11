package DomainLayer.Roles;

import DomainLayer.User.Subscriber;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Role {

    @ManyToOne(cascade = CascadeType.ALL)
    public Subscriber user;

    public Role() {

    }
    @Id
    @Column(name ="id")
    protected int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
