package acceptance;

import ServiceLayer.GuestImp;
import ServiceLayer.AdminImp;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

public class uc_1_1_init_system_with_file {

    private static GuestImp guestImp;
    private static AdminImp managerImp;



    @Test
    public void TEST() throws IOException {
        managerImp = new AdminImp();
        assert managerImp.init_system(true);
    }

/*    @Test
    public void success_scenario() throws JSONException {
        assert SystemManage_Facade.get_subscriber("hila").isLogged_in();
        assert SystemManage_Facade.get_subscriber("user1").isLogged_in();
        assert SystemManage_Facade.get_subscriber("user2").isLogged_in();

        assert SystemManage_Facade.is_initialized();
        assert SystemManage_Facade.find_store("shoes");
        //assert SystemManage_Facade.getAllStores().isEmpty()==false;   TODO check function getAllStores();
        assert SystemManage_Facade.get_subscriber("hila").getRole_list().size()==1;
        assert SystemManage_Facade.get_subscriber("user1").getRole_list().size()==1;
        assert SystemManage_Facade.get_subscriber("user2").getRole_list().size()==1;
        assert SystemManage_Facade.get_subscriber("user1").get_role_at_store("shoes")!=null;
    }


    @Test
    public void failure_scenario() {
        assertFalse (SystemManage_Facade.find_store("books"));
        assertFalse (SystemManage_Facade.get_subscriber("user2").get_role_at_store("shoes")==null);
        assertFalse (SystemManage_Facade.get_subscriber("hila").getRole_list().size()==0);
    }*/
}
