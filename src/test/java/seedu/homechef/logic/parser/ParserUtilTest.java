package seedu.homechef.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_BANK_PAYMENT_REQUIRED;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_CASH_PAYMENT_REQUIRED;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_MULTIPLE_PAYMENT_PREFIXES;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_PAYNOW_PAYMENT_REQUIRED;
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
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.BankPayment;
import seedu.homechef.model.order.CashPayment;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.DietTag;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.PayNowPayment;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Quantity;

public class ParserUtilTest {
    private static final String INVALID_CUSTOMER = "R#chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PRICE = "05.50";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_CUSTOMER = "Rachel Walker";
    private static final String VALID_CUSTOMER_WITH_PUNCTUATION = "Anne-Marie O'Neil/Lee @ Home";
    private static final String VALID_CUSTOMER_WITH_UNICODE = "李雷";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_PRICE = "10.50";
    private static final String VALID_PRICE_NO_DECIMAL = "10";
    private static final String VALID_ZERO_PRICE = "0.00";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseIndex(String.valueOf(Integer.MAX_VALUE) + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        assertEquals(INDEX_FIRST_ORDER, ParserUtil.parseIndex("1"));
        assertEquals(INDEX_FIRST_ORDER, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseCustomer_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCustomer(null));
    }

    @Test
    public void parseCustomer_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCustomer(INVALID_CUSTOMER));
    }

    @Test
    public void parseCustomer_validValues() throws Exception {
        Customer expectedCustomer = new Customer(VALID_CUSTOMER);
        assertEquals(expectedCustomer, ParserUtil.parseCustomer(VALID_CUSTOMER));
        assertEquals(expectedCustomer, ParserUtil.parseCustomer(WHITESPACE + VALID_CUSTOMER + WHITESPACE));

        Customer expectedCustomerWithPunctuation = new Customer(VALID_CUSTOMER_WITH_PUNCTUATION);
        assertEquals(expectedCustomerWithPunctuation,
                ParserUtil.parseCustomer(WHITESPACE + VALID_CUSTOMER_WITH_PUNCTUATION + WHITESPACE));

        Customer expectedUnicodeCustomer = new Customer(VALID_CUSTOMER_WITH_UNICODE);
        assertEquals(expectedUnicodeCustomer,
                ParserUtil.parseCustomer(WHITESPACE + VALID_CUSTOMER_WITH_UNICODE + WHITESPACE));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValues() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
        assertEquals(expectedPhone, ParserUtil.parsePhone(WHITESPACE + VALID_PHONE + WHITESPACE));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress(null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValues() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
        assertEquals(expectedAddress, ParserUtil.parseAddress(WHITESPACE + VALID_ADDRESS + WHITESPACE));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValues() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
        assertEquals(expectedEmail, ParserUtil.parseEmail(WHITESPACE + VALID_EMAIL + WHITESPACE));
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
    public void parseTag_validValues() throws Exception {
        DietTag expectedDietTag = new DietTag(VALID_TAG_1);
        assertEquals(expectedDietTag, ParserUtil.parseTag(VALID_TAG_1));
        assertEquals(expectedDietTag, ParserUtil.parseTag(WHITESPACE + VALID_TAG_1 + WHITESPACE));
    }

    @Test
    public void parseTags_cases() throws Exception {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());

        Set<DietTag> actualDietTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<DietTag> expectedDietTagSet = new HashSet<>(
                Arrays.asList(new DietTag(VALID_TAG_1), new DietTag(VALID_TAG_2)));
        assertEquals(expectedDietTagSet, actualDietTagSet);
    }

    @Test
    public void parsePrice_cases() throws Exception {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrice(null));
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice(INVALID_PRICE));
        assertEquals(new Price(VALID_PRICE), ParserUtil.parsePrice(VALID_PRICE));
        assertEquals(new Price(VALID_PRICE), ParserUtil.parsePrice(WHITESPACE + VALID_PRICE + WHITESPACE));
        assertEquals(new Price(VALID_PRICE_NO_DECIMAL), ParserUtil.parsePrice(VALID_PRICE_NO_DECIMAL));
        assertEquals(new Price(VALID_ZERO_PRICE), ParserUtil.parsePrice(VALID_ZERO_PRICE));
    }

    @Test
    public void parseMenuNameAndPrice_normalized() throws Exception {
        assertEquals(new Food("Chicken Rice"), ParserUtil.parseFood(WHITESPACE + "Chicken Rice" + WHITESPACE));
        assertEquals(new Food("Chef's Fish/Chips"),
                ParserUtil.parseFood(WHITESPACE + "Chef's Fish/Chips" + WHITESPACE));
        assertEquals(new Food("Chef\u2019s Fish/Chips"),
                ParserUtil.parseFood(WHITESPACE + "Chef\u2019s Fish/Chips" + WHITESPACE));
        assertEquals(new Food("Fish & Chips"), ParserUtil.parseFood(WHITESPACE + "Fish & Chips" + WHITESPACE));
        assertEquals(new Food("No. 1 Curry + Rice"),
                ParserUtil.parseFood(WHITESPACE + "No. 1 Curry + Rice" + WHITESPACE));
        assertEquals(new Food("Nasi @ Home"), ParserUtil.parseFood(WHITESPACE + "Nasi @ Home" + WHITESPACE));
        assertEquals(new Food("Ramen 日本"), ParserUtil.parseFood(WHITESPACE + "Ramen 日本" + WHITESPACE));
        assertEquals("0.00", ParserUtil.parseMenuPrice("0").toString());
        assertEquals("5.50", ParserUtil.parseMenuPrice("5.5").toString());
        assertEquals("12.00", ParserUtil.parseMenuPrice("12").toString());
    }

    @Test
    public void parsePaymentInfo_noFields_returnsEmpty() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.empty(), Optional.empty(), Optional.empty());
        assertTrue(result.isEmpty());
    }

    @Test
    public void parsePaymentInfo_cash_success() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.empty(), Optional.empty(), Optional.of("yes"));
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof CashPayment);
    }

    @Test
    public void parsePaymentInfo_cashNo_success() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.empty(), Optional.empty(), Optional.of("no"));
        assertTrue(result.isEmpty());
    }

    @Test
    public void parsePaymentInfo_payNow_success() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.empty(), Optional.of("+65 91234567"), Optional.empty());
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof PayNowPayment);
        assertEquals("+65 91234567", result.get().getReference());
    }

    @Test
    public void parsePaymentInfo_bank_success() throws Exception {
        Optional<PaymentInfo> result = ParserUtil.parsePaymentInfo(
                Optional.of("REF123"), Optional.empty(), Optional.empty());
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof BankPayment);
        assertEquals("REF123", result.get().getReference());
    }

    @Test
    public void parsePaymentInfo_normalization_success() throws Exception {
        Optional<PaymentInfo> paynow = ParserUtil.parsePaymentInfo(
                Optional.empty(), Optional.of("  +65   91234567  "), Optional.empty());
        assertTrue(paynow.isPresent());
        assertEquals("+65 91234567", paynow.get().getReference());

        Optional<PaymentInfo> bank = ParserUtil.parsePaymentInfo(
                Optional.of("  DBS   REF   001  "), Optional.empty(), Optional.empty());
        assertEquals("DBS REF 001", bank.get().getReference());
    }

    @Test
    public void parsePaymentInfo_failures() {
        assertThrows(ParseException.class, MESSAGE_MULTIPLE_PAYMENT_PREFIXES, () ->
                ParserUtil.parsePaymentInfo(Optional.of("REF123"), Optional.of("+65 91234567"), Optional.empty()));
        assertThrows(ParseException.class, MESSAGE_CASH_PAYMENT_REQUIRED, () ->
                ParserUtil.parsePaymentInfo(Optional.empty(), Optional.empty(), Optional.of("value")));
        assertThrows(ParseException.class, MESSAGE_CASH_PAYMENT_REQUIRED, () ->
                ParserUtil.parsePaymentInfo(Optional.empty(), Optional.empty(), Optional.of("  ")));
        assertThrows(ParseException.class, MESSAGE_PAYNOW_PAYMENT_REQUIRED, () ->
                ParserUtil.parsePaymentInfo(Optional.empty(), Optional.of("  "), Optional.empty()));
        assertThrows(ParseException.class, MESSAGE_BANK_PAYMENT_REQUIRED, () ->
                ParserUtil.parsePaymentInfo(Optional.of(""), Optional.empty(), Optional.empty()));
        assertThrows(ParseException.class, MESSAGE_BANK_PAYMENT_REQUIRED, () ->
                ParserUtil.parsePaymentInfo(Optional.of("!!!@@@"), Optional.empty(), Optional.empty()));
    }

    @Test
    public void parseAvailability_cases() throws Exception {
        assertEquals(Availability.YES, ParserUtil.parseAvailability("yes"));
        assertEquals(Availability.NO, ParserUtil.parseAvailability("no"));
        assertEquals(Availability.YES, ParserUtil.parseAvailability("  yEs  "));
        assertEquals(Availability.NO, ParserUtil.parseAvailability("  nO  "));
        assertThrows(ParseException.class, () -> ParserUtil.parseAvailability("true"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAvailability("false"));
    }

    @Test
    public void parseQuantity_cases() throws Exception {
        assertEquals(new Quantity(1), ParserUtil.parseQuantity("1"));
        assertEquals(new Quantity(3), ParserUtil.parseQuantity(" 3 "));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("1000"));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("abc"));
    }

    @Test
    public void parseDate_cases() throws Exception {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
        assertThrows(ParseException.class, Date.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseDate("31-02-2026"));
        assertThrows(ParseException.class, Date.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseDate("29-02-2025"));

        assertEquals(new Date("29-02-2024"), ParserUtil.parseDate("29-02-2024"));
        assertEquals(new Date("01-03-2026"), ParserUtil.parseDate(WHITESPACE + "01-03-2026" + WHITESPACE));
    }
}
