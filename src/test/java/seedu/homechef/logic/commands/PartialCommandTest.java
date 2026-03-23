package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.homechef.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.Messages;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentStatus;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code PartialCommand}.
 */
public class PartialCommandTest {

    private Model model = new ModelManager(getTypicalHomeChef(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        PartialCommand partialCommand = new PartialCommand(INDEX_FIRST_ORDER);

        PaymentStatus partialStatus = PaymentStatus.PARTIAL;
        Order editedOrder = new Order(
                orderToEdit.getFood(), orderToEdit.getCustomer(), orderToEdit.getPhone(),
                orderToEdit.getEmail(), orderToEdit.getAddress(), orderToEdit.getDate(),
                orderToEdit.getCompletionStatus(), partialStatus, orderToEdit.getTags());

        String expectedMessage = String.format(PartialCommand.MESSAGE_MARK_PARTIAL_SUCCESS,
                Messages.format(editedOrder));

        ModelManager expectedModel = new ModelManager(model.getHomeChef(), new UserPrefs());
        expectedModel.setOrder(orderToEdit, editedOrder);

        assertCommandSuccess(partialCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        PartialCommand partialCommand = new PartialCommand(outOfBoundIndex);

        assertCommandFailure(partialCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        PartialCommand partialCommand = new PartialCommand(INDEX_FIRST_ORDER);

        PaymentStatus partialStatus = PaymentStatus.PARTIAL;
        Order editedOrder = new Order(
                orderToEdit.getFood(), orderToEdit.getCustomer(), orderToEdit.getPhone(),
                orderToEdit.getEmail(), orderToEdit.getAddress(), orderToEdit.getDate(),
                orderToEdit.getCompletionStatus(), partialStatus, orderToEdit.getTags());

        String expectedMessage = String.format(PartialCommand.MESSAGE_MARK_PARTIAL_SUCCESS,
                Messages.format(editedOrder));

        Model expectedModel = new ModelManager(model.getHomeChef(), new UserPrefs());
        expectedModel.setOrder(orderToEdit, editedOrder);

        assertCommandSuccess(partialCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHomeChef().getOrderList().size());

        PartialCommand partialCommand = new PartialCommand(outOfBoundIndex);

        assertCommandFailure(partialCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        PartialCommand partialFirstCommand = new PartialCommand(INDEX_FIRST_ORDER);
        PartialCommand partialSecondCommand = new PartialCommand(INDEX_SECOND_ORDER);

        // same object -> true
        assertTrue(partialFirstCommand.equals(partialFirstCommand));

        // same values -> true
        PartialCommand partialFirstCommandCopy = new PartialCommand(INDEX_FIRST_ORDER);
        assertTrue(partialFirstCommand.equals(partialFirstCommandCopy));

        // different types -> false
        assertFalse(partialFirstCommand.equals(1));

        // null -> false
        assertFalse(partialFirstCommand.equals(null));

        // different index -> false
        assertFalse(partialFirstCommand.equals(partialSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        PartialCommand partialCommand = new PartialCommand(targetIndex);
        String expected = PartialCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, partialCommand.toString());
    }
}
