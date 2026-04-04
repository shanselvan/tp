package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.ReadOnlyUserPrefs;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.Price;
import seedu.homechef.model.menu.ReadOnlyMenuBook;
import seedu.homechef.model.order.Order;

public class AddMenuCommandTest {

    @Test
    public void constructor_nullMenuItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMenuCommand(null));
    }

    @Test
    public void execute_menuItemAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMenuItemAdded modelStub = new ModelStubAcceptingMenuItemAdded();
        MenuItem validItem = new MenuItem(new Food("Chicken Rice"), new Price("5.50"), true);

        CommandResult commandResult = new AddMenuCommand(validItem).execute(modelStub);

        assertEquals(String.format(AddMenuCommand.MESSAGE_SUCCESS, "Chicken Rice", "5.50"),
                commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.menuItemsAdded.size());
    }

    @Test
    public void execute_duplicateMenuItem_throwsCommandException() {
        MenuItem validItem = new MenuItem(new Food("Chicken Rice"), new Price("5.50"), true);
        AddMenuCommand addCommand = new AddMenuCommand(validItem);
        ModelStubWithMenuItem modelStub = new ModelStubWithMenuItem(validItem);

        assertThrows(CommandException.class,
                AddMenuCommand.MESSAGE_DUPLICATE_MENU_ITEM, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        MenuItem chicken = new MenuItem(new Food("Chicken Rice"), new Price("5.50"), true);
        MenuItem nasi = new MenuItem(new Food("Nasi Goreng"), new Price("6.00"), true);
        AddMenuCommand addChicken = new AddMenuCommand(chicken);
        AddMenuCommand addNasi = new AddMenuCommand(nasi);

        // EP: same command -> returns true
        assertTrue(addChicken.equals(addChicken));

        // EP: same food -> returns true
        assertTrue(addChicken.equals(new AddMenuCommand(chicken)));

        // EP: different food -> returns false
        assertFalse(addChicken.equals(addNasi));

        // EP: null -> returns false
        assertFalse(addChicken.equals(null));

        // EP: different types -> returns false
        assertFalse(addChicken.equals(1));
    }

    /**
     * A default model stub that has all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getHomeChefFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHomeChefFilePath(Path homeChefFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addOrder(Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHomeChef(ReadOnlyHomeChef newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyHomeChef getHomeChef() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasOrder(Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteOrder(Order target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOrder(Order target, Order editedOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMenuItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMenuItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMenuItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenuItem(MenuItem target, MenuItem edited) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyMenuBook getMenuBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<MenuItem> getFilteredMenuItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMenuItemList(Predicate<MenuItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single menu item.
     */
    private class ModelStubWithMenuItem extends ModelStub {
        private final MenuItem item;

        ModelStubWithMenuItem(MenuItem item) {
            this.item = item;
        }

        @Override
        public boolean hasMenuItem(MenuItem menuItem) {
            return this.item.isSameMenuItem(menuItem);
        }
    }

    /**
     * A Model stub that always accepts the menu item being added.
     */
    private class ModelStubAcceptingMenuItemAdded extends ModelStub {
        final ArrayList<MenuItem> menuItemsAdded = new ArrayList<>();

        @Override
        public boolean hasMenuItem(MenuItem menuItem) {
            return menuItemsAdded.stream().anyMatch(menuItem::isSameMenuItem);
        }

        @Override
        public void addMenuItem(MenuItem menuItem) {
            requireNonNull(menuItem);
            menuItemsAdded.add(menuItem);
        }

        @Override
        public ReadOnlyMenuBook getMenuBook() {
            return new MenuBook();
        }

        @Override
        public ObservableList<MenuItem> getFilteredMenuItemList() {
            return new MenuBook().getMenuItemList();
        }

        @Override
        public void updateFilteredMenuItemList(Predicate<MenuItem> predicate) {
            // not called in tests
        }
    }
}
