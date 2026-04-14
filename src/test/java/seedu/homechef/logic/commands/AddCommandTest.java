package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.Messages.MESSAGE_DUPLICATE_ORDER;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalOrders.ALICE;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.Model;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.ReadOnlyUserPrefs;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.ReadOnlyMenuBook;
import seedu.homechef.model.order.CashPayment;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
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
        // price is always derived from the menu item ("Birthday Cake" costs "6.70" in the stub)
        Order expectedOrder = new OrderBuilder().withPrice(OrderBuilder.DEFAULT_PRICE).build();

        CommandResult commandResult = new AddCommand(inputOrder).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedOrder)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_orderWithPaymentInfo_addSuccessful() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        PaymentInfo cashPayment = new CashPayment();
        Order inputOrder = new OrderBuilder().withPaymentInfo(cashPayment).build();
        // price is always derived from the menu item ("Birthday Cake" costs "6.70" in the stub)
        Order expectedOrder = new OrderBuilder()
                .withPrice(OrderBuilder.DEFAULT_PRICE)
                .withPaymentInfo(cashPayment)
                .build();

        CommandResult commandResult = new AddCommand(inputOrder).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedOrder)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_orderWithQuantityThree_priceMulipliedByThree() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        // Stub menu has "Birthday Cake" at $6.70; quantity 3 -> expected total $20.10
        Double expectedPriceValue = Double.parseDouble(OrderBuilder.DEFAULT_PRICE) * 3;
        String expectedPriceString = expectedPriceValue.toString();
        Order inputOrder = new OrderBuilder().withQuantity(3).build();
        Order expectedOrder = new OrderBuilder().withQuantity(3).withPrice(expectedPriceString).build();

        CommandResult commandResult = new AddCommand(inputOrder).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedOrder)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_orderWithDefaultQuantity_priceNotMultiplied() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        // quantity defaults to 1; price stays $6.70
        Order inputOrder = new OrderBuilder().build();
        Order expectedOrder = new OrderBuilder().withPrice(OrderBuilder.DEFAULT_PRICE).build();

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
    public void execute_foodByMenuIndex_resolvesToCorrectItem() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        // "1" is the 1-based index of "Birthday Cake" in the stub's menu
        Order inputOrder = new OrderBuilder().withFood("1").withPrice("0.01").build();
        Order expectedOrder = new OrderBuilder().withPrice(OrderBuilder.DEFAULT_PRICE).build();

        CommandResult commandResult = new AddCommand(inputOrder).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedOrder)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        Order validOrder = new OrderBuilder().withPrice(OrderBuilder.DEFAULT_PRICE).build();
        AddCommand addCommand = new AddCommand(validOrder);
        ModelStub modelStub = new ModelStubWithOrder(validOrder);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_ORDER, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_orderWithPastDate_includesWarning() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        String yesterday = LocalDate.now().minusDays(1).format(seedu.homechef.model.order.Date.FORMATTER);
        Order inputOrder = new OrderBuilder().withDate(yesterday).build();
        Order expectedOrder = new OrderBuilder().withDate(yesterday).withPrice(OrderBuilder.DEFAULT_PRICE).build();

        CommandResult commandResult = new AddCommand(inputOrder).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedOrder))
                        + AddCommand.MESSAGE_PAST_DATE_WARNING,
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedOrder), modelStub.ordersAdded);
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
        public ReadOnlyMenuBook getMenuBook() {
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
        public void deleteMenuItem(MenuItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenuItem(MenuItem target,
                                MenuItem editedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<MenuItem> getFilteredMenuItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMenuItemList(
                Predicate<MenuItem> predicate) {
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
        public ReadOnlyMenuBook getMenuBook() {
            MenuBook menuBook = new MenuBook();
            menuBook.addMenuItem(new MenuItem(
                    new Food("Birthday Cake"),
                    new Price(OrderBuilder.DEFAULT_PRICE),
                    Availability.YES));
            return menuBook;
        }
    }

    /**
     * A Model stub whose menu contains two items whose names both contain "Cake".
     */
    private class ModelStubWithAmbiguousMenu extends ModelStub {
        @Override
        public ReadOnlyMenuBook getMenuBook() {
            MenuBook menuBook = new MenuBook();
            menuBook.addMenuItem(new MenuItem(
                    new Food("Birthday Cake"),
                    new Price(OrderBuilder.DEFAULT_PRICE),
                    Availability.YES));
            menuBook.addMenuItem(new MenuItem(
                    new Food("Wedding Cake"),
                    new Price("80.00"),
                    Availability.YES));
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
        public ReadOnlyMenuBook getMenuBook() {
            MenuBook menuBook = new MenuBook();
            menuBook.addMenuItem(new MenuItem(
                    new Food("Birthday Cake"),
                    new Price(OrderBuilder.DEFAULT_PRICE),
                    Availability.YES));
            return menuBook;
        }

        @Override
        public ObservableList<MenuItem> getFilteredMenuItemList() {
            ObservableList<MenuItem> list = FXCollections.observableArrayList();
            list.add(new MenuItem(
                    new Food("Birthday Cake"),
                    new Price(OrderBuilder.DEFAULT_PRICE),
                    Availability.YES));
            return list;
        }
    }

}

