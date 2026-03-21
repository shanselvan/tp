package seedu.homechef.logic.commands;

import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.homechef.logic.Messages;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.order.Order;
import seedu.homechef.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());
    }

    @Test
    public void execute_newOrder_success() {
        Order validOrder = new OrderBuilder().build();

        Model expectedModel = new ModelManager(model.getHomeChef(), new MenuBook(), new UserPrefs());
        expectedModel.addOrder(validOrder);

        assertCommandSuccess(new AddCommand(validOrder), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validOrder)),
                expectedModel);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        Order orderInList = model.getHomeChef().getOrderList().get(0);
        assertCommandFailure(new AddCommand(orderInList), model,
                AddCommand.MESSAGE_DUPLICATE_ORDER);
    }

}
