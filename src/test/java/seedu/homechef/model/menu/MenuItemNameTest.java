package seedu.homechef.model.menu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MenuItemNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MenuItemName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MenuItemName("")); // empty string, boundary value
        assertThrows(IllegalArgumentException.class, () -> new MenuItemName(" "));
        assertThrows(IllegalArgumentException.class, () -> new MenuItemName("@invalid"));
    }

    @Test
    public void isValidMenuItemName() {
        // EP: null menu item name
        assertThrows(NullPointerException.class, () -> MenuItemName.isValidMenuItemName(null));

        // EP: invalid menu item name
        assertFalse(MenuItemName.isValidMenuItemName("")); // empty string, boundary value
        assertFalse(MenuItemName.isValidMenuItemName(" "));
        assertFalse(MenuItemName.isValidMenuItemName("^"));
        assertFalse(MenuItemName.isValidMenuItemName("cake*"));

        // EP:  valid menu item name
        assertTrue(MenuItemName.isValidMenuItemName("Chicken Rice"));
        assertTrue(MenuItemName.isValidMenuItemName("Nasi Goreng"));
        assertTrue(MenuItemName.isValidMenuItemName("Cookies (7pcs) - [Blueberry]"));
        assertTrue(MenuItemName.isValidMenuItemName("12345"));
    }

    @Test
    public void equals() {
        MenuItemName name = new MenuItemName("Chicken Rice");

        // EP: same values -> returns true
        assertTrue(name.equals(new MenuItemName("Chicken Rice")));

        // EP: same object -> returns true
        assertTrue(name.equals(name));

        // EP: null -> returns false
        assertFalse(name.equals(null));

        // EP: different values -> returns false
        assertFalse(name.equals(new MenuItemName("Nasi Goreng")));
        // equals() is case-sensitive even though isSameMenuItem() is case-insensitive
        assertFalse(name.equals(new MenuItemName("chicken rice")));
    }
}
