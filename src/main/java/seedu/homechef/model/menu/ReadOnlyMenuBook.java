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
     * Returns the unique {@code MenuItem} matching {@code foodName}.
     * Exact case-insensitive match takes priority and bypasses ambiguity checking;
     * falls back to substring match only when no exact match exists.
     *
     * @param foodName the food name to look up; matched case-insensitively,
     *         with exact match taking priority over substring match
     * @throws MenuItemNotFoundException if no items match
     * @throws AmbiguousMenuItemException if the input matches multiple items by substring
     *         and no exact match exists
     */
    MenuItem resolveMenuItem(String foodName);
}
