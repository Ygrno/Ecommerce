package DomainLayer.User;

import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreRole;
import DomainLayer.System;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//persistence
@Entity
@Table(name="subscribers")
public class Subscriber extends User {

    @Id
    private int id;

    @Column(name="user_name")
    private String name;
    @Column(name="password")
    private String password;
    //    @OneToMany(mappedBy = "roles")
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    private List<Role> role_list;
    @Transient
    private boolean logged_in = false;
    @Transient
    private List<String> Queries; //3.5


    public Subscriber(String user_name, String password) {
        super();
        this.password = password;
        this.name = user_name;

        role_list = Collections.synchronizedList(new  ArrayList<>());
        Queries = Collections.synchronizedList(new  ArrayList<>());

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

    public List<String> getQueries() {
        return Queries;
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof  Subscriber)) return false;

        return ((Subscriber) obj).getName().equals(this.getName());
    }
}
