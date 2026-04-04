package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // EP: null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // EP: invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string, boundary value
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 digits
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // space without country code prefix
        assertFalse(Phone.isValidPhone("+651234")); // country code missing space separator
        assertFalse(Phone.isValidPhone("+65 12")); // local part too short after country code

        // EP: valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 digits
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
        assertTrue(Phone.isValidPhone("+65 91234567")); // Singapore format
        assertTrue(Phone.isValidPhone("+1 2125551234")); // US format
        assertTrue(Phone.isValidPhone("+44 7911123456")); // UK format
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // EP: same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // EP: same object -> returns true
        assertTrue(phone.equals(phone));

        // EP: null -> returns false
        assertFalse(phone.equals(null));

        // EP: different types -> returns false
        assertFalse(phone.equals(5.0f));

        // EP: different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }
}
