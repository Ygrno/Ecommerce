package DomainLayer.Roles;

import DomainLayer.User.Subscriber;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
