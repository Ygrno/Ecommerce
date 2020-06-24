package Unit.tests;

import DomainLayer.Store.VisibleDiscount;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DueDateCheck {



    @Test
    public void checkDueDate(){
        VisibleDiscount visibleDiscount = new VisibleDiscount();
        assertTrue(visibleDiscount.check_date(25122124));
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        assertTrue(visibleDiscount.check_date(Integer.parseInt(format.format(date))));
        assertFalse(visibleDiscount.check_date(21052019));
        assertFalse(visibleDiscount.check_date(23062020));
    }


}
