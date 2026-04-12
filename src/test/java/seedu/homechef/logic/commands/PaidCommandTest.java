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
 * {@code PaidCommand}.
 */
public class PaidCommandTest {

    private Model model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_SECOND_ORDER.getZeroBased());
        PaidCommand paidCommand = new PaidCommand(INDEX_SECOND_ORDER);

        PaymentStatus paidStatus = PaymentStatus.PAID;
        Order editedOrder = new Order(
                orderToEdit.getFood(), orderToEdit.getCustomer(), orderToEdit.getPhone(),
                orderToEdit.getEmail(), orderToEdit.getAddress(), orderToEdit.getDate(),
                orderToEdit.getCompletionStatus(), paidStatus, orderToEdit.getTags(),
                orderToEdit.getQuantity(), orderToEdit.getPrice(), orderToEdit.getPaymentInfo());

        String expectedMessage = String.format(PaidCommand.MESSAGE_MARK_PAID_SUCCESS,
                Messages.format(editedOrder));

        ModelManager expectedModel = new ModelManager(model.getHomeChef(), new MenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToEdit, editedOrder);

        assertCommandSuccess(paidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        PaidCommand paidCommand = new PaidCommand(outOfBoundIndex);

        assertCommandFailure(paidCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOrderAtIndex(model, INDEX_SECOND_ORDER);

        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        PaidCommand paidCommand = new PaidCommand(INDEX_FIRST_ORDER);

        PaymentStatus paidStatus = PaymentStatus.PAID;
        Order editedOrder = new Order(
                orderToEdit.getFood(), orderToEdit.getCustomer(), orderToEdit.getPhone(),
                orderToEdit.getEmail(), orderToEdit.getAddress(), orderToEdit.getDate(),
                orderToEdit.getCompletionStatus(), paidStatus, orderToEdit.getTags(),
                orderToEdit.getQuantity(), orderToEdit.getPrice(), orderToEdit.getPaymentInfo());

        String expectedMessage = String.format(PaidCommand.MESSAGE_MARK_PAID_SUCCESS,
                Messages.format(editedOrder));

        Model expectedModel = new ModelManager(model.getHomeChef(), new MenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToEdit, editedOrder);

        assertCommandSuccess(paidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHomeChef().getOrderList().size());

        PaidCommand paidCommand = new PaidCommand(outOfBoundIndex);

        assertCommandFailure(paidCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        PaidCommand paidFirstCommand = new PaidCommand(INDEX_FIRST_ORDER);
        PaidCommand paidSecondCommand = new PaidCommand(INDEX_SECOND_ORDER);

        // EP: same object -> true
        assertTrue(paidFirstCommand.equals(paidFirstCommand));

        // EP: same values -> true
        PaidCommand paidFirstCommandCopy = new PaidCommand(INDEX_FIRST_ORDER);
        assertTrue(paidFirstCommand.equals(paidFirstCommandCopy));

        // EP: different types -> false
        assertFalse(paidFirstCommand.equals(1));

        // EP: null -> false
        assertFalse(paidFirstCommand.equals(null));

        // EP: different index -> false
        assertFalse(paidFirstCommand.equals(paidSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        PaidCommand paidCommand = new PaidCommand(targetIndex);
        String expected = PaidCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, paidCommand.toString());
    }
}
