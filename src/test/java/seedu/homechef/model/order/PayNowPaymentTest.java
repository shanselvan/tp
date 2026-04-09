package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PayNowPaymentTest {

    @Test
    public void constructor_validHandle_success() {
        PayNowPayment payment = new PayNowPayment("+65 91234567");
        assertEquals("+65 91234567", payment.getReference());
        assertEquals("PayNow: +65 91234567", payment.toString());
    }

    @Test
    public void constructor_nonPhoneHandle_success() {
        PayNowPayment payment = new PayNowPayment("alice@paynow");
        assertEquals("alice@paynow", payment.getReference());
    }

    @Test
    public void constructor_blankHandle_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PayNowPayment("   "));
    }

    @Test
    public void constructor_nullHandle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PayNowPayment(null));
    }

    @Test
    public void equals() {
        PayNowPayment paymentA = new PayNowPayment("+65 91234567");
        PayNowPayment paymentB = new PayNowPayment("+65 91234567");
        PayNowPayment differentPayment = new PayNowPayment("alice@paynow");

        assertTrue(paymentA.equals(paymentA));
        assertTrue(paymentA.equals(paymentB));
        assertEquals(paymentA.hashCode(), paymentB.hashCode());
        assertFalse(paymentA.equals(differentPayment));
        assertFalse(paymentA.equals(null));
        assertFalse(paymentA.equals("PAYNOW"));
    }
}
