package integration.stubs;

import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreRole;
import DomainLayer.User.Subscriber;

import java.util.List;

public class SubscriberStub extends Subscriber {
    public SubscriberStub() {
        super("Sub_stub", "somepassword123");
    }


    @Override
    public String getName() {
        return "Sub_stub";
    }

    @Override
    public String getPassword() {
        return "somepassword123";
    }

    @Override
    public List<Role> getRole_list() {
        return super.getRole_list();
    }

    @Override
    public List<PurchaseProcess> getPurchaseProcesslist() {
        return super.getPurchaseProcesslist();
    }

    @Override
    public boolean isLogged_in() {
        return super.isLogged_in();
    }

    @Override
    public void setLogged_in(boolean logged_in) {
        super.setLogged_in(logged_in);
    }

    @Override
    public StoreRole get_role_at_store(String store_name) {
        return super.get_role_at_store(store_name);
    }

    @Override
    public List<String> getQuries() {
        return super.getQuries();
    }
}
