package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompletionStatusTest {
    @Test
    public void isValidCompletionStatus() {
        // EP: null completion status
        assertFalse(CompletionStatus.isValidCompletionStatus(null));

        // EP: invalid completion status
        assertFalse(CompletionStatus.isValidCompletionStatus("")); // boundary value
        assertFalse(CompletionStatus.isValidCompletionStatus("Invalid status"));

        // EP: valid completion status
        assertTrue(CompletionStatus.isValidCompletionStatus("Pending"));
        assertTrue(CompletionStatus.isValidCompletionStatus("In progress"));
        assertTrue(CompletionStatus.isValidCompletionStatus("Completed"));
    }

    @Test
    public void equals() {
        CompletionStatus completionStatus = CompletionStatus.COMPLETED;

        // EP: same values -> returns true
        assertTrue(completionStatus.equals(CompletionStatus.COMPLETED));

        // EP: same object -> returns true
        assertEquals(completionStatus, completionStatus);

        // EP: null -> returns false
        assertNotEquals(null, completionStatus);

        // EP: different types -> returns false
        assertNotEquals(5.0f, completionStatus);

        // EP: different values -> returns false
        assertFalse(completionStatus.equals(CompletionStatus.IN_PROGRESS));
    }

    @Test
    public void hashCodeTest() {
        CompletionStatus completionStatus1 = CompletionStatus.IN_PROGRESS;
        CompletionStatus completionStatus2 = CompletionStatus.COMPLETED;

        // EP: same status
        assertEquals(completionStatus1.hashCode(), CompletionStatus.IN_PROGRESS.hashCode());

        // EP: different statuses
        assertNotEquals(completionStatus1.hashCode(), CompletionStatus.COMPLETED.hashCode());
        assertNotEquals(completionStatus1.hashCode(), completionStatus2.hashCode());
    }

    @Test
    public void toStringTest() {
        CompletionStatus completionStatus1 = CompletionStatus.IN_PROGRESS;
        CompletionStatus completionStatus2 = CompletionStatus.COMPLETED;
        CompletionStatus completionStatus3 = CompletionStatus.PENDING;
        String testString1 = "";
        String testString2 = "Invalid status";

        // EP: does not match null
        assertNotEquals(null, completionStatus1.toString());
        assertNotEquals(null, completionStatus2.toString());
        assertNotEquals(null, completionStatus3.toString());

        // EP: does not match any other string representation
        assertNotEquals(testString1, completionStatus1.toString()); // boundary value
        assertNotEquals(testString1, completionStatus2.toString()); // boundary value
        assertNotEquals(testString1, completionStatus3.toString()); // boundary value
        assertNotEquals(testString2, completionStatus1.toString());
        assertNotEquals(testString2, completionStatus2.toString());
        assertNotEquals(testString2, completionStatus3.toString());

        // EP: matches correct string representation
        assertEquals("In Progress", completionStatus1.toString());
        assertEquals("Completed", completionStatus2.toString());
        assertEquals("Pending", completionStatus3.toString());
    }

    @Test
    public void fromString_validString() {
        // EP: valid string to convert from
        CompletionStatus completionStatus1 = CompletionStatus.IN_PROGRESS;
        CompletionStatus completionStatus2 = CompletionStatus.COMPLETED;
        CompletionStatus completionStatus3 = CompletionStatus.PENDING;

        assertEquals(completionStatus1, CompletionStatus.fromString("In progress"));
        assertEquals(completionStatus2, CompletionStatus.fromString("Completed"));
        assertEquals(completionStatus3, CompletionStatus.fromString("Pending"));
    }

    @Test
    public void fromString_invalidString_throwsIllegalArgumentException() {
        // EP: invalid string to convert from
        String invalidString1 = "";
        String invalidString2 = " ";
        String invalidString3 = "Invalid string";

        assertThrows(IllegalArgumentException.class, () -> CompletionStatus.fromString(invalidString1));
        // boundary value
        assertThrows(IllegalArgumentException.class, () -> CompletionStatus.fromString(invalidString2));
        assertThrows(IllegalArgumentException.class, () -> CompletionStatus.fromString(invalidString3));
    }

    @Test
    public void fromString_nullString_throwsNullPointerException() {
        // EP: null string to convert from
        String nullString = null;

        assertThrows(NullPointerException.class, () -> CompletionStatus.fromString(nullString));
    }
}
