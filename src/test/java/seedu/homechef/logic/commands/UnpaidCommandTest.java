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
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentStatus;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnpaidCommand}.
 */
public class UnpaidCommandTest {

    private Model model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        UnpaidCommand unpaidCommand = new UnpaidCommand(INDEX_FIRST_ORDER);

        PaymentStatus unpaidStatus = new PaymentStatus(PaymentStatus.IS_UNPAID);
        Order editedOrder = new Order(
                orderToEdit.getFood(), orderToEdit.getCustomer(), orderToEdit.getPhone(),
                orderToEdit.getEmail(), orderToEdit.getAddress(), orderToEdit.getDate(),
                orderToEdit.getCompletionStatus(), unpaidStatus, orderToEdit.getTags());

        String expectedMessage = String.format(UnpaidCommand.MESSAGE_MARK_UNPAID_SUCCESS,
                Messages.format(editedOrder));

        ModelManager expectedModel = new ModelManager(model.getHomeChef(), new MenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToEdit, editedOrder);

        assertCommandSuccess(unpaidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        UnpaidCommand unpaidCommand = new UnpaidCommand(outOfBoundIndex);

        assertCommandFailure(unpaidCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        UnpaidCommand unpaidCommand = new UnpaidCommand(INDEX_FIRST_ORDER);

        PaymentStatus unpaidStatus = new PaymentStatus(PaymentStatus.IS_UNPAID);
        Order editedOrder = new Order(
                orderToEdit.getFood(), orderToEdit.getCustomer(), orderToEdit.getPhone(),
                orderToEdit.getEmail(), orderToEdit.getAddress(), orderToEdit.getDate(),
                orderToEdit.getCompletionStatus(), unpaidStatus, orderToEdit.getTags());

        String expectedMessage = String.format(UnpaidCommand.MESSAGE_MARK_UNPAID_SUCCESS,
                Messages.format(editedOrder));

        Model expectedModel = new ModelManager(model.getHomeChef(), new MenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToEdit, editedOrder);

        assertCommandSuccess(unpaidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHomeChef().getOrderList().size());

        UnpaidCommand unpaidCommand = new UnpaidCommand(outOfBoundIndex);

        assertCommandFailure(unpaidCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnpaidCommand unpaidFirstCommand = new UnpaidCommand(INDEX_FIRST_ORDER);
        UnpaidCommand unpaidSecondCommand = new UnpaidCommand(INDEX_SECOND_ORDER);

        // same object -> true
        assertTrue(unpaidFirstCommand.equals(unpaidFirstCommand));

        // same values -> true
        UnpaidCommand unpaidFirstCommandCopy = new UnpaidCommand(INDEX_FIRST_ORDER);
        assertTrue(unpaidFirstCommand.equals(unpaidFirstCommandCopy));

        // different types -> false
        assertFalse(unpaidFirstCommand.equals(1));

        // null -> false
        assertFalse(unpaidFirstCommand.equals(null));

        // different index -> false
        assertFalse(unpaidFirstCommand.equals(unpaidSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnpaidCommand unpaidCommand = new UnpaidCommand(targetIndex);
        String expected = UnpaidCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unpaidCommand.toString());
    }
}
