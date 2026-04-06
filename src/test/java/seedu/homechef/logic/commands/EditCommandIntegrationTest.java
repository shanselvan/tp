package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.order.Phone;
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
    public void execute_editFoodToAvailableItem_success() throws Exception {
        Index indexAlice = Index.fromOneBased(1);
        EditCommand.EditOrderDescriptor descriptor = new EditCommand.EditOrderDescriptor();
        descriptor.setFood(new Food("Chicken Rice"));
        new EditCommand(indexAlice, descriptor).execute(model);
        // succeeds — no exception thrown
    }

    @Test
    public void execute_editFoodToUnavailableItem_throwsCommandException() {
        MenuItem unavailable = new MenuItem(
                new Food("Sourdough Bread"), new Price("8.00"), false);
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
}
