package seedu.homechef.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.model.order.Order;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' HomeChef file path.
     */
    Path getHomeChefFilePath();

    /**
     * Sets the user prefs' HomeChef file path.
     */
    void setHomeChefFilePath(Path homeChefFilePath);

    /**
     * Replaces HomeChef data with the data in {@code homeChef}.
     */
    void setHomeChef(ReadOnlyHomeChef homeChef);

    /** Returns the HomeChef */
    ReadOnlyHomeChef getHomeChef();

    /**
     * Returns true if a order with the same identity as {@code order} exists in the HomeChef.
     */
    boolean hasOrder(Order order);

    /**
     * Deletes the given order.
     * The order must exist in the HomeChef.
     */
    void deleteOrder(Order target);

    /**
     * Adds the given order.
     * {@code order} must not already exist in the HomeChef.
     */
    void addOrder(Order order);

    /**
     * Replaces the given order {@code target} with {@code editedOrder}.
     * {@code target} must exist in the HomeChef.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the HomeChef.
     */
    void setOrder(Order target, Order editedOrder);

    /** Returns an unmodifiable view of the filtered order list */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);
}
