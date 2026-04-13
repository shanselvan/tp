package seedu.homechef.model.menu;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.model.menu.exceptions.AmbiguousMenuItemException;
import seedu.homechef.model.menu.exceptions.MenuItemNotFoundException;

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

    /** {@inheritDoc} */
    @Override
    public MenuItem resolveMenuItem(String foodName) {
        List<MenuItem> items = menuItems.asUnmodifiableObservableList();
        try {
            int oneBasedIndex = Integer.parseInt(foodName);
            if (oneBasedIndex >= 1 && oneBasedIndex <= items.size()) {
                return items.get(oneBasedIndex - 1);
            }
        } catch (NumberFormatException e) {
            // not an integer; fall through to name matching
        }
        Optional<MenuItem> exactMatch = items.stream()
                .filter(item -> item.getFood().toString().equalsIgnoreCase(foodName))
                .findFirst();
        if (exactMatch.isPresent()) {
            return exactMatch.get();
        }

        List<MenuItem> substringMatches = items.stream()
                .filter(item -> item.getFood().nameContains(foodName))
                .collect(Collectors.toList());
        if (substringMatches.isEmpty()) {
            throw new MenuItemNotFoundException();
        }
        if (substringMatches.size() > 1) {
            String matchingNames = substringMatches.stream()
                    .map(item -> item.getFood().toString())
                    .collect(Collectors.joining(", "));
            throw new AmbiguousMenuItemException(matchingNames);
        }
        return substringMatches.get(0);
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
