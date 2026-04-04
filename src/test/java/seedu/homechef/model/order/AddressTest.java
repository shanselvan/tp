package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // EP: null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // EP: invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string, boundary value
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // EP: valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address");

        // EP: same values -> returns true
        assertTrue(address.equals(new Address("Valid Address")));

        // EP: same object -> returns true
        assertTrue(address.equals(address));

        // EP: null -> returns false
        assertFalse(address.equals(null)); // boundary value

        // EP: different types -> returns false
        assertFalse(address.equals(5.0f));

        // EP: different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address")));
        // No need to check for empty string as that is invalid.
    }
}
