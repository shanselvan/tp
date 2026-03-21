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


public class MarkCompleteCommandTest {
    private Model model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToMark = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order completedOrder = new OrderBuilder(orderToMark).withCompletionStatus("Completed").build();
        MarkCompleteCommand markCompleteCommand = new MarkCompleteCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(MarkCompleteCommand.MESSAGE_COMPLETE_ORDER_SUCCESS,
                Messages.format(orderToMark));

        Model expectedModel = new ModelManager(new HomeChef(model.getHomeChef()), new MenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToMark, completedOrder);

        assertCommandSuccess(markCompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        MarkCompleteCommand markCompleteCommand = new MarkCompleteCommand(outOfBoundIndex);

        assertCommandFailure(markCompleteCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_filteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order completedOrder = new OrderBuilder(orderInFilteredList).withCompletionStatus("Completed").build();
        MarkCompleteCommand markCompleteCommand = new MarkCompleteCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(MarkCompleteCommand.MESSAGE_COMPLETE_ORDER_SUCCESS,
                Messages.format(completedOrder));

        Model expectedModel = new ModelManager(new HomeChef(model.getHomeChef()), new MenuBook(), new UserPrefs());
        expectedModel.setOrder(model.getFilteredOrderList().get(0), completedOrder);

        assertCommandSuccess(markCompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MarkCompleteCommand markCompleteFirstCommand = new MarkCompleteCommand(INDEX_FIRST_ORDER);
        MarkCompleteCommand markCompleteSecondCommand = new MarkCompleteCommand(INDEX_SECOND_ORDER);

        // same object -> returns true
        assertTrue(markCompleteFirstCommand.equals(markCompleteFirstCommand));

        // same values -> returns true
        MarkCompleteCommand markCompleteFirstCommandCopy = new MarkCompleteCommand(INDEX_FIRST_ORDER);
        assertTrue(markCompleteFirstCommand.equals(markCompleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(markCompleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markCompleteFirstCommand.equals(null));

        // different order -> returns false
        assertFalse(markCompleteFirstCommand.equals(markCompleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkCompleteCommand markCompleteCommand = new MarkCompleteCommand(targetIndex);
        String expected = MarkCompleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, markCompleteCommand.toString());
    }
}
