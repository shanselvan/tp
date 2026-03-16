package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PaymentInfoTest {

    // --- CASH ---

    @Test
    public void constructor_cash_success() {
        PaymentInfo info = new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null);
        assertEquals(PaymentType.CASH, info.getType());
        assertEquals("CASH", info.toString());
    }

    @Test
    public void constructor_cashWithExtraHandle_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.CASH, "+6591234567", null, null, null, null, null));
    }

    // --- PAYNOW ---

    @Test
    public void constructor_payNow_success() {
        PaymentInfo info = new PaymentInfo(PaymentType.PAYNOW, "+65 91234567", null, null, null, null, null);
        assertEquals(PaymentType.PAYNOW, info.getType());
        assertEquals("+65 91234567", info.getHandle());
        assertEquals("PAYNOW (handle: +65 91234567)", info.toString());
    }

    @Test
    public void constructor_payNowInvalidPhoneFormat_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.PAYNOW, "+6591234567", null, null, null, null, null));
    }

    @Test
    public void constructor_payNowNullHandle_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.PAYNOW, null, null, null, null, null, null));
    }

    @Test
    public void constructor_payNowBlankHandle_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.PAYNOW, "   ", null, null, null, null, null));
    }

    @Test
    public void constructor_payNowWithExtraBankName_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.PAYNOW, "+65 91234567", "DBS", null, null, null, null));
    }

    // --- BANK ---

    @Test
    public void constructor_bank_success() {
        PaymentInfo info = new PaymentInfo(PaymentType.BANK, null, "DBS", "REF123", null, null, null);
        assertEquals(PaymentType.BANK, info.getType());
        assertEquals("DBS", info.getBankName());
        assertEquals("REF123", info.getReferenceNumber());
        assertEquals("BANK (bank: DBS, ref: REF123)", info.toString());
    }

    @Test
    public void constructor_bankNullBankName_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.BANK, null, null, "REF123", null, null, null));
    }

    @Test
    public void constructor_bankNullReferenceNumber_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.BANK, null, "DBS", null, null, null, null));
    }

    @Test
    public void constructor_bankWithExtraHandle_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.BANK, "+6591234567", "DBS", "REF123", null, null, null));
    }

    // --- CARD ---

    @Test
    public void constructor_card_success() {
        PaymentInfo info = new PaymentInfo(PaymentType.CARD, null, null, null, "4321", null, null);
        assertEquals(PaymentType.CARD, info.getType());
        assertEquals("4321", info.getLastFourDigits());
        assertEquals("CARD (last 4: 4321)", info.toString());
    }

    @Test
    public void constructor_cardNullLastFour_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.CARD, null, null, null, null, null, null));
    }

    @Test
    public void constructor_cardNonNumericLastFour_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.CARD, null, null, null, "abcd", null, null));
    }

    @Test
    public void constructor_cardTooShortLastFour_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.CARD, null, null, null, "123", null, null));
    }

    // --- EWALLET ---

    @Test
    public void constructor_eWallet_success() {
        PaymentInfo info = new PaymentInfo(PaymentType.EWALLET, null, null, null, null, "GrabPay", "user@grab.com");
        assertEquals(PaymentType.EWALLET, info.getType());
        assertEquals("GrabPay", info.getWalletProvider());
        assertEquals("user@grab.com", info.getWalletAccountId());
        assertEquals("EWALLET (provider: GrabPay, account: user@grab.com)", info.toString());
    }

    @Test
    public void constructor_eWalletNullProvider_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.EWALLET, null, null, null, null, null, "user@grab.com"));
    }

    @Test
    public void constructor_eWalletNullAccountId_throwsIae() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentInfo(PaymentType.EWALLET, null, null, null, null, "GrabPay", null));
    }

    // --- isValidLastFourDigits ---

    @Test
    public void isValidLastFourDigits_validInputs() {
        assertTrue(PaymentInfo.isValidLastFourDigits("0000"));
        assertTrue(PaymentInfo.isValidLastFourDigits("1234"));
        assertTrue(PaymentInfo.isValidLastFourDigits("9999"));
    }

    @Test
    public void isValidLastFourDigits_invalidInputs() {
        assertFalse(PaymentInfo.isValidLastFourDigits(null));
        assertFalse(PaymentInfo.isValidLastFourDigits("123"));
        assertFalse(PaymentInfo.isValidLastFourDigits("12345"));
        assertFalse(PaymentInfo.isValidLastFourDigits("abcd"));
    }

    // --- equals / hashCode ---

    @Test
    public void equals() {
        PaymentInfo cash1 = new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null);
        PaymentInfo cash2 = new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null);
        PaymentInfo payNow = new PaymentInfo(PaymentType.PAYNOW, "+65 91234567", null, null, null, null, null);

        assertTrue(cash1.equals(cash1));
        assertTrue(cash1.equals(cash2));
        assertEquals(cash1.hashCode(), cash2.hashCode());
        assertFalse(cash1.equals(payNow));
        assertFalse(cash1.equals(null));
        assertFalse(cash1.equals("CASH"));
    }
}
