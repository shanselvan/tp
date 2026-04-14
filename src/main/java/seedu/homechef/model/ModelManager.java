package seedu.homechef.model;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.commons.core.LogsCenter;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.ReadOnlyMenuBook;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Order;

/**
 * Represents the in-memory model of the HomeChef data.
 * List sorts orders by fulfillment date, then customer name, then food name.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static final Comparator<Order> DEFAULT_ORDER_COMPARATOR = (a, b) -> {
        Date da = a.getDate();
        Date db = b.getDate();

        int dateCmp = da.compareTo(db);
        if (dateCmp != 0) {
            return dateCmp;
        }

        int nameCmp = a.getCustomer().toString().compareToIgnoreCase(b.getCustomer().toString());
        if (nameCmp != 0) {
            return nameCmp;
        }

        return a.getFood().toString().compareToIgnoreCase(b.getFood().toString());
    };

    private final HomeChef homeChef;
    private final MenuBook menuBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Order> filteredOrders;
    private final SortedList<Order> sortedOrders;
    private final FilteredList<MenuItem> filteredMenuItems;

    /**
     * Initializes a ModelManager with the given homeChef, menuBook, and userPrefs.
     */
    public ModelManager(ReadOnlyHomeChef homeChef, ReadOnlyMenuBook menuBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(homeChef, menuBook, userPrefs);

        logger.fine("Initializing with HomeChef: " + homeChef + ", MenuBook: " + menuBook
                + " and user prefs " + userPrefs);

        this.homeChef = new HomeChef(homeChef);
        this.menuBook = new MenuBook(menuBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredOrders = new FilteredList<>(this.homeChef.getOrderList());
        sortedOrders = new SortedList<>(filteredOrders);
        sortedOrders.setComparator(DEFAULT_ORDER_COMPARATOR);
        filteredMenuItems = new FilteredList<>(this.menuBook.getMenuItemList());
    }

    public ModelManager() {
        this(new HomeChef(), new MenuBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getHomeChefFilePath() {
        return userPrefs.getHomeChefFilePath();
    }

    @Override
    public void setHomeChefFilePath(Path homeChefFilePath) {
        requireNonNull(homeChefFilePath);
        userPrefs.setHomeChefFilePath(homeChefFilePath);
    }

    //=========== HomeChef ================================================================================

    @Override
    public void setHomeChef(ReadOnlyHomeChef homeChef) {
        this.homeChef.resetData(homeChef);
    }

    @Override
    public ReadOnlyHomeChef getHomeChef() {
        return homeChef;
    }

    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return homeChef.hasOrder(order);
    }

    @Override
    public void deleteOrder(Order target) {
        homeChef.removeOrder(target);
    }

    @Override
    public void addOrder(Order order) {
        homeChef.addOrder(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        homeChef.setOrder(target, editedOrder);
    }

    //=========== Filtered Order List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of
     * {@code versionedHomeChef}
     */
    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return sortedOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    //=========== MenuBook ================================================================================

    @Override
    public ReadOnlyMenuBook getMenuBook() {
        return menuBook;
    }

    @Override
    public boolean hasMenuItem(MenuItem menuItem) {
        requireNonNull(menuItem);
        return menuBook.hasMenuItem(menuItem);
    }

    @Override
    public void addMenuItem(MenuItem menuItem) {
        menuBook.addMenuItem(menuItem);
        updateFilteredMenuItemList(PREDICATE_SHOW_ALL_MENU_ITEMS);
    }

    @Override
    public void deleteMenuItem(MenuItem target) {
        menuBook.removeMenuItem(target);
    }

    @Override
    public void setMenuItem(MenuItem target, MenuItem editedItem) {
        requireAllNonNull(target, editedItem);
        menuBook.setMenuItem(target, editedItem);
    }

    //=========== Filtered Menu Item List Accessors =============================================================

    @Override
    public ObservableList<MenuItem> getFilteredMenuItemList() {
        return filteredMenuItems;
    }

    @Override
    public void updateFilteredMenuItemList(Predicate<MenuItem> predicate) {
        requireNonNull(predicate);
        filteredMenuItems.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return homeChef.equals(otherModelManager.homeChef)
                && menuBook.equals(otherModelManager.menuBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredOrders.equals(otherModelManager.filteredOrders)
                && filteredMenuItems.equals(otherModelManager.filteredMenuItems);
    }

}
