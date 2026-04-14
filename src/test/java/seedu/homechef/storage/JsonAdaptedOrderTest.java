package seedu.homechef.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.storage.JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalOrders.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.BankPayment;
import seedu.homechef.model.order.CashPayment;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PayNowPayment;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentStatus;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Quantity;
import seedu.homechef.testutil.OrderBuilder;

public class JsonAdaptedOrderTest {
    private static final String INVALID_FOOD = "Spa#ghetti";
    private static final String INVALID_CUSTOMER = "R#chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DATE = "2020-13-01";
    private static final String INVALID_COMPLETION_STATUS = "Not in progress";
    private static final String INVALID_PAYMENT_STATUS = "Not a payment";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PRICE = "05.20";
    private static final String INVALID_PAYMENT_METHOD = "CRYPTO";

    private static final String VALID_FOOD = BENSON.getFood().toString();
    private static final String VALID_CUSTOMER = BENSON.getCustomer().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_DATE = BENSON.getDate().toString();
    private static final String VALID_COMPLETION_STATUS = BENSON.getCompletionStatus().toString();
    private static final String VALID_PAYMENT_STATUS = BENSON.getPaymentStatus().toString();
    private static final String VALID_PRICE = BENSON.getPrice().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(BENSON);
        assertEquals(BENSON, order.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_FOOD, INVALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                        VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                        null, null, null);
        String expectedMessage = Customer.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_DATE, VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Customer.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                        VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                        null, null, null);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_DATE, VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                        VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                        null, null, null);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, null, VALID_ADDRESS,
                VALID_DATE, VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_DATE,
                        VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                        null, null, null);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, null,
                VALID_DATE, VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                        VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, invalidTags,
                        null, null, null);
        assertThrows(IllegalValueException.class, order::toModelType);
    }

    @Test
    public void toModelType_nullTagElement_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(null);
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                        VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, invalidTags,
                        null, null, null);
        assertThrows(IllegalValueException.class, order::toModelType);
    }

    @Test
    public void toModelType_nullTagName_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag((String) null));
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                        VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, invalidTags,
                        null, null, null);
        assertThrows(IllegalValueException.class, order::toModelType);
    }

    @Test
    public void toModelType_invalidDish_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(INVALID_FOOD, VALID_CUSTOMER, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DATE, VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS,
                VALID_TAGS, null, null, null);
        String expectedMessage = Food.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullDish_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(null, VALID_CUSTOMER, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DATE, VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS,
                VALID_TAGS, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Food.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, INVALID_DATE, VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS,
                VALID_TAGS, null, null, null);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, null, VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS,
                VALID_TAGS, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidCompletionStatus_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DATE, VALID_PRICE, INVALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS,
                VALID_TAGS, null, null, null);
        String expectedMessage = CompletionStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }


    @Test
    public void toModelType_nullCompletionStatus_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DATE, VALID_PRICE, null, VALID_PAYMENT_STATUS,
                VALID_TAGS, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CompletionStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidPaymentStatus_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DATE, VALID_PRICE, VALID_COMPLETION_STATUS, INVALID_PAYMENT_STATUS,
                VALID_TAGS, null, null, null);
        String expectedMessage = PaymentStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPaymentStatus_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DATE, VALID_PRICE, VALID_COMPLETION_STATUS, null,
                VALID_TAGS, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PaymentStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_absentPaymentInfo_returnsOrderWithoutPayment() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(BENSON);
        Order result = order.toModelType();
        assertTrue(result.getPaymentInfo().isEmpty());
    }

    @Test
    public void toModelType_validPayNowPayment_roundTrips() throws Exception {
        PaymentInfo payNow = new PayNowPayment("+65 91234567");
        Order orderWithPayment = new OrderBuilder(BENSON).withPaymentInfo(payNow).build();
        JsonAdaptedOrder adapted = new JsonAdaptedOrder(orderWithPayment);
        Order result = adapted.toModelType();
        assertEquals(orderWithPayment, result);
        assertTrue(result.getPaymentInfo().isPresent());
        assertTrue(result.getPaymentInfo().get() instanceof PayNowPayment);
        assertEquals("+65 91234567", result.getPaymentInfo().get().getReference());
    }

    @Test
    public void toModelType_validBankPayment_roundTrips() throws Exception {
        PaymentInfo bank = new BankPayment("REF123");
        Order orderWithPayment = new OrderBuilder(BENSON).withPaymentInfo(bank).build();
        JsonAdaptedOrder adapted = new JsonAdaptedOrder(orderWithPayment);
        assertEquals(orderWithPayment, adapted.toModelType());
    }

    @Test
    public void toModelType_validCashPayment_roundTrips() throws Exception {
        PaymentInfo cash = new CashPayment();
        Order orderWithPayment = new OrderBuilder(BENSON).withPaymentInfo(cash).build();
        JsonAdaptedOrder adapted = new JsonAdaptedOrder(orderWithPayment);
        assertEquals(orderWithPayment, adapted.toModelType());
    }

    @Test
    public void toModelType_corruptedPaymentMethod_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                INVALID_PAYMENT_METHOD, null, null);
        assertThrows(IllegalValueException.class, order::toModelType);
    }

    @Test
    public void toModelType_blankPayNowDetails_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                "PAYNOW", "   ", null);
        assertThrows(IllegalValueException.class, PayNowPayment.MESSAGE_INVALID_REFERENCE, order::toModelType);
    }

    @Test
    public void toModelType_missingBankDetails_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                "BANK", null, null);
        assertThrows(IllegalValueException.class, BankPayment.MESSAGE_CONSTRAINTS, order::toModelType);
    }

    @Test
    public void toModelType_cashWithDetails_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                VALID_PRICE, VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                "CASH", "unexpected", null);
        assertThrows(IllegalValueException.class, "Cash payment must not contain payment details.", order::toModelType);
    }

    @Test
    public void toModelType_withQuantity_roundTrips() throws Exception {
        Order orderWithQuantity = new OrderBuilder(BENSON).withQuantity(3).build();
        JsonAdaptedOrder adapted = new JsonAdaptedOrder(orderWithQuantity);
        Order result = adapted.toModelType();
        assertEquals(new Quantity(3), result.getQuantity());
    }

    @Test
    public void toModelType_zeroPrice_roundTrips() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_FOOD, VALID_CUSTOMER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                "0.00", VALID_COMPLETION_STATUS, VALID_PAYMENT_STATUS, VALID_TAGS,
                null, null, BENSON.getQuantity().toString());
        Order result = order.toModelType();
        assertEquals("0.00", result.getPrice().toString());
    }

    @Test
    public void toModelType_invalidCalendarDate_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_FOOD, VALID_CUSTOMER, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, "31-02-2026", VALID_PRICE, VALID_COMPLETION_STATUS,
                VALID_PAYMENT_STATUS, VALID_TAGS, null, null, null);
        assertThrows(IllegalValueException.class, Date.MESSAGE_CONSTRAINTS, order::toModelType);
    }

}
