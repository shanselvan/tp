package seedu.homechef.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.DietTag;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Price;
import seedu.homechef.model.order.Quantity;

public class ParserUtilTest {
    private static final String INVALID_CUSTOMER = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PRICE = "05.50";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_CUSTOMER = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_PRICE = "10.50";
    private static final String VALID_PRICE_NO_DECIMAL = "10";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_ORDER, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ORDER, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseCustomer_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCustomer((String) null));
    }

    @Test
    public void parseCustomer_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCustomer(INVALID_CUSTOMER));
    }

    @Test
    public void parseCustomer_validValueWithoutWhitespace_returnsCustomer() throws Exception {
        Customer expectedCustomer = new Customer(VALID_CUSTOMER);
        assertEquals(expectedCustomer, ParserUtil.parseCustomer(VALID_CUSTOMER));
    }

    @Test
    public void parseCustomer_validValueWithWhitespace_returnsTrimmedCustomer() throws Exception {
        String customerWithWhitespace = WHITESPACE + VALID_CUSTOMER + WHITESPACE;
        Customer expectedCustomer = new Customer(VALID_CUSTOMER);
        assertEquals(expectedCustomer, ParserUtil.parseCustomer(customerWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        DietTag expectedDietTag = new DietTag(VALID_TAG_1);
        assertEquals(expectedDietTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        DietTag expectedDietTag = new DietTag(VALID_TAG_1);
        assertEquals(expectedDietTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<DietTag> actualDietTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<DietTag> expectedDietTagSet = new HashSet<DietTag>(
                Arrays.asList(new DietTag(VALID_TAG_1), new DietTag(VALID_TAG_2))
        );

        assertEquals(expectedDietTagSet, actualDietTagSet);
    }

    @Test
    public void parsePrice_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrice((String) null));
    }

    @Test
    public void parsePrice_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice(INVALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithoutWhitespace_returnsPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(VALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_PRICE + WHITESPACE;
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(priceWithWhitespace));
    }

    @Test
    public void parsePrice_validIntegerValueWithoutWhitespace_returnsPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE_NO_DECIMAL);
        assertEquals(expectedPrice, ParserUtil.parsePrice(VALID_PRICE_NO_DECIMAL));
    }

    @Test
    public void parsePrice_validIntegerValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_PRICE_NO_DECIMAL + WHITESPACE;
        Price expectedPrice = new Price(VALID_PRICE_NO_DECIMAL);
        assertEquals(expectedPrice, ParserUtil.parsePrice(priceWithWhitespace));
    }

    @Test
    public void parseMenuItemName_validValueWithWhitespace_returnsTrimmedMenuItemName() throws Exception {
        String menuItemNameWithWhitespace = WHITESPACE + "Chicken Rice" + WHITESPACE;
        Food expectedName = new Food("Chicken Rice");
        assertEquals(expectedName, ParserUtil.parseFood(menuItemNameWithWhitespace));
    }

    @Test
    public void parseMenuPrice_validValueWithOneDecimal_normalizesToTwoDecimalPlaces() throws Exception {
        // EP: valid one-decimal menu price should be normalized for display consistency.
        assertEquals("5.50", ParserUtil.parseMenuPrice("5.5").value);
    }

    @Test
    public void parseMenuPrice_validIntegerValue_normalizesToTwoDecimalPlaces() throws Exception {
        // EP: valid integer menu price should be normalized for display consistency.
        assertEquals("12.00", ParserUtil.parseMenuPrice("12").value);
    }

    @Test
    public void parsePaymentInfo_noFields_returnsEmpty() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        assertTrue(result.isEmpty());
    }

    @Test
    public void parsePaymentInfo_cash_success() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.of("CASH"), Optional.empty(), Optional.empty(), Optional.empty());
        assertTrue(result.isPresent());
        assertEquals(PaymentType.CASH, result.get().getType());
    }

    @Test
    public void parsePaymentInfo_payNow_success() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.of("PAYNOW"), Optional.of("+65 91234567"), Optional.empty(), Optional.empty());
        assertTrue(result.isPresent());
        assertEquals(PaymentType.PAYNOW, result.get().getType());
        assertEquals("+65 91234567", result.get().getHandle());
    }

    @Test
    public void parsePaymentInfo_bank_success() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.of("BANK"), Optional.of("REF123"), Optional.of("DBS"), Optional.empty());
        assertTrue(result.isPresent());
        assertEquals(PaymentType.BANK, result.get().getType());
        assertEquals("DBS", result.get().getBankName());
        assertEquals("REF123", result.get().getReferenceNumber());
    }

    @Test
    public void parsePaymentInfo_card_success() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.of("CARD"), Optional.of("4321"), Optional.empty(), Optional.empty());
        assertTrue(result.isPresent());
        assertEquals(PaymentType.CARD, result.get().getType());
        assertEquals("4321", result.get().getLastFourDigits());
    }

    @Test
    public void parsePaymentInfo_eWallet_success() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.of("EWALLET"), Optional.of("user@grab.com"), Optional.empty(), Optional.of("GrabPay"));
        assertTrue(result.isPresent());
        assertEquals(PaymentType.EWALLET, result.get().getType());
        assertEquals("GrabPay", result.get().getWalletProvider());
        assertEquals("user@grab.com", result.get().getWalletAccountId());
    }

    @Test
    public void parsePaymentInfo_unknownType_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePaymentInfo(
                        Optional.of("CRYPTO"), Optional.empty(), Optional.empty(), Optional.empty()));
    }

    @Test
    public void parsePaymentInfo_missingMethodWithRef_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePaymentInfo(
                        Optional.empty(), Optional.of("+6591234567"), Optional.empty(), Optional.empty()));
    }

    @Test
    public void parsePaymentInfo_payNowMissingRef_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePaymentInfo(
                        Optional.of("PAYNOW"), Optional.empty(), Optional.empty(), Optional.empty()));
    }

    @Test
    public void parsePaymentInfo_payNowInvalidPhoneFormat_throwsParseException() throws Exception {
        ParseException thrown = org.junit.jupiter.api.Assertions.assertThrows(ParseException.class, () ->
                ParserUtil.parsePaymentInfo(
                        Optional.of("PAYNOW"), Optional.of("+6591234567"), Optional.empty(), Optional.empty()));
        assertEquals(PaymentInfo.MESSAGE_INVALID_PAYNOW_PHONE, thrown.getMessage());
    }

    @Test
    public void parsePaymentInfo_cashWithRef_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePaymentInfo(
                        Optional.of("CASH"), Optional.of("extra"), Optional.empty(), Optional.empty()));
    }

    @Test
    public void parsePaymentInfo_payNowWithBankName_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePaymentInfo(
                        Optional.of("PAYNOW"), Optional.of("+6591234567"), Optional.of("DBS"), Optional.empty()));
    }

    @Test
    public void parsePaymentInfo_bankMissingBankName_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePaymentInfo(
                        Optional.of("BANK"), Optional.of("REF123"), Optional.empty(), Optional.empty()));
    }

    @Test
    public void parsePaymentInfo_eWalletMissingWalletProvider_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePaymentInfo(
                        Optional.of("EWALLET"), Optional.of("user@grab.com"), Optional.empty(), Optional.empty()));
    }

    @Test
    public void parsePaymentInfo_cardInvalidRef_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePaymentInfo(
                        Optional.of("CARD"), Optional.of("AB12"), Optional.empty(), Optional.empty()));
    }

    //---------------- Tests for parseAvailability ----------------------------------------

    @Test
    public void parseAvailability_true_returnsTrue() throws Exception {
        assertTrue(ParserUtil.parseAvailability("true"));
    }

    @Test
    public void parseAvailability_false_returnsFalse() throws Exception {
        assertFalse(ParserUtil.parseAvailability("false"));
    }

    @Test
    public void parseAvailability_mixedCaseAndWhitespace_returnsParsedValue() throws Exception {
        assertTrue(ParserUtil.parseAvailability("  TRUE  "));
        assertFalse(ParserUtil.parseAvailability("  False  "));
    }

    @Test
    public void parseAvailability_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAvailability("yes"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAvailability("1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAvailability(""));
    }

    //---------------- Tests for parseQuantity ----------------------------------------

    @Test
    public void parseQuantity_validValueOne_returnsQuantity() throws Exception {
        assertEquals(new Quantity(1), ParserUtil.parseQuantity("1"));
    }

    @Test
    public void parseQuantity_validValueMid_returnsQuantity() throws Exception {
        assertEquals(new Quantity(3), ParserUtil.parseQuantity("3"));
    }

    @Test
    public void parseQuantity_validValueMax_returnsQuantity() throws Exception {
        assertEquals(new Quantity(999), ParserUtil.parseQuantity("999"));
    }

    @Test
    public void parseQuantity_validValueWithWhitespace_returnsTrimmedQuantity() throws Exception {
        assertEquals(new Quantity(3), ParserUtil.parseQuantity(" 3 "));
    }

    @Test
    public void parseQuantity_zero_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("0"));
    }

    @Test
    public void parseQuantity_negative_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("-1"));
    }

    @Test
    public void parseQuantity_exceedsMax_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("1000"));
    }

    @Test
    public void parseQuantity_nonNumeric_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("abc"));
    }

    @Test
    public void parseQuantity_empty_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity(""));
    }

    @Test
    public void parseQuantity_decimal_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("1.5"));
    }
}
