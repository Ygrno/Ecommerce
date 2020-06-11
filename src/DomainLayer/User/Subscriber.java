package DomainLayer.User;

import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreManger;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.Roles.StoreRole;
import DomainLayer.User.User;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//persistence
@Entity
@Table(name="subscribers")
public class Subscriber extends User {


    @Column(name="user_name")
    private String name;
    @Column(name="password")
    private String password;
    //    @OneToMany(mappedBy = "roles")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private List<Role> role_list;
    @Transient
    private boolean logged_in = false;
    @Transient
    private List<String> Quries; //3.5


    public Subscriber(String user_name, String password) {
        super();
        this.password = password;
        this.name = user_name;
        role_list = new ArrayList<>();

        Quries= new ArrayList<>();
    }

    public Subscriber() {
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRole_list() {
        return role_list;
    }



    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    public StoreRole get_role_at_store(String store_name){
        StoreRole role_to_return = null;
        for(Role role : role_list){
            if(role instanceof StoreRole && ((StoreRole) role).store.getName().equals(store_name)){
                role_to_return = (StoreRole) role;
            }
        }
        return role_to_return;
    }

    public List<String> getQuries() {
        return Quries;
    }
}
