package seedu.homechef.model;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.commons.core.LogsCenter;
import seedu.homechef.model.order.Order;

/**
 * Represents the in-memory model of the HomeChef data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final HomeChef homeChef;
    private final UserPrefs userPrefs;
    private final FilteredList<Order> filteredOrders;

    /**
     * Initializes a ModelManager with the given homeChef and userPrefs.
     */
    public ModelManager(ReadOnlyHomeChef homeChef, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(homeChef, userPrefs);

        logger.fine("Initializing with HomeChef: " + homeChef + " and user prefs " + userPrefs);

        this.homeChef = new HomeChef(homeChef);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredOrders = new FilteredList<>(this.homeChef.getOrderList());
    }

    public ModelManager() {
        this(new HomeChef(), new UserPrefs());
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
        return filteredOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
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
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredOrders.equals(otherModelManager.filteredOrders);
    }

}
