package seedu.homechef.model.menu;

import javafx.collections.ObservableList;
import seedu.homechef.model.menu.exceptions.AmbiguousMenuItemException;
import seedu.homechef.model.menu.exceptions.MenuItemNotFoundException;

/**
 * Unmodifiable view of a MenuBook.
 */
public interface ReadOnlyMenuBook {

    /**
     * Returns an unmodifiable view of the menu items list.
     * This list will not contain any duplicate menu items.
     */
    ObservableList<MenuItem> getMenuItemList();

    /**
     * Returns the unique {@code MenuItem} matching {@code foodName} using a three-tier strategy:
     * <ol>
     *   <li>If {@code foodName} is a positive integer within the menu size, returns the item
     *       at that 1-based index (bypasses name matching entirely).</li>
     *   <li>Exact case-insensitive name match.</li>
     *   <li>Unique substring match (multiple matches are ambiguous).</li>
     * </ol>
     *
     * @param foodName the food name or 1-based menu index to look up
     * @throws MenuItemNotFoundException if no items match
     * @throws AmbiguousMenuItemException if the input matches multiple items by substring
     *         and no exact match exists
     */
    MenuItem resolveMenuItem(String foodName);
}
