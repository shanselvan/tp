package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PaymentInfoTest {
    @Test
    public void cashPayment_success() {
        PaymentInfo info = new CashPayment();
        assertTrue(info instanceof CashPayment);
        assertEquals("Cash", info.toString());
    }

    @Test
    public void payNowPayment_success() {
        PaymentInfo info = new PayNowPayment("+65 91234567");
        assertTrue(info instanceof PayNowPayment);
        assertEquals("+65 91234567", info.getReference());
        assertEquals("PayNow: +65 91234567", info.toString());
    }

    @Test
    public void bankPayment_success() {
        PaymentInfo info = new BankPayment("REF123");
        assertTrue(info instanceof BankPayment);
        assertEquals("REF123", info.getReference());
        assertEquals("Bank: REF123", info.toString());
    }

    @Test
    public void constructors_success() {
        assertTrue(new CashPayment() instanceof PaymentInfo);
        assertEquals("alice@paynow", new PayNowPayment("alice@paynow").getReference());
        assertEquals("Bank: REF123", new BankPayment("REF123").toString());
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

        assertEquals(cash1, cash1);
        assertEquals(cash1, cash2);
        assertEquals(cash1.hashCode(), cash2.hashCode());
        assertNotEquals(cash1, payNow);
        assertNotEquals(cash1, null);
        assertFalse(cash1.equals("CASH"));
    }
}

