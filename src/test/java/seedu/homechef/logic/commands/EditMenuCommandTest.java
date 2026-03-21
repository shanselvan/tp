package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.EditMenuCommand.EditMenuDescriptor;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.ReadOnlyUserPrefs;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.MenuItemName;
import seedu.homechef.model.menu.Price;
import seedu.homechef.model.menu.ReadOnlyMenuBook;
import seedu.homechef.model.order.Order;

public class EditMenuCommandTest {

    private static final MenuItem CHICKEN = new MenuItem(
            new MenuItemName("Chicken Rice"), new Price("5.50"), true);
    private static final MenuItem NASI = new MenuItem(
            new MenuItemName("Nasi Goreng"), new Price("6.00"), true);

    @Test
    public void execute_editPrice_success() throws Exception {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList(CHICKEN);

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setPrice(new Price("7.00"));
        CommandResult result = new EditMenuCommand(Index.fromOneBased(1), descriptor).execute(modelStub);

        assertEquals(String.format(EditMenuCommand.MESSAGE_EDIT_MENU_ITEM_SUCCESS, "Chicken Rice", "7.00", true),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_editName_success() throws Exception {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList(CHICKEN);

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setName(new MenuItemName("Mee Goreng"));
        CommandResult result = new EditMenuCommand(Index.fromOneBased(1), descriptor).execute(modelStub);

        assertEquals(String.format(EditMenuCommand.MESSAGE_EDIT_MENU_ITEM_SUCCESS, "Mee Goreng", "5.50", true),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_editAvailability_success() throws Exception {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList(CHICKEN);

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setAvailable(false);
        CommandResult result = new EditMenuCommand(Index.fromOneBased(1), descriptor).execute(modelStub);

        assertEquals(String.format(EditMenuCommand.MESSAGE_EDIT_MENU_ITEM_SUCCESS, "Chicken Rice", "5.50", false),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateName_throwsCommandException() {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList(CHICKEN, NASI);

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setName(new MenuItemName("Nasi Goreng"));
        EditMenuCommand editCommand = new EditMenuCommand(Index.fromOneBased(1), descriptor);

        assertThrows(CommandException.class,
                EditMenuCommand.MESSAGE_DUPLICATE_MENU_ITEM, () -> editCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList();

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setPrice(new Price("8.00"));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX, ()
                -> new EditMenuCommand(Index.fromOneBased(1), descriptor).execute(modelStub));
    }

    @Test
    public void equals() {
        EditMenuDescriptor desc1 = new EditMenuDescriptor();
        desc1.setPrice(new Price("5.50"));
        EditMenuDescriptor desc2 = new EditMenuDescriptor();
        desc2.setPrice(new Price("6.00"));

        EditMenuCommand editFirst = new EditMenuCommand(Index.fromOneBased(1), desc1);
        EditMenuCommand editSecond = new EditMenuCommand(Index.fromOneBased(2), desc1);
        EditMenuCommand editFirstDiffDesc = new EditMenuCommand(Index.fromOneBased(1), desc2);

        assertTrue(editFirst.equals(editFirst));
        assertTrue(editFirst.equals(new EditMenuCommand(Index.fromOneBased(1), desc1)));
        assertFalse(editFirst.equals(editSecond));
        assertFalse(editFirst.equals(editFirstDiffDesc));
        assertFalse(editFirst.equals(null));
        assertFalse(editFirst.equals(1));
    }

    // --- Model stubs ---

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
        public void setMenuItem(MenuItem target, MenuItem editedItem) {
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

    private class ModelStubWithMenuItemList extends ModelStub {
        private final ObservableList<MenuItem> items = FXCollections.observableArrayList();

        ModelStubWithMenuItemList(MenuItem... menuItems) {
            items.addAll(Arrays.asList(menuItems));
        }

        @Override
        public ObservableList<MenuItem> getFilteredMenuItemList() {
            return items;
        }

        @Override
        public boolean hasMenuItem(MenuItem menuItem) {
            return items.stream().anyMatch(menuItem::isSameMenuItem);
        }

        @Override
        public void setMenuItem(MenuItem target, MenuItem edited) {
            int idx = items.indexOf(target);
            items.set(idx, edited);
        }
    }
}
