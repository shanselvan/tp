package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.order.Order;
import seedu.homechef.testutil.OrderBuilder;
import seedu.homechef.testutil.TypicalMenuItems;
import seedu.homechef.testutil.TypicalOrders;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHomeChef(), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
    }

    @Test
    public void execute_newOrder_success() {
        Order inputOrder = new OrderBuilder().build();
        // price is derived from the menu; "Birthday Cake" costs "25.00"
        Order expectedOrder = new OrderBuilder().withPrice("25.00").build();

        Model expectedModel = new ModelManager(
                model.getHomeChef(), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.addOrder(expectedOrder);

        assertCommandSuccess(new AddCommand(inputOrder), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedOrder)),
                expectedModel);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        Order orderInList = model.getHomeChef().getOrderList().get(0);
        assertCommandFailure(new AddCommand(orderInList), model,
                AddCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_foodMatchesAvailableMenuItem_success() throws Exception {
        Order inputOrder = new OrderBuilder().withFood("Chicken Rice").build();
        new AddCommand(inputOrder).execute(model);
        // price is derived from menu; "Chicken Rice" costs "5.50"
        Order expectedOrder = new OrderBuilder().withFood("Chicken Rice").withPrice("5.50").build();
        assertTrue(model.getFilteredOrderList().contains(expectedOrder));
    }

    @Test
    public void execute_foodMatchesUnavailableMenuItem_throwsCommandException() {
        MenuItem unavailableChicken = new MenuItem(
                new Food("Chicken Rice"),
                new Price("5.50"), false);
        MenuBook mb = new MenuBook();
        mb.addMenuItem(unavailableChicken);
        for (MenuItem item : TypicalMenuItems.getTypicalMenuItems()) {
            if (!item.getFood().toString().equalsIgnoreCase("Chicken Rice")) {
                mb.addMenuItem(item);
            }
        }
        model = new ModelManager(TypicalOrders.getTypicalHomeChef(), mb, new UserPrefs());

        Order unavailableOrder = new OrderBuilder().withFood("Chicken Rice").build();
        assertThrows(CommandException.class, () -> new AddCommand(unavailableOrder).execute(model),
                "Expected unavailable menu item to be rejected");
    }

    @Test
    public void execute_foodNotInMenu_throwsCommandExceptionWithAddMenuSuggestion() {
        Order unknownOrder = new OrderBuilder().withFood("Pizza Margherita").build();
        CommandException thrown = assertThrows(CommandException.class, ()
                -> new AddCommand(unknownOrder).execute(model));
        assertTrue(thrown.getMessage().contains("add-menu"),
                "Error message should suggest 'add-menu'");
    }

    @Test
    public void execute_foodMatchesMultipleMenuItems_throwsCommandException() {
        // "Wedding Cake" is a substring of both "Wedding Cake - 3 Tier" and "Wedding Cake" in the typical menu
        Order ambiguousOrder = new OrderBuilder().withFood("Wedding Cake").build();
        CommandException thrown = assertThrows(CommandException.class, ()
                -> new AddCommand(ambiguousOrder).execute(model));
        assertTrue(thrown.getMessage().contains("Wedding Cake - 3 Tier"),
                "Error message should list matching menu items");
    }

    @Test
    public void execute_foodNameCaseInsensitive_normalizesToCanonicalName() throws Exception {
        Order orderWithLowercaseFood = new OrderBuilder().withFood("chicken rice").build();
        new AddCommand(orderWithLowercaseFood).execute(model);

        Order storedOrder = model.getFilteredOrderList().stream()
                .filter(o -> o.getCustomer().equals(orderWithLowercaseFood.getCustomer())
                        && o.getDate().equals(orderWithLowercaseFood.getDate()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Order not found in model"));

        assertEquals("Chicken Rice", storedOrder.getFood().toString(),
                "Food name should be normalized to canonical menu casing");
    }
}
