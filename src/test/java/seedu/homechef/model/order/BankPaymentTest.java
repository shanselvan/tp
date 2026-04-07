package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BankPaymentTest {

    @Test
    public void constructor_noReference_success() {
        BankPayment payment = new BankPayment();
        assertEquals(PaymentInfo.METHOD_BANK, payment.getMethod());
        assertEquals(null, payment.getReferenceNumber());
        assertEquals("BANK", payment.toString());
    }

    @Test
    public void constructor_reference_success() {
        BankPayment payment = new BankPayment("DBS-123456");
        assertEquals("DBS-123456", payment.getReferenceNumber());
        assertEquals("BANK (ref: DBS-123456)", payment.toString());
    }

    @Test
    public void constructor_blankReference_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BankPayment("   "));
    }

    @Test
    public void constructor_nullReference_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BankPayment(null));
    }

    @Test
    public void equals() {
        BankPayment noReferenceA = new BankPayment();
        BankPayment noReferenceB = new BankPayment();
        BankPayment withReferenceA = new BankPayment("DBS-123456");
        BankPayment withReferenceB = new BankPayment("DBS-123456");
        BankPayment differentReference = new BankPayment("UEN-201234567A");

        assertTrue(noReferenceA.equals(noReferenceA));
        assertTrue(noReferenceA.equals(noReferenceB));
        assertEquals(noReferenceA.hashCode(), noReferenceB.hashCode());
        assertTrue(withReferenceA.equals(withReferenceB));
        assertEquals(withReferenceA.hashCode(), withReferenceB.hashCode());
        assertFalse(withReferenceA.equals(differentReference));
        assertFalse(withReferenceA.equals(null));
        assertFalse(withReferenceA.equals("BANK"));
    }
}
