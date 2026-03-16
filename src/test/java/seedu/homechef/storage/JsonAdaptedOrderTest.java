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
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.Name;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.model.order.Phone;
import seedu.homechef.testutil.OrderBuilder;

public class JsonAdaptedOrderTest {
    private static final String INVALID_DISH = "Sp@ghetti";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DATE = "2020-13-01";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PAYMENT_TYPE = "CRYPTO";

    private static final String VALID_DISH = BENSON.getFood().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_DATE = BENSON.getDate().toString();
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
                new JsonAdaptedOrder(VALID_DISH, INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                        VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_DISH, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_DATE, VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_DISH, VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                        VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_DISH, VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_DATE, VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_DISH, VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                        VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_DISH, VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_DATE, VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_DISH, VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_DATE,
                        VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_DISH, VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_DATE, VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_DISH, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE,
                        invalidTags, null, null, null, null, null, null, null);
        assertThrows(IllegalValueException.class, order::toModelType);
    }

    @Test
    public void toModelType_invalidDish_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(INVALID_DISH, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DATE, VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = Food.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullDish_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(null, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DATE, VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Food.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_DISH, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, INVALID_DATE, VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_DISH, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
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
        PaymentInfo payNow = new PaymentInfo(
                PaymentType.PAYNOW, "+65 91234567", null, null, null, null, null);
        Order orderWithPayment = new OrderBuilder(BENSON).withPaymentInfo(payNow).build();
        JsonAdaptedOrder adapted = new JsonAdaptedOrder(orderWithPayment);
        Order result = adapted.toModelType();
        assertEquals(orderWithPayment, result);
        assertTrue(result.getPaymentInfo().isPresent());
        assertEquals(PaymentType.PAYNOW, result.getPaymentInfo().get().getType());
        assertEquals("+65 91234567", result.getPaymentInfo().get().getHandle());
    }

    @Test
    public void toModelType_validBankPayment_roundTrips() throws Exception {
        PaymentInfo bank = new PaymentInfo(PaymentType.BANK, null, "DBS", "REF123", null, null, null);
        Order orderWithPayment = new OrderBuilder(BENSON).withPaymentInfo(bank).build();
        JsonAdaptedOrder adapted = new JsonAdaptedOrder(orderWithPayment);
        assertEquals(orderWithPayment, adapted.toModelType());
    }

    @Test
    public void toModelType_validCardPayment_roundTrips() throws Exception {
        PaymentInfo card = new PaymentInfo(PaymentType.CARD, null, null, null, "4321", null, null);
        Order orderWithPayment = new OrderBuilder(BENSON).withPaymentInfo(card).build();
        JsonAdaptedOrder adapted = new JsonAdaptedOrder(orderWithPayment);
        assertEquals(orderWithPayment, adapted.toModelType());
    }

    @Test
    public void toModelType_validEWalletPayment_roundTrips() throws Exception {
        PaymentInfo eWallet = new PaymentInfo(
                PaymentType.EWALLET, null, null, null, null, "GrabPay", "user@grab.com");
        Order orderWithPayment = new OrderBuilder(BENSON).withPaymentInfo(eWallet).build();
        JsonAdaptedOrder adapted = new JsonAdaptedOrder(orderWithPayment);
        assertEquals(orderWithPayment, adapted.toModelType());
    }

    @Test
    public void toModelType_validCashPayment_roundTrips() throws Exception {
        PaymentInfo cash = new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null);
        Order orderWithPayment = new OrderBuilder(BENSON).withPaymentInfo(cash).build();
        JsonAdaptedOrder adapted = new JsonAdaptedOrder(orderWithPayment);
        assertEquals(orderWithPayment, adapted.toModelType());
    }

    @Test
    public void toModelType_corruptedPaymentType_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_DISH, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE, VALID_TAGS,
                INVALID_PAYMENT_TYPE, null, null, null, null, null, null);
        assertThrows(IllegalValueException.class, order::toModelType);
    }

}
