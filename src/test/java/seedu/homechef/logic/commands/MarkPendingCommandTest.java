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
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.order.Order;
import seedu.homechef.testutil.OrderBuilder;
import seedu.homechef.testutil.TypicalMenuItems;

public class MarkPendingCommandTest {
    private Model model = new ModelManager(
            getTypicalHomeChef(), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToMark = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order pendingOrder = new OrderBuilder(orderToMark).withCompletionStatus("Pending").build();
        MarkPendingCommand markPendingCommand = new MarkPendingCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(MarkPendingCommand.MESSAGE_PENDING_ORDER_SUCCESS,
                Messages.format(pendingOrder));

        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToMark, pendingOrder);

        assertCommandSuccess(markPendingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        MarkPendingCommand markPendingCommand = new MarkPendingCommand(outOfBoundIndex);

        assertCommandFailure(markPendingCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_filteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order pendingOrder = new OrderBuilder(orderInFilteredList).withCompletionStatus("Pending").build();
        MarkPendingCommand markPendingCommand = new MarkPendingCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(MarkPendingCommand.MESSAGE_PENDING_ORDER_SUCCESS,
                Messages.format(pendingOrder));

        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.setOrder(model.getFilteredOrderList().get(0), pendingOrder);

        assertCommandSuccess(markPendingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MarkPendingCommand markPendingFirstCommand = new MarkPendingCommand(INDEX_FIRST_ORDER);
        MarkPendingCommand markPendingSecondCommand = new MarkPendingCommand(INDEX_SECOND_ORDER);

        // EP: same object -> returns true
        assertTrue(markPendingFirstCommand.equals(markPendingFirstCommand));

        // EP: same values -> returns true
        MarkPendingCommand markPendingFirstCommandCopy = new MarkPendingCommand(INDEX_FIRST_ORDER);
        assertTrue(markPendingFirstCommand.equals(markPendingFirstCommandCopy));

        // EP: different types -> returns false
        assertFalse(markPendingFirstCommand.equals(1));

        // EP: null -> returns false
        assertFalse(markPendingFirstCommand.equals(null));

        // EP: different order -> returns false
        assertFalse(markPendingFirstCommand.equals(markPendingSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkPendingCommand markPendingCommand = new MarkPendingCommand(targetIndex);
        String expected = MarkPendingCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, markPendingCommand.toString());
    }
}
