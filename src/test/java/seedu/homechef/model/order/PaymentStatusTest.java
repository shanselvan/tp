package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.model.order.PaymentStatus.IS_PAID;
import static seedu.homechef.model.order.PaymentStatus.IS_UNPAID;
import static seedu.homechef.model.order.PaymentStatus.STATUS_PAID;
import static seedu.homechef.model.order.PaymentStatus.STATUS_UNPAID;
import static seedu.homechef.model.order.PaymentStatus.STYLE_PAID;
import static seedu.homechef.model.order.PaymentStatus.STYLE_UNPAID;

import org.junit.jupiter.api.Test;

public class PaymentStatusTest {

    @Test
    public void constructor_paidAndUnpaid_setsStatusCorrectly() {
        PaymentStatus paidStatus = new PaymentStatus(IS_PAID);
        PaymentStatus unpaidStatus = new PaymentStatus(IS_UNPAID);

        assertTrue(paidStatus.isPaid());
        assertFalse(unpaidStatus.isPaid());
    }

    @Test
    public void toString_paidAndUnpaid_returnsCorrectString() {
        PaymentStatus paidStatus = new PaymentStatus(IS_PAID);
        PaymentStatus unpaidStatus = new PaymentStatus(IS_UNPAID);

        assertEquals(STATUS_PAID, paidStatus.toString());
        assertEquals(STATUS_UNPAID, unpaidStatus.toString());
    }

    @Test
    public void equals() {
        PaymentStatus paid1 = new PaymentStatus(IS_PAID);
        PaymentStatus paid2 = new PaymentStatus(IS_PAID);
        PaymentStatus unpaid = new PaymentStatus(IS_UNPAID);

        // same object
        assertTrue(paid1.equals(paid1));

        // same value
        assertTrue(paid1.equals(paid2));

        // different value
        assertFalse(paid1.equals(unpaid));

        // null
        assertFalse(paid1.equals(null));

        // different type
        assertFalse(paid1.equals(STATUS_PAID));
    }

    @Test
    public void getStyle_paidAndUnpaid_returnsCorrectStyle() {
        PaymentStatus paidStatus = new PaymentStatus(IS_PAID);
        PaymentStatus unpaidStatus = new PaymentStatus(IS_UNPAID);

        assertEquals(STYLE_PAID, paidStatus.getStyle());
        assertEquals(STYLE_UNPAID, unpaidStatus.getStyle());
    }

    @Test
    public void hashCodeTest() {
        PaymentStatus paymentStatus1 = new PaymentStatus(IS_PAID);
        PaymentStatus paymentStatus2 = new PaymentStatus(IS_UNPAID);

        assertEquals(paymentStatus1.hashCode(), Boolean.hashCode(true));
        assertNotEquals(paymentStatus1.hashCode(), Boolean.hashCode(false));
        assertNotEquals(paymentStatus1.hashCode(), paymentStatus2.hashCode());
    }

}
