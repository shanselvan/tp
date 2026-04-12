package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.homechef.model.common.Price;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        // EP: empty string
        assertThrows(IllegalArgumentException.class, () -> new Price("")); // boundary value

        // EP: invalid formats
        assertThrows(IllegalArgumentException.class, () -> new Price(" "));
        assertThrows(IllegalArgumentException.class, () -> new Price("abc"));
        assertThrows(IllegalArgumentException.class, () -> new Price("-5"));
        assertThrows(IllegalArgumentException.class, () -> new Price("5.123")); // more than 2 dp
    }

    @Test
    public void isValidPrice() {
        // EP: null price
        assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // EP: invalid prices
        assertFalse(Price.isValidPrice("")); // empty, boundary value
        assertFalse(Price.isValidPrice(" ")); // spaces
        assertFalse(Price.isValidPrice("abc")); // non-numeric
        assertFalse(Price.isValidPrice("-5")); // negative
        assertFalse(Price.isValidPrice("5.123")); // more than 2 decimal places
        assertFalse(Price.isValidPrice("00.50")); // leading zero invalid per regex
        assertTrue(Price.isValidPrice("0"));
        assertTrue(Price.isValidPrice("0.00"));

        // EP: valid prices
        assertTrue(Price.isValidPrice("5"));
        assertTrue(Price.isValidPrice("5.5"));
        assertTrue(Price.isValidPrice("5.50"));
        assertTrue(Price.isValidPrice("10"));
        assertTrue(Price.isValidPrice("0.01"));
        assertTrue(Price.isValidPrice("999.99"));
    }

    @Test
    public void equals() {
        Price price = new Price("5.50");

        // EP: same values -> returns true
        assertTrue(price.equals(new Price("5.50")));

        // EP: normalization check
        assertTrue(price.equals(new Price("5.5")));

        // EP: same object -> returns true
        assertTrue(price.equals(price));

        // EP: null -> returns false
        assertFalse(price.equals(null));

        // EP: different types -> returns false
        assertFalse(price.equals(5.0f));

        // EP: different values -> returns false
        assertFalse(price.equals(new Price("6.00")));
    }

    @Test
    public void constructor_longWholeNumber_preservesExactValue() {
        Price price = new Price("33333333333333333");
        assertEquals("33333333333333333.00", price.toString());
    }
}
