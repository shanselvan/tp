package seedu.homechef.model.menu;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.homechef.commons.util.ToStringBuilder;

/**
 * Wraps all menu data at the MenuBook level.
 * Duplicates are not allowed (by .isSameMenuItem comparison).
 */
public class MenuBook implements ReadOnlyMenuBook {

    private final UniqueMenuItemList menuItems;

    {
        menuItems = new UniqueMenuItemList();
    }

    public MenuBook() {}

    /**
     * Creates a MenuBook using the MenuItems in the {@code toBeCopied}.
     */
    public MenuBook(ReadOnlyMenuBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the menu item list with {@code menuItems}.
     */
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems.setMenuItems(menuItems);
    }

    /**
     * Resets the existing data of this {@code MenuBook} with {@code newData}.
     */
    public void resetData(ReadOnlyMenuBook newData) {
        requireNonNull(newData);
        setMenuItems(newData.getMenuItemList());
    }

    /**
     * Returns true if a menu item with the same identity exists in the MenuBook.
     */
    public boolean hasMenuItem(MenuItem menuItem) {
        requireNonNull(menuItem);
        return menuItems.contains(menuItem);
    }

    /**
     * Adds a menu item to the MenuBook.
     */
    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    /**
     * Replaces the given menu item {@code target} with {@code editedItem}.
     */
    public void setMenuItem(MenuItem target, MenuItem editedItem) {
        requireNonNull(editedItem);
        menuItems.setMenuItem(target, editedItem);
    }

    /**
     * Removes {@code toRemove} from this {@code MenuBook}.
     */
    public void removeMenuItem(MenuItem toRemove) {
        menuItems.remove(toRemove);
    }

    @Override
    public ObservableList<MenuItem> getMenuItemList() {
        return menuItems.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MenuBook)) {
            return false;
        }
        MenuBook otherMenuBook = (MenuBook) other;
        return menuItems.equals(otherMenuBook.menuItems);
    }

    @Override
    public int hashCode() {
        return menuItems.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("menuItems", menuItems)
                .toString();
    }
}
