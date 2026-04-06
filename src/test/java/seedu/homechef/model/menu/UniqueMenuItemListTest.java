package seedu.homechef.model.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.exceptions.DuplicateMenuItemException;
import seedu.homechef.model.menu.exceptions.MenuItemNotFoundException;

public class UniqueMenuItemListTest {

    private static final MenuItem CHICKEN = new MenuItem(
            new Food("Chicken Rice"), new Price("5.50"), true);
    private static final MenuItem NASI = new MenuItem(
            new Food("Nasi Goreng"), new Price("6.00"), true);

    private final UniqueMenuItemList list = new UniqueMenuItemList();

    @Test
    public void contains_nullItem_throwsNullPointerException() {
        // EP: null menu item lookup
        assertThrows(NullPointerException.class, () -> list.contains(null));
    }

    @Test
    public void add_uniqueItem_success() {
        list.add(CHICKEN);
        assertTrue(list.contains(CHICKEN));
    }

    @Test
    public void add_duplicateName_throwsDuplicateMenuItemException() {
        list.add(CHICKEN);
        MenuItem duplicate = new MenuItem(new Food("Chicken Rice"), new Price("9.00"), false);
        assertThrows(DuplicateMenuItemException.class, () -> list.add(duplicate));
    }

    @Test
    public void add_duplicateNameCaseInsensitive_throwsDuplicateMenuItemException() {
        list.add(CHICKEN);
        MenuItem duplicate = new MenuItem(new Food("chicken rice"), new Price("5.50"), true);
        assertThrows(DuplicateMenuItemException.class, () -> list.add(duplicate));
    }

    @Test
    public void remove_existingItem_success() {
        list.add(CHICKEN);
        list.remove(CHICKEN);
        assertFalse(list.contains(CHICKEN));
    }

    @Test
    public void remove_sameIdentityDifferentFields_success() {
        list.add(CHICKEN);
        MenuItem sameIdentity = new MenuItem(new Food("Chicken Rice"), new Price("9.00"), false);

        list.remove(sameIdentity);

        // EP: removal by same identity but different non-identity fields
        assertFalse(list.contains(CHICKEN));
    }

    @Test
    public void remove_absentItem_throwsMenuItemNotFoundException() {
        assertThrows(MenuItemNotFoundException.class, () -> list.remove(CHICKEN));
    }

    @Test
    public void setMenuItem_validEdit_success() {
        list.add(CHICKEN);
        MenuItem edited = new MenuItem(new Food("Chicken Rice"), new Price("7.00"), true);
        list.setMenuItem(CHICKEN, edited);
        assertTrue(list.contains(edited));
    }

    @Test
    public void setMenuItem_targetNotFound_throwsMenuItemNotFoundException() {
        assertThrows(MenuItemNotFoundException.class, () -> list.setMenuItem(CHICKEN,
                new MenuItem(new Food("Chicken Rice"), new Price("7.00"), true)));
    }

    @Test
    public void setMenuItem_editedNameConflicts_throwsDuplicateMenuItemException() {
        list.add(CHICKEN);
        list.add(NASI);
        MenuItem conflict = new MenuItem(new Food("Nasi Goreng"), new Price("5.50"), true);
        assertThrows(DuplicateMenuItemException.class, () -> list.setMenuItem(CHICKEN, conflict));
    }

    @Test
    public void setMenuItems_uniqueMenuItemList_replacesContents() {
        list.add(CHICKEN);
        UniqueMenuItemList replacement = new UniqueMenuItemList();
        replacement.add(NASI);

        list.setMenuItems(replacement);

        // EP: replacement from another unique menu item list
        assertFalse(list.contains(CHICKEN));
        assertTrue(list.contains(NASI));
    }

    @Test
    public void setMenuItems_listWithUniqueItems_replacesContents() {
        list.add(CHICKEN);

        list.setMenuItems(Arrays.asList(NASI));

        // EP: replacement from plain list with unique items
        assertFalse(list.contains(CHICKEN));
        assertTrue(list.contains(NASI));
    }

    @Test
    public void setMenuItems_listWithDuplicates_throwsDuplicateMenuItemException() {
        MenuItem duplicate = new MenuItem(new Food("chicken rice"), new Price("9.00"), false);

        // EP: replacement list contains duplicate identities
        assertThrows(DuplicateMenuItemException.class, () -> list.setMenuItems(Arrays.asList(CHICKEN, duplicate)));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        list.add(CHICKEN);

        // EP: unmodifiable observable list view
        assertThrows(UnsupportedOperationException.class, () -> list.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        UniqueMenuItemList sameList = new UniqueMenuItemList();
        sameList.add(CHICKEN);
        UniqueMenuItemList differentList = new UniqueMenuItemList();
        differentList.add(NASI);

        list.add(CHICKEN);

        // EP: same object -> returns true
        assertTrue(list.equals(list));
        // EP: same values -> returns true
        assertTrue(list.equals(sameList));
        // EP: different values -> returns false
        assertFalse(list.equals(differentList));
        // EP: null -> returns false
        assertFalse(list.equals(null));
        // EP: different types -> returns false
        assertFalse(list.equals(1));
    }

    @Test
    public void toStringMethod() {
        list.add(CHICKEN);

        // EP: string representation delegates to backing list
        assertEquals("[" + CHICKEN + "]", list.toString());
    }
}
