package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PaymentInfoTest {
    @Test
    public void cashPayment_success() {
        PaymentInfo info = new CashPayment();
        assertEquals(PaymentInfo.METHOD_CASH, info.getMethod());
        assertEquals("CASH", info.toString());
    }

    @Test
    public void payNowPayment_success() {
        PaymentInfo info = new PayNowPayment("+65 91234567");
        assertEquals(PaymentInfo.METHOD_PAYNOW, info.getMethod());
        assertEquals("PAYNOW (handle: +65 91234567)", info.toString());
    }

    @Test
    public void bankPayment_success() {
        PaymentInfo info = new BankPayment("REF123");
        assertEquals(PaymentInfo.METHOD_BANK, info.getMethod());
        assertEquals("REF123", info.getReferenceNumber());
    }

    @Test
    public void constructors_success() {
        assertEquals(PaymentInfo.METHOD_CASH, new CashPayment().getMethod());
        assertEquals(PaymentInfo.METHOD_PAYNOW, new PayNowPayment("alice@paynow").getMethod());
        assertEquals("BANK", new BankPayment().toString());
        assertEquals("BANK (ref: REF123)", new BankPayment("REF123").toString());
    }

    @Test
    public void constructors_failures() {
        assertThrows(IllegalArgumentException.class, () -> new BankPayment("   "));
        assertThrows(IllegalArgumentException.class, () -> new PayNowPayment("   "));
    }

    @Test
    public void equals() {
        PaymentInfo cash1 = new CashPayment();
        PaymentInfo cash2 = new CashPayment();
        PaymentInfo payNow = new PayNowPayment("alice@paynow");

        assertTrue(cash1.equals(cash1));
        assertTrue(cash1.equals(cash2));
        assertEquals(cash1.hashCode(), cash2.hashCode());
        assertFalse(cash1.equals(payNow));
        assertFalse(cash1.equals(null));
        assertFalse(cash1.equals("CASH"));
    }
}

