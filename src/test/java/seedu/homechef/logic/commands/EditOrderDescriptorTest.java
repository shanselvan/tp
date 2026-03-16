package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_FOOD_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_FOOD_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.testutil.EditOrderDescriptorBuilder;

public class EditOrderDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditOrderDescriptor descriptorWithSameValues = new EditOrderDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditOrderDescriptor editedAmy = new EditOrderDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditOrderDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditOrderDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditOrderDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditOrderDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different food (both non-null) -> returns false
        EditOrderDescriptor amyWithFood = new EditOrderDescriptorBuilder(DESC_AMY).withFood(VALID_FOOD_AMY).build();
        editedAmy = new EditOrderDescriptorBuilder(DESC_AMY).withFood(VALID_FOOD_BOB).build();
        assertFalse(amyWithFood.equals(editedAmy));

        // same food (both non-null) -> returns true
        EditOrderDescriptor amyWithFoodCopy = new EditOrderDescriptorBuilder(DESC_AMY).withFood(VALID_FOOD_AMY).build();
        assertTrue(amyWithFood.equals(amyWithFoodCopy));

        // different date -> returns false
        editedAmy = new EditOrderDescriptorBuilder(DESC_AMY).withDate(VALID_DATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different paymentInfo -> returns false
        EditOrderDescriptor amyWithPayment = new EditOrderDescriptorBuilder(DESC_AMY)
                .withPaymentInfo(new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null)).build();
        EditOrderDescriptor amyWithDifferentPayment = new EditOrderDescriptorBuilder(DESC_AMY)
                .withPaymentInfo(new PaymentInfo(PaymentType.PAYNOW, "+65 91234567", null, null, null, null, null))
                .build();
        assertFalse(amyWithPayment.equals(amyWithDifferentPayment));

        // same paymentInfo -> returns true
        EditOrderDescriptor amyWithPaymentCopy = new EditOrderDescriptorBuilder(DESC_AMY)
                .withPaymentInfo(new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null)).build();
        assertTrue(amyWithPayment.equals(amyWithPaymentCopy));
    }

    @Test
    public void toStringMethod() {
        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();
        String expected = EditOrderDescriptor.class.getCanonicalName() + "{food="
                + editOrderDescriptor.getFood().orElse(null) + ", name="
                + editOrderDescriptor.getName().orElse(null) + ", phone="
                + editOrderDescriptor.getPhone().orElse(null) + ", email="
                + editOrderDescriptor.getEmail().orElse(null) + ", address="
                + editOrderDescriptor.getAddress().orElse(null) + ", date="
                + editOrderDescriptor.getDate().orElse(null) + ", dietTags="
                + editOrderDescriptor.getTags().orElse(null)
                + ", paymentInfo=" + editOrderDescriptor.getPaymentInfo().orElse(null) + "}";
        assertEquals(expected, editOrderDescriptor.toString());
    }
}
