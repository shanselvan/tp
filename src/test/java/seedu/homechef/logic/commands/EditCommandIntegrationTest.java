package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_MENU_BIRTHDAY_PRICE;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_MENU_BREAD_PRICE;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_MENU_CHICKEN_PRICE;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Quantity;
import seedu.homechef.testutil.OrderBuilder;
import seedu.homechef.testutil.TypicalMenuItems;

/**
 * Integration tests for {@code EditCommand} — verifies food name validation against the menu.
 */
public class EditCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHomeChef(), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
    }

    @Test
    public void execute_editQuantity_updatesQuantityAndRecalculatesPrice() throws Exception {
        // Alice's order defaults to quantity 1; "Birthday Cake" costs $20.00 on the menu
        Index indexAlice = Index.fromOneBased(1);
        EditCommand.EditOrderDescriptor descriptor = new EditCommand.EditOrderDescriptor();
        descriptor.setQuantity(new Quantity(3));
        double editedPriceValue = Double.parseDouble(VALID_MENU_BIRTHDAY_PRICE) * 3;
        String editedPriceString = Double.toString(editedPriceValue);
        new EditCommand(indexAlice, descriptor).execute(model);

        Order stored = model.getFilteredOrderList().get(0);
        assertEquals(new Quantity(3), stored.getQuantity());
        assertEquals(new Price(editedPriceString), stored.getPrice());
    }

    @Test
    public void execute_editFoodOnHighQuantityOrder_preservesQuantityAndRecalculatesPrice() throws Exception {
        // Change Alice's quantity to 3 first, then change the food
        Index indexAlice = Index.fromOneBased(1);
        EditCommand.EditOrderDescriptor setQty = new EditCommand.EditOrderDescriptor();
        setQty.setQuantity(new Quantity(3));
        double editedPriceValue = Double.parseDouble(VALID_MENU_CHICKEN_PRICE) * 3;
        String editedPriceString = Double.toString(editedPriceValue);
        new EditCommand(indexAlice, setQty).execute(model);

        EditCommand.EditOrderDescriptor changeFood = new EditCommand.EditOrderDescriptor();
        changeFood.setFood(new Food("Chicken Rice")); // $5.50 on the menu
        new EditCommand(indexAlice, changeFood).execute(model);

        Order stored = model.getFilteredOrderList().get(0);
        assertEquals(new Quantity(3), stored.getQuantity());
        assertEquals(new Price(editedPriceString), stored.getPrice());
    }

    @Test
    public void execute_editFoodToAvailableItem_success() throws Exception {
        Index indexAlice = Index.fromOneBased(1);
        EditCommand.EditOrderDescriptor descriptor = new EditCommand.EditOrderDescriptor();
        descriptor.setFood(new Food("Chicken Rice"));
        new EditCommand(indexAlice, descriptor).execute(model);
        // succeeds — no exception thrown
    }

    @Test
    public void execute_editFoodToZeroPricedItem_recalculatesToZero() throws Exception {
        model.addMenuItem(new MenuItem(new Food("Free Sample"), new Price("0.00"), Availability.YES));

        Index indexAlice = Index.fromOneBased(1);
        EditCommand.EditOrderDescriptor setQty = new EditCommand.EditOrderDescriptor();
        setQty.setQuantity(new Quantity(3));
        new EditCommand(indexAlice, setQty).execute(model);

        EditCommand.EditOrderDescriptor setFood = new EditCommand.EditOrderDescriptor();
        setFood.setFood(new Food("Free Sample"));
        new EditCommand(indexAlice, setFood).execute(model);

        Order stored = model.getFilteredOrderList().get(0);
        assertEquals(new Quantity(3), stored.getQuantity());
        assertEquals(new Price("0.00"), stored.getPrice());
    }

    @Test
    public void execute_editFoodFromZeroPricedItem_recalculatesToNonZero() throws Exception {
        model.addMenuItem(new MenuItem(new Food("Free Sample"), new Price("0.00"), Availability.YES));

        Index indexAlice = Index.fromOneBased(1);
        EditCommand.EditOrderDescriptor setQty = new EditCommand.EditOrderDescriptor();
        setQty.setQuantity(new Quantity(2));
        new EditCommand(indexAlice, setQty).execute(model);

        EditCommand.EditOrderDescriptor setFreeFood = new EditCommand.EditOrderDescriptor();
        setFreeFood.setFood(new Food("Free Sample"));
        new EditCommand(indexAlice, setFreeFood).execute(model);

        EditCommand.EditOrderDescriptor setChicken = new EditCommand.EditOrderDescriptor();
        setChicken.setFood(new Food("Chicken Rice"));
        new EditCommand(indexAlice, setChicken).execute(model);

        Order stored = model.getFilteredOrderList().get(0);
        assertEquals(new Quantity(2), stored.getQuantity());
        assertEquals(new Price("11.00"), stored.getPrice());
    }

    @Test
    public void execute_editFoodToUnavailableItem_throwsCommandException() {
        MenuItem unavailable = new MenuItem(
                new Food("Sourdough Bread"), new Price(VALID_MENU_BREAD_PRICE), Availability.NO);
        model.setMenuItem(
                model.getFilteredMenuItemList().stream()
                        .filter(i -> i.getFood().toString().equals("Sourdough Bread"))
                        .findFirst().get(),
                unavailable);

        Index indexBenson = Index.fromOneBased(2);
        EditCommand.EditOrderDescriptor descriptor = new EditCommand.EditOrderDescriptor();
        descriptor.setFood(new Food("Sourdough Bread"));
        EditCommand editCommandBenson = new EditCommand(indexBenson, descriptor);
        assertThrows(CommandException.class, () -> editCommandBenson.execute(model));
    }

    @Test
    public void execute_editFoodToAmbiguousItem_throwsCommandException() {
        // "Cake" has no exact match but matches multiple menu items by substring
        Index indexAlice = Index.fromOneBased(1);
        EditCommand.EditOrderDescriptor descriptor = new EditCommand.EditOrderDescriptor();
        descriptor.setFood(new Food("Cake"));
        assertThrows(CommandException.class, () -> new EditCommand(indexAlice, descriptor).execute(model));
    }

    @Test
    public void execute_editFoodToUnknownItem_throwsCommandException() {
        Index indexAlice = Index.fromOneBased(1);
        EditCommand.EditOrderDescriptor descriptor = new EditCommand.EditOrderDescriptor();
        descriptor.setFood(new Food("Pizza Margherita"));
        EditCommand editCommandAlice = new EditCommand(indexAlice, descriptor);
        assertThrows(CommandException.class, () -> editCommandAlice.execute(model));
    }

    @Test
    public void execute_editNonFoodField_doesNotTriggerMenuValidation() throws Exception {
        Index indexAlice = Index.fromOneBased(1);
        EditCommand.EditOrderDescriptor descriptor = new EditCommand.EditOrderDescriptor();
        descriptor.setPhone(new Phone("91234567"));
        new EditCommand(indexAlice, descriptor).execute(model);
        // succeeds — validation not triggered
    }

    @Test
    public void execute_foodNameCaseInsensitive_normalizesToCanonicalName() throws Exception {
        Index indexAlice = Index.fromOneBased(1);
        EditCommand.EditOrderDescriptor descriptor = new EditCommand.EditOrderDescriptor();
        descriptor.setFood(new Food("chicken RICE"));
        new EditCommand(indexAlice, descriptor).execute(model);

        String storedFoodName = model.getFilteredOrderList().get(0).getFood().toString();
        assertEquals("Chicken Rice", storedFoodName,
                "Food name should be normalized to canonical menu casing");
    }

    @Test
    public void execute_editDateToPast_feedbackIncludesWarning() throws Exception {
        Index indexAlice = Index.fromOneBased(1);
        Order orderToEdit = model.getFilteredOrderList().get(indexAlice.getZeroBased());

        EditCommand.EditOrderDescriptor descriptor = new EditCommand.EditOrderDescriptor();
        String yesterday = LocalDate.now().minusDays(1).format(Date.FORMATTER);
        descriptor.setDate(new Date(yesterday));

        CommandResult result = new EditCommand(indexAlice, descriptor).execute(model);

        Order expectedOrder = new OrderBuilder(orderToEdit).withDate(yesterday).build();
        assertEquals(String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(expectedOrder))
                + EditCommand.MESSAGE_PAST_DATE_WARNING, result.getFeedbackToUser());
    }
}
