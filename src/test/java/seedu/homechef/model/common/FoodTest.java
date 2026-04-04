package seedu.homechef.model.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FoodTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Food(null));
    }

    @Test
    public void constructor_invalidFood_throwsIllegalArgumentException() {
        String invalidFood = "";
        assertThrows(IllegalArgumentException.class, () -> new Food(invalidFood));
    }

    @Test
    public void isValidFood() {
        // EP: null name
        assertThrows(NullPointerException.class, () -> Food.isValidFood(null));

        // EP: invalid name
        assertFalse(Food.isValidFood("")); // empty string
        assertFalse(Food.isValidFood(" ")); // spaces only
        assertFalse(Food.isValidFood("^")); // only non-alphanumeric characters
        assertFalse(Food.isValidFood("cookie*")); // contains non-alphanumeric characters

        // EP: valid name
        assertTrue(Food.isValidFood("cookies")); // alphabets only
        assertTrue(Food.isValidFood("12345")); // numbers only
        assertTrue(Food.isValidFood("5 cookies")); // alphanumeric characters
        assertTrue(Food.isValidFood("Cookies")); // with capital letters
        assertTrue(Food.isValidFood("Cookies from Heaven")); // long names
        assertTrue(Food.isValidFood("Cookies (7pcs) - [Blueberry]")); // (), [] and - are allowed
    }

    @Test
    public void equals() {
        Food food = new Food("Valid Food");

        // EP: same values -> returns true
        assertTrue(food.equals(new Food("Valid Food")));

        // EP: same object -> returns true
        assertTrue(food.equals(food));

        // EP: null -> returns false
        assertFalse(food.equals(null));

        // EP: different types -> returns false
        assertFalse(food.equals(5.0f));

        // EP: different values -> returns false
        assertFalse(food.equals(new Food("Other Valid Food")));
    }

    @Test
    public void hashCode_equalObjectsHaveEqualHashCode() {
        Food food1 = new Food("Valid Food");
        Food food2 = new Food("Valid Food");
        assertEquals(food1.hashCode(), food2.hashCode());
    }
}
