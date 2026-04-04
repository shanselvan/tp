package seedu.homechef.model.menu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.homechef.model.common.Food;
import seedu.homechef.model.menu.exceptions.DuplicateMenuItemException;
import seedu.homechef.model.menu.exceptions.MenuItemNotFoundException;

public class UniqueMenuItemListTest {

    private static final MenuItem CHICKEN = new MenuItem(
            new Food("Chicken Rice"), new Price("5.50"), true);
    private static final MenuItem NASI = new MenuItem(
            new Food("Nasi Goreng"), new Price("6.00"), true);

    private final UniqueMenuItemList list = new UniqueMenuItemList();

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
}
