package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PaymentInfoTest {
    @Test
    public void fromStorageFields_cash_success() {
        PaymentInfo info = PaymentInfo.fromStorageFields("CASH", null);
        assertEquals(PaymentInfo.METHOD_CASH, info.getMethod());
        assertEquals("CASH", info.toString());
    }

    @Test
    public void fromStorageFields_payNow_success() {
        PaymentInfo info = PaymentInfo.fromStorageFields("PAYNOW", "+65 91234567");
        assertEquals(PaymentInfo.METHOD_PAYNOW, info.getMethod());
        assertEquals("PAYNOW (handle: +65 91234567)", info.toString());
    }

    @Test
    public void fromStorageFields_bank_success() {
        PaymentInfo info = PaymentInfo.fromStorageFields("BANK", "REF123");
        assertEquals(PaymentInfo.METHOD_BANK, info.getMethod());
        assertEquals("REF123", info.getReferenceNumber());
    }

    @Test
    public void fromStorageFields_failures() {
        assertThrows(IllegalArgumentException.class, () -> PaymentInfo.fromStorageFields(null, null));
        assertThrows(IllegalArgumentException.class, () -> PaymentInfo.fromStorageFields("CRYPTO", null));
        assertThrows(IllegalArgumentException.class, () -> PaymentInfo.fromStorageFields("PAYNOW", "   "));
    }

    @Test
    public void factories_success() {
        assertEquals(PaymentInfo.METHOD_CASH, PaymentInfo.cash().getMethod());
        assertEquals(PaymentInfo.METHOD_PAYNOW, PaymentInfo.payNow("alice@paynow").getMethod());
        assertEquals("BANK", PaymentInfo.bank().toString());
        assertEquals("BANK (ref: REF123)", PaymentInfo.bank("REF123").toString());
    }

    @Test
    public void equals() {
        PaymentInfo cash1 = PaymentInfo.cash();
        PaymentInfo cash2 = PaymentInfo.cash();
        PaymentInfo payNow = PaymentInfo.payNow("alice@paynow");

        assertTrue(cash1.equals(cash1));
        assertTrue(cash1.equals(cash2));
        assertEquals(cash1.hashCode(), cash2.hashCode());
        assertFalse(cash1.equals(payNow));
        assertFalse(cash1.equals(null));
        assertFalse(cash1.equals("CASH"));
    }
}
