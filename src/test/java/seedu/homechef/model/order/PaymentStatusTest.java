package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PaymentStatusTest {

    @Test
    public void constructor_paidAndUnpaid_setsStatusCorrectly() {
        PaymentStatus paidStatus = new PaymentStatus(true);
        PaymentStatus unpaidStatus = new PaymentStatus(false);

        assertTrue(paidStatus.isPaid());
        assertFalse(unpaidStatus.isPaid());
    }

    @Test
    public void toString_paidAndUnpaid_returnsCorrectString() {
        PaymentStatus paidStatus = new PaymentStatus(true);
        PaymentStatus unpaidStatus = new PaymentStatus(false);

        assertEquals("PAID", paidStatus.toString());
        assertEquals("UNPAID", unpaidStatus.toString());
    }

}
