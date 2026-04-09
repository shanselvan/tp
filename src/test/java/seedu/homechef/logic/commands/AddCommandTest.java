package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalOrders.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.Model;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.ReadOnlyUserPrefs;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.testutil.OrderBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_orderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        Order inputOrder = new OrderBuilder().build();
        // price is always derived from the menu item ("Birthday Cake" costs "25.00" in the stub)
        Order expectedOrder = new OrderBuilder().withPrice("25.00").build();

        CommandResult commandResult = new AddCommand(inputOrder).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedOrder)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_orderWithPaymentInfo_addSuccessful() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        PaymentInfo cashPayment = new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null);
        Order inputOrder = new OrderBuilder().withPaymentInfo(cashPayment).build();
        // price is always derived from the menu item ("Birthday Cake" costs "25.00" in the stub)
        Order expectedOrder = new OrderBuilder().withPrice("25.00").withPaymentInfo(cashPayment).build();

        CommandResult commandResult = new AddCommand(inputOrder).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedOrder)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_orderWithQuantityThree_priceMulipliedByThree() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        // Stub menu has "Birthday Cake" at $25.00; quantity 3 -> expected total $75.00
        Order inputOrder = new OrderBuilder().withQuantity(3).build();
        Order expectedOrder = new OrderBuilder().withQuantity(3).withPrice("75.00").build();

        CommandResult commandResult = new AddCommand(inputOrder).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedOrder)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_orderWithDefaultQuantity_priceNotMultiplied() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        // quantity defaults to 1; price stays $25.00
        Order inputOrder = new OrderBuilder().build();
        Order expectedOrder = new OrderBuilder().withPrice("25.00").build();

        CommandResult commandResult = new AddCommand(inputOrder).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedOrder)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_foodMatchesMultipleMenuItems_throwsCommandException() {
        // "Cake" is a substring of both "Birthday Cake" and "Wedding Cake"
        Order orderWithAmbiguousFood = new OrderBuilder().withFood("Cake").build();
        AddCommand addCommand = new AddCommand(orderWithAmbiguousFood);
        ModelStubWithAmbiguousMenu modelStub = new ModelStubWithAmbiguousMenu();

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_MENU_ITEM_AMBIGUOUS,
                        "Cake", "Birthday Cake, Wedding Cake"), () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        Order validOrder = new OrderBuilder().build();
        AddCommand addCommand = new AddCommand(validOrder);
        ModelStub modelStub = new ModelStubWithOrder(validOrder);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ORDER, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Order alice = new OrderBuilder().withCustomer("Alice").build();
        Order bob = new OrderBuilder().withCustomer("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // EP: same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // EP: same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // EP: different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // EP: null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // EP: different order -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
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
        public seedu.homechef.model.menu.ReadOnlyMenuBook getMenuBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMenuItem(seedu.homechef.model.menu.MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMenuItem(seedu.homechef.model.menu.MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMenuItem(seedu.homechef.model.menu.MenuItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenuItem(seedu.homechef.model.menu.MenuItem target,
                                seedu.homechef.model.menu.MenuItem editedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public javafx.collections.ObservableList<seedu.homechef.model.menu.MenuItem> getFilteredMenuItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMenuItemList(
                java.util.function.Predicate<seedu.homechef.model.menu.MenuItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single order.
     */
    private class ModelStubWithOrder extends ModelStub {
        private final Order order;

        ModelStubWithOrder(Order order) {
            requireNonNull(order);
            this.order = order;
        }

        @Override
        public boolean hasOrder(Order order) {
            requireNonNull(order);
            return this.order.isSameOrder(order);
        }

        @Override
        public seedu.homechef.model.menu.ReadOnlyMenuBook getMenuBook() {
            seedu.homechef.model.menu.MenuBook menuBook = new seedu.homechef.model.menu.MenuBook();
            menuBook.addMenuItem(new seedu.homechef.model.menu.MenuItem(
                    new seedu.homechef.model.common.Food("Birthday Cake"),
                    new seedu.homechef.model.common.Price("25.00"),
                    seedu.homechef.model.menu.Availability.YES));
            return menuBook;
        }
    }

    /**
     * A Model stub whose menu contains two items whose names both contain "Cake".
     */
    private class ModelStubWithAmbiguousMenu extends ModelStub {
        @Override
        public seedu.homechef.model.menu.ReadOnlyMenuBook getMenuBook() {
            seedu.homechef.model.menu.MenuBook menuBook = new seedu.homechef.model.menu.MenuBook();
            menuBook.addMenuItem(new seedu.homechef.model.menu.MenuItem(
                    new seedu.homechef.model.common.Food("Birthday Cake"),
                    new seedu.homechef.model.common.Price("25.00"),
                    seedu.homechef.model.menu.Availability.YES));
            menuBook.addMenuItem(new seedu.homechef.model.menu.MenuItem(
                    new seedu.homechef.model.common.Food("Wedding Cake"),
                    new seedu.homechef.model.common.Price("80.00"),
                    seedu.homechef.model.menu.Availability.YES));
            return menuBook;
        }
    }

    /**
     * A Model stub that always accept the order being added.
     */
    private class ModelStubAcceptingOrderAdded extends ModelStub {
        final ArrayList<Order> ordersAdded = new ArrayList<>();

        @Override
        public boolean hasOrder(Order order) {
            requireNonNull(order);
            return ordersAdded.stream().anyMatch(order::isSameOrder);
        }

        @Override
        public void addOrder(Order order) {
            requireNonNull(order);
            ordersAdded.add(order);
        }

        @Override
        public ReadOnlyHomeChef getHomeChef() {
            return new HomeChef();
        }

        @Override
        public seedu.homechef.model.menu.ReadOnlyMenuBook getMenuBook() {
            seedu.homechef.model.menu.MenuBook menuBook = new seedu.homechef.model.menu.MenuBook();
            menuBook.addMenuItem(new seedu.homechef.model.menu.MenuItem(
                    new seedu.homechef.model.common.Food("Birthday Cake"),
                    new seedu.homechef.model.common.Price("25.00"),
                    seedu.homechef.model.menu.Availability.YES));
            return menuBook;
        }

        @Override
        public javafx.collections.ObservableList<seedu.homechef.model.menu.MenuItem> getFilteredMenuItemList() {
            javafx.collections.ObservableList<seedu.homechef.model.menu.MenuItem> list =
                    javafx.collections.FXCollections.observableArrayList();
            list.add(new seedu.homechef.model.menu.MenuItem(
                    new seedu.homechef.model.common.Food("Birthday Cake"),
                    new seedu.homechef.model.common.Price("25.00"),
                    seedu.homechef.model.menu.Availability.YES));
            return list;
        }
    }

}
