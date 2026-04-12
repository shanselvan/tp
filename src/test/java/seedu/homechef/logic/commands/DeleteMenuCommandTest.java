package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.Model;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.ReadOnlyUserPrefs;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.ReadOnlyMenuBook;
import seedu.homechef.model.order.Order;
import seedu.homechef.testutil.OrderBuilder;

public class DeleteMenuCommandTest {

    @Test
    public void execute_validIndex_success() throws Exception {
        MenuItem chicken = new MenuItem(new Food("Chicken Rice"), new Price("5.50"), Availability.YES);
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList(chicken);

        CommandResult result = new DeleteMenuCommand(Index.fromOneBased(1)).execute(modelStub);

        assertEquals(String.format(DeleteMenuCommand.MESSAGE_DELETE_MENU_ITEM_SUCCESS, "Chicken Rice"),
                result.getFeedbackToUser());
        assertTrue(modelStub.deletedItems.contains(chicken));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubWithMenuItemList modelStub = new ModelStubWithMenuItemList();

        assertThrows(CommandException.class, ()
                -> new DeleteMenuCommand(Index.fromOneBased(1)).execute(modelStub));
    }

    @Test
    public void execute_menuItemHasActiveOrders_success() throws Exception {
        MenuItem chicken = new MenuItem(new Food("Chicken Rice"), new Price("5.50"), Availability.YES);
        Order activeOrder = new OrderBuilder().withFood("Chicken Rice").build();
        ModelStubWithMenuItemAndOrders modelStub =
                new ModelStubWithMenuItemAndOrders(chicken, List.of(activeOrder));

        CommandResult result = new DeleteMenuCommand(Index.fromOneBased(1)).execute(modelStub);

        assertEquals(String.format(DeleteMenuCommand.MESSAGE_DELETE_MENU_ITEM_SUCCESS, "Chicken Rice"),
                result.getFeedbackToUser());
        assertTrue(modelStub.deletedItems.contains(chicken));
    }

    @Test
    public void execute_menuItemHasActiveOrdersCaseInsensitive_success() throws Exception {
        // Order food name differs in case from menu item — should still be blocked
        MenuItem chicken = new MenuItem(new Food("Chicken Rice"), new Price("5.50"), Availability.YES);
        Order activeOrder = new OrderBuilder().withFood("chicken rice").build();
        ModelStubWithMenuItemAndOrders modelStub =
                new ModelStubWithMenuItemAndOrders(chicken, List.of(activeOrder));

        CommandResult result = new DeleteMenuCommand(Index.fromOneBased(1)).execute(modelStub);

        assertEquals(String.format(DeleteMenuCommand.MESSAGE_DELETE_MENU_ITEM_SUCCESS, "Chicken Rice"),
                result.getFeedbackToUser());
        assertTrue(modelStub.deletedItems.contains(chicken));
    }

    @Test
    public void execute_menuItemNoActiveOrders_success() throws Exception {
        MenuItem chicken = new MenuItem(new Food("Chicken Rice"), new Price("5.50"), Availability.YES);
        Order unrelatedOrder = new OrderBuilder().withFood("Birthday Cake").build();
        ModelStubWithMenuItemAndOrders modelStub =
                new ModelStubWithMenuItemAndOrders(chicken, List.of(unrelatedOrder));

        CommandResult result = new DeleteMenuCommand(Index.fromOneBased(1)).execute(modelStub);

        assertEquals(String.format(DeleteMenuCommand.MESSAGE_DELETE_MENU_ITEM_SUCCESS, "Chicken Rice"),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_menuItemOrderCompletedAndPaid_success() throws Exception {
        MenuItem chicken = new MenuItem(new Food("Chicken Rice"), new Price("5.50"), Availability.YES);
        Order completedAndPaidOrder = new OrderBuilder()
                .withFood("Chicken Rice")
                .withCompletionStatus("Completed")
                .withPaymentStatus("Paid")
                .build();
        ModelStubWithMenuItemAndOrders modelStub =
                new ModelStubWithMenuItemAndOrders(chicken, List.of(completedAndPaidOrder));

        CommandResult result = new DeleteMenuCommand(Index.fromOneBased(1)).execute(modelStub);

        assertEquals(String.format(DeleteMenuCommand.MESSAGE_DELETE_MENU_ITEM_SUCCESS, "Chicken Rice"),
                result.getFeedbackToUser());
        assertTrue(modelStub.deletedItems.contains(chicken));
    }

    @Test
    public void execute_menuItemOrderCompletedButUnpaid_success() throws Exception {
        MenuItem chicken = new MenuItem(new Food("Chicken Rice"), new Price("5.50"), Availability.YES);
        Order completedButUnpaidOrder = new OrderBuilder()
                .withFood("Chicken Rice")
                .withCompletionStatus("Completed")
                .withPaymentStatus("Unpaid")
                .build();
        ModelStubWithMenuItemAndOrders modelStub =
                new ModelStubWithMenuItemAndOrders(chicken, List.of(completedButUnpaidOrder));

        CommandResult result = new DeleteMenuCommand(Index.fromOneBased(1)).execute(modelStub);

        assertEquals(String.format(DeleteMenuCommand.MESSAGE_DELETE_MENU_ITEM_SUCCESS, "Chicken Rice"),
                result.getFeedbackToUser());
        assertTrue(modelStub.deletedItems.contains(chicken));
    }

    @Test
    public void equals() {
        DeleteMenuCommand deleteFirst = new DeleteMenuCommand(Index.fromOneBased(1));
        DeleteMenuCommand deleteSecond = new DeleteMenuCommand(Index.fromOneBased(2));

        // EP: same object -> returns true
        assertTrue(deleteFirst.equals(deleteFirst));

        // EP: same index -> returns true
        assertTrue(deleteFirst.equals(new DeleteMenuCommand(Index.fromOneBased(1))));

        // EP: different index -> returns false
        assertFalse(deleteFirst.equals(deleteSecond));

        // EP: null -> returns false
        assertFalse(deleteFirst.equals(null));

        // EP: different types -> returns false
        assertFalse(deleteFirst.equals(1));
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
        final ArrayList<MenuItem> deletedItems = new ArrayList<>();
        private final ObservableList<MenuItem> items = FXCollections.observableArrayList();

        ModelStubWithMenuItemList(MenuItem... menuItems) {
            items.addAll(Arrays.asList(menuItems));
        }

        @Override
        public ObservableList<MenuItem> getFilteredMenuItemList() {
            return items;
        }

        @Override
        public void deleteMenuItem(MenuItem menuItem) {
            items.remove(menuItem);
            deletedItems.add(menuItem);
        }

        @Override
        public ReadOnlyHomeChef getHomeChef() {
            return new HomeChef();
        }
    }

    private class ModelStubWithMenuItemAndOrders extends ModelStub {
        final ArrayList<MenuItem> deletedItems = new ArrayList<>();
        private final ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();
        private final HomeChef homeChef = new HomeChef();

        ModelStubWithMenuItemAndOrders(MenuItem menuItem, List<Order> orders) {
            menuItems.add(menuItem);
            orders.forEach(homeChef::addOrder);
        }

        @Override
        public ObservableList<MenuItem> getFilteredMenuItemList() {
            return menuItems;
        }

        @Override
        public void deleteMenuItem(MenuItem menuItem) {
            menuItems.remove(menuItem);
            deletedItems.add(menuItem);
        }

        @Override
        public ReadOnlyHomeChef getHomeChef() {
            return homeChef;
        }
    }
}
