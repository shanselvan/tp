package seedu.homechef.model.menu;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.homechef.model.menu.exceptions.DuplicateMenuItemException;
import seedu.homechef.model.menu.exceptions.MenuItemNotFoundException;

/**
 * A list of menu items that enforces uniqueness between its elements and does not allow nulls.
 * Uniqueness is determined by {@code MenuItem#isSameMenuItem(MenuItem)} (case-insensitive name match).
 *
 * Supports a minimal set of list operations.
 */
public class UniqueMenuItemList implements Iterable<MenuItem> {

    private final ObservableList<MenuItem> internalList = FXCollections.observableArrayList();
    private final ObservableList<MenuItem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent menu item.
     */
    public boolean contains(MenuItem toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMenuItem);
    }

    /**
     * Adds a menu item to the list. The item must not already exist.
     */
    public void add(MenuItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMenuItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the menu item {@code target} with {@code editedItem}.
     */
    public void setMenuItem(MenuItem target, MenuItem editedItem) {
        requireAllNonNull(target, editedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MenuItemNotFoundException();
        }

        if (!target.isSameMenuItem(editedItem) && contains(editedItem)) {
            throw new DuplicateMenuItemException();
        }

        internalList.set(index, editedItem);
    }

    /**
     * Removes the equivalent menu item from the list.
     * Uses {@code MenuItem#isSameMenuItem(MenuItem)} for lookup so that removal succeeds
     * even if the availability field differs from the stored instance.
     */
    public void remove(MenuItem toRemove) {
        requireNonNull(toRemove);
        int index = -1;
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).isSameMenuItem(toRemove)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new MenuItemNotFoundException();
        }
        internalList.remove(index);
    }

    public void setMenuItems(UniqueMenuItemList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code menuItems}.
     */
    public void setMenuItems(List<MenuItem> menuItems) {
        requireAllNonNull(menuItems);
        if (!menuItemsAreUnique(menuItems)) {
            throw new DuplicateMenuItemException();
        }
        internalList.setAll(menuItems);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<MenuItem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<MenuItem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UniqueMenuItemList)) {
            return false;
        }
        UniqueMenuItemList otherList = (UniqueMenuItemList) other;
        return internalList.equals(otherList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    private boolean menuItemsAreUnique(List<MenuItem> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).isSameMenuItem(items.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
