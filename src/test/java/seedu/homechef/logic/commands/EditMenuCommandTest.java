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
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.ReadOnlyMenuBook;
import seedu.homechef.model.order.Order;

public class EditMenuCommandTest {

    private static final MenuItem CHICKEN = new MenuItem(
            new Food("Chicken Rice"), new Price("5.50"), Availability.YES);
    private static final MenuItem NASI = new MenuItem(
            new Food("Nasi Goreng"), new Price("6.00"), Availability.YES);

    @Test
    public void execute_editPrice_success() throws Exception {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList(CHICKEN);

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setPrice(new Price("7.00"));
        CommandResult result = new EditMenuCommand(Index.fromOneBased(1), descriptor).execute(modelStub);

        // EP: only price edited; name and availability fall back to original values
        assertEquals(String.format(EditMenuCommand.MESSAGE_EDIT_MENU_ITEM_SUCCESS,
                "Chicken Rice", "7.00", Availability.YES),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_editPriceToZero_success() throws Exception {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList(CHICKEN);

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setPrice(new Price("0.00"));
        CommandResult result = new EditMenuCommand(Index.fromOneBased(1), descriptor).execute(modelStub);

        assertEquals(String.format(EditMenuCommand.MESSAGE_EDIT_MENU_ITEM_SUCCESS,
                "Chicken Rice", "0.00", Availability.YES),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_editName_success() throws Exception {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList(CHICKEN);

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setName(new Food("Mee Goreng"));
        CommandResult result = new EditMenuCommand(Index.fromOneBased(1), descriptor).execute(modelStub);

        // EP: only name edited; other fields unchanged
        assertEquals(String.format(EditMenuCommand.MESSAGE_EDIT_MENU_ITEM_SUCCESS,
                "Mee Goreng", "5.50", Availability.YES),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_editAvailability_success() throws Exception {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList(CHICKEN);

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setAvailability(Availability.NO);
        CommandResult result = new EditMenuCommand(Index.fromOneBased(1), descriptor).execute(modelStub);

        // EP: only availability edited; name and price unchanged
        assertEquals(String.format(EditMenuCommand.MESSAGE_EDIT_MENU_ITEM_SUCCESS,
                "Chicken Rice", "5.50", Availability.NO),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateName_throwsCommandException() {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList(CHICKEN, NASI);

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setName(new Food("Nasi Goreng"));
        EditMenuCommand editCommand = new EditMenuCommand(Index.fromOneBased(1), descriptor);

        // EP: edited identity clashes with an existing menu item
        assertThrows(CommandException.class,
                EditMenuCommand.MESSAGE_DUPLICATE_MENU_ITEM, () -> editCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList();

        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setPrice(new Price("8.00"));

        // EP: index outside displayed menu list
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

        // EP: same object -> returns true
        assertTrue(editFirst.equals(editFirst));

        // EP: same values -> returns true
        assertTrue(editFirst.equals(new EditMenuCommand(Index.fromOneBased(1), desc1)));

        // EP: different index -> returns false
        assertFalse(editFirst.equals(editSecond));

        // EP: different desc -> returns false
        assertFalse(editFirst.equals(editFirstDiffDesc));

        // EP: null -> returns false
        assertFalse(editFirst.equals(null));

        // EP: different types -> returns false
        assertFalse(editFirst.equals(1));
    }

    @Test
    public void toStringMethod() {
        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setName(new Food("Chicken Rice"));
        descriptor.setPrice(new Price("5.50"));
        descriptor.setAvailability(Availability.YES);

        EditMenuCommand command = new EditMenuCommand(Index.fromOneBased(1), descriptor);

        String expected = EditMenuCommand.class.getCanonicalName()
                + "{index=" + Index.class.getCanonicalName() + "{zeroBasedIndex=0}, editMenuDescriptor="
                + EditMenuDescriptor.class.getCanonicalName()
                + "{name=Chicken Rice, price=5.50, availability=Available}}";

        // EP: command string representation includes both index and descriptor contents
        assertEquals(expected, command.toString());
    }

    @Test
    public void editMenuDescriptor_isAnyFieldEdited() {
        EditMenuDescriptor emptyDescriptor = new EditMenuDescriptor();
        // EP: descriptor with all-null fields
        assertFalse(emptyDescriptor.isAnyFieldEdited());

        EditMenuDescriptor descriptorWithName = new EditMenuDescriptor();
        descriptorWithName.setName(new Food("Chicken Rice"));
        // EP: descriptor with at least one non-null field
        assertTrue(descriptorWithName.isAnyFieldEdited());
    }

    @Test
    public void editMenuDescriptor_copyConstructorAndAccessors() {
        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setName(new Food("Chicken Rice"));
        descriptor.setPrice(new Price("5.50"));
        descriptor.setAvailability(Availability.NO);

        EditMenuDescriptor copy = new EditMenuDescriptor(descriptor);

        // EP: copy constructor preserves every optional field
        assertEquals(descriptor.getName(), copy.getName());
        assertEquals(descriptor.getPrice(), copy.getPrice());
        assertEquals(descriptor.getAvailability(), copy.getAvailability());
        assertEquals(descriptor, copy);
    }

    @Test
    public void editMenuDescriptor_equals() {
        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setName(new Food("Chicken Rice"));
        descriptor.setPrice(new Price("5.50"));
        descriptor.setAvailability(Availability.YES);

        EditMenuDescriptor sameDescriptor = new EditMenuDescriptor(descriptor);
        EditMenuDescriptor differentDescriptor = new EditMenuDescriptor();
        differentDescriptor.setAvailability(Availability.NO);

        // EP: same object -> returns true
        assertTrue(descriptor.equals(descriptor));
        // EP: same field values -> returns true
        assertTrue(descriptor.equals(sameDescriptor));
        // EP: null -> returns false
        assertFalse(descriptor.equals(null));
        // EP: different types -> returns false
        assertFalse(descriptor.equals(1));
        // EP: different field values -> returns false
        assertFalse(descriptor.equals(differentDescriptor));
    }

    @Test
    public void editMenuDescriptor_toStringMethod() {
        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setName(new Food("Chicken Rice"));
        descriptor.setPrice(new Price("5.50"));
        descriptor.setAvailability(Availability.YES);

        String expected = EditMenuDescriptor.class.getCanonicalName()
                + "{name=Chicken Rice, price=5.50, availability=Available}";

        // EP: descriptor string representation includes all populated fields
        assertEquals(expected, descriptor.toString());
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
