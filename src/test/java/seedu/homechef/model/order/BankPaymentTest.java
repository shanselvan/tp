package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BankPaymentTest {

    @Test
    public void constructor_reference_success() {
        BankPayment payment = new BankPayment("DBS-123456");
        assertEquals("DBS-123456", payment.getReference());
        assertEquals("Bank: DBS-123456", payment.toString());
    }

    @Test
    public void constructor_normalizationNotApplied_referencePreserved() {
        BankPayment payment = new BankPayment("DBS REF 001");
        assertEquals("DBS REF 001", payment.getReference());
    }

    @Test
    public void constructor_blankReference_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BankPayment("   "));
    }

    @Test
    public void constructor_symbolOnlyReference_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BankPayment("!!!@@@"));
    }

    @Test
    public void constructor_tooLongReference_throwsIllegalArgumentException() {
        String longReference = "A".repeat(51);
        assertThrows(IllegalArgumentException.class, () -> new BankPayment(longReference));
    }

    @Test
    public void constructor_nullReference_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BankPayment(null));
    }

    @Test
    public void equals() {
        BankPayment withReferenceA = new BankPayment("DBS-123456");
        BankPayment withReferenceB = new BankPayment("DBS-123456");
        BankPayment differentReference = new BankPayment("UEN-201234567A");

        assertTrue(withReferenceA.equals(withReferenceB));
        assertEquals(withReferenceA.hashCode(), withReferenceB.hashCode());
        assertFalse(withReferenceA.equals(differentReference));
        assertFalse(withReferenceA.equals(null));
        assertFalse(withReferenceA.equals("BANK"));
    }
}
