package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_FOOD_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_NAME_BOB;
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
        // same object -> returns true
        assertTrue(ALICE.isSameOrder(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameOrder(null));

        // same name, food and date; other attributes different -> returns true
        Order editedAlice = new OrderBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameOrder(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new OrderBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameOrder(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Order editedBob = new OrderBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameOrder(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new OrderBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameOrder(editedBob));

        // different food, same name and date -> returns false
        editedAlice = new OrderBuilder(ALICE).withFood(VALID_FOOD_BOB).build();
        assertFalse(ALICE.isSameOrder(editedAlice));

        // same name and food, different date -> returns false
        editedAlice = new OrderBuilder(ALICE).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE.isSameOrder(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Order aliceCopy = new OrderBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different order -> returns false
        assertFalse(ALICE.equals(BOB));

        // different food -> returns false
        Order editedAlice = new OrderBuilder(ALICE).withFood(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different name -> returns false
        editedAlice = new OrderBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new OrderBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new OrderBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different date -> returns false
        editedAlice = new OrderBuilder(ALICE).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new OrderBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Order.class.getCanonicalName() + "{food=" + ALICE.getFood() + ", name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone() + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", date=" + ALICE.getDate() + ", dietTags=" + ALICE.getTags()
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
