package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.homechef.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.testutil.EditOrderDescriptorBuilder;
import seedu.homechef.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalHomeChef(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Order editedOrder = new OrderBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder));

        Model expectedModel = new ModelManager(new HomeChef(model.getHomeChef()), new UserPrefs());
        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder));

        Model expectedModel = new ModelManager(new HomeChef(model.getHomeChef()), new UserPrefs());
        expectedModel.setOrder(lastOrder, editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, new EditOrderDescriptor());
        Order editedOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder));

        Model expectedModel = new ModelManager(new HomeChef(model.getHomeChef()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder(orderInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER,
                new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder));

        Model expectedModel = new ModelManager(new HomeChef(model.getHomeChef()), new UserPrefs());
        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateOrderUnfilteredList_failure() {
        Order firstOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(firstOrder).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ORDER, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_duplicateOrderFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        // edit order in filtered list into a duplicate in HomeChef
        Order orderInList = model.getHomeChef().getOrderList().get(INDEX_SECOND_ORDER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER,
                new EditOrderDescriptorBuilder(orderInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of HomeChef
     */
    @Test
    public void execute_invalidOrderIndexFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of HomeChef list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHomeChef().getOrderList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ORDER, DESC_AMY);

        // same values -> returns true
        EditOrderDescriptor copyDescriptor = new EditOrderDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ORDER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ORDER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ORDER, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();
        EditCommand editCommand = new EditCommand(index, editOrderDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editOrderDescriptor="
                + editOrderDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

    @Test
    public void execute_paymentInfoSet_success() {
        Model model = new ModelManager(getTypicalHomeChef(), new UserPrefs());
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        PaymentInfo payNow = new PaymentInfo(
                PaymentType.PAYNOW, "+65 91234567", null, null, null, null, null);

        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(payNow).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, descriptor);

        Order expectedOrder = new OrderBuilder(orderToEdit).withPaymentInfo(payNow).build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(expectedOrder));

        Model expectedModel = new ModelManager(new HomeChef(model.getHomeChef()), new UserPrefs());
        expectedModel.setOrder(orderToEdit, expectedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_paymentInfoTypeSwitch_success() {
        Model model = new ModelManager(getTypicalHomeChef(), new UserPrefs());
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        PaymentInfo payNow = new PaymentInfo(
                PaymentType.PAYNOW, "+65 91234567", null, null, null, null, null);
        Order orderWithPayNow = new OrderBuilder(orderToEdit).withPaymentInfo(payNow).build();
        model.setOrder(orderToEdit, orderWithPayNow);

        PaymentInfo cash = new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null);
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(cash).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, descriptor);

        Order expectedOrder = new OrderBuilder(orderWithPayNow).withPaymentInfo(cash).build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(expectedOrder));
        Model expectedModel = new ModelManager(new HomeChef(model.getHomeChef()), new UserPrefs());
        expectedModel.setOrder(orderWithPayNow, expectedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        Order result = expectedModel.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        assertEquals(PaymentType.CASH, result.getPaymentInfo().get().getType());
        assertNull(result.getPaymentInfo().get().getHandle());
    }

    @Test
    public void editDescriptor_paymentInfoOnly_isAnyFieldEdited() {
        PaymentInfo cash = new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null);
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(cash).build();
        assertTrue(descriptor.isAnyFieldEdited());
    }

}
