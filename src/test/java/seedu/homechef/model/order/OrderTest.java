package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_CUSTOMER_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_FOOD_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalOrders.ALICE;
import static seedu.homechef.testutil.TypicalOrders.BOB;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.homechef.testutil.OrderBuilder;

public class OrderTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Order order = new OrderBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> order.getTags().remove(0));
    }

    @Test
    public void isSameOrder() {
        // EP: same customers
        // same object -> returns true
        assertTrue(ALICE.isSameOrder(ALICE));

        // same Customer, food and date; other attributes different -> returns true
        Order editedAlice = new OrderBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameOrder(editedAlice));

        // EP: different customers
        // null -> returns false
        assertFalse(ALICE.isSameOrder(null));

        // different Customer, all other attributes same -> returns false
        editedAlice = new OrderBuilder(ALICE).withCustomer(VALID_CUSTOMER_BOB).build();
        assertFalse(ALICE.isSameOrder(editedAlice));

        // Customer differs in case, all other attributes same -> returns false
        Order editedBob = new OrderBuilder(BOB).withCustomer(VALID_CUSTOMER_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameOrder(editedBob));

        // Customer has trailing spaces, all other attributes same -> returns false
        String customerWithTrailingSpaces = VALID_CUSTOMER_BOB + " ";
        editedBob = new OrderBuilder(BOB).withCustomer(customerWithTrailingSpaces).build();
        assertFalse(BOB.isSameOrder(editedBob));

        // different food, same customer and date -> returns false
        editedAlice = new OrderBuilder(ALICE).withFood(VALID_FOOD_BOB).build();
        assertFalse(ALICE.isSameOrder(editedAlice));

        // same customer and food, different date -> returns false
        editedAlice = new OrderBuilder(ALICE).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE.isSameOrder(editedAlice));
    }

    @Test
    public void equals() {
        // EP: same values -> returns true
        Order aliceCopy = new OrderBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // EP: same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // EP: null -> returns false
        assertFalse(ALICE.equals(null));

        // EP: different type -> returns false
        assertFalse(ALICE.equals(5));

        // EP: different order -> returns false
        assertFalse(ALICE.equals(BOB));

        // EP: different food -> returns false
        Order editedAlice = new OrderBuilder(ALICE).withFood(VALID_CUSTOMER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different name -> returns false
        editedAlice = new OrderBuilder(ALICE).withCustomer(VALID_CUSTOMER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different phone -> returns false
        editedAlice = new OrderBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different email -> returns false
        editedAlice = new OrderBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different address -> returns false
        editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different date -> returns false
        editedAlice = new OrderBuilder(ALICE).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different tags -> returns false
        editedAlice = new OrderBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Order.class.getCanonicalName() + "{food=" + ALICE.getFood()
                + ", customer=" + ALICE.getCustomer()
                + ", phone=" + ALICE.getPhone() + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", date=" + ALICE.getDate() + ", price=" + ALICE.getPrice()
                + ", completionStatus=" + ALICE.getCompletionStatus()
                + ", paymentStatus=" + ALICE.getPaymentStatus() + ", dietTags=" + ALICE.getTags()
                + ", paymentInfo=" + ALICE.getPaymentInfo() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void getPaymentInfo_noPaymentInfo_returnsEmpty() {
        Order order = new OrderBuilder().build();
        assertTrue(order.getPaymentInfo().isEmpty());
    }

    @Test
    public void getPaymentInfo_withPaymentInfo_returnsPaymentInfo() {
        PaymentInfo payNow = new PaymentInfo(PaymentType.PAYNOW, "+65 91234567",
                null, null, null, null, null);
        Order order = new OrderBuilder().withPaymentInfo(payNow).build();
        assertEquals(Optional.of(payNow), order.getPaymentInfo());
    }

    @Test
    public void equals_sameFieldsDifferentPaymentInfo_notEqual() {
        Order orderWithout = new OrderBuilder().build();
        PaymentInfo cash = new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null);
        Order orderWith = new OrderBuilder().withPaymentInfo(cash).build();
        assertFalse(orderWithout.equals(orderWith));
    }
}
