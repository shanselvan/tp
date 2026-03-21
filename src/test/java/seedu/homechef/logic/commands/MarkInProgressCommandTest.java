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
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.order.Order;
import seedu.homechef.testutil.OrderBuilder;

public class MarkInProgressCommandTest {
    private Model model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToMark = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order inProgressOrder = new OrderBuilder(orderToMark).withCompletionStatus("In progress").build();
        MarkInProgressCommand markInProgressCommand = new MarkInProgressCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(MarkInProgressCommand.MESSAGE_IN_PROGRESS_ORDER_SUCCESS,
                Messages.format(orderToMark));

        Model expectedModel = new ModelManager(new HomeChef(model.getHomeChef()), new MenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToMark, inProgressOrder);

        assertCommandSuccess(markInProgressCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        MarkInProgressCommand markInProgressCommand = new MarkInProgressCommand(outOfBoundIndex);

        assertCommandFailure(markInProgressCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_filteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order inProgressOrder = new OrderBuilder(orderInFilteredList).withCompletionStatus("In progress").build();
        MarkInProgressCommand markInProgressCommand = new MarkInProgressCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(MarkInProgressCommand.MESSAGE_IN_PROGRESS_ORDER_SUCCESS,
                Messages.format(inProgressOrder));

        Model expectedModel = new ModelManager(new HomeChef(model.getHomeChef()), new MenuBook(), new UserPrefs());
        expectedModel.setOrder(model.getFilteredOrderList().get(0), inProgressOrder);

        assertCommandSuccess(markInProgressCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MarkInProgressCommand markInProgressFirstCommand = new MarkInProgressCommand(INDEX_FIRST_ORDER);
        MarkInProgressCommand markInProgressSecondCommand = new MarkInProgressCommand(INDEX_SECOND_ORDER);

        // same object -> returns true
        assertTrue(markInProgressFirstCommand.equals(markInProgressFirstCommand));

        // same values -> returns true
        MarkInProgressCommand markInProgressFirstCommandCopy = new MarkInProgressCommand(INDEX_FIRST_ORDER);
        assertTrue(markInProgressFirstCommand.equals(markInProgressFirstCommandCopy));

        // different types -> returns false
        assertFalse(markInProgressFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markInProgressFirstCommand.equals(null));

        // different order -> returns false
        assertFalse(markInProgressFirstCommand.equals(markInProgressSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkInProgressCommand markInProgressCommand = new MarkInProgressCommand(targetIndex);
        String expected = MarkInProgressCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, markInProgressCommand.toString());
    }
}
