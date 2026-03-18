package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CompletionStatusTest {
    @Test
    public void isValidCompletionStatus() {
        // valid completion status
        assertTrue(CompletionStatus.isValidCompletionStatus(CompletionStatusEnum.IN_PROGRESS));
        assertTrue(CompletionStatus.isValidCompletionStatus(CompletionStatusEnum.COMPLETED));
    }

    @Test
    public void equals() {
        CompletionStatus completionStatus = new CompletionStatus(CompletionStatusEnum.COMPLETED);

        // same values -> returns true
        assertTrue(completionStatus.equals(new CompletionStatus(CompletionStatusEnum.COMPLETED)));

        // same object -> returns true
        assertTrue(completionStatus.equals(completionStatus));

        // null -> returns false
        assertFalse(completionStatus.equals(null));

        // different types -> returns false
        assertFalse(completionStatus.equals(5.0f));

        // different values -> returns false
        assertFalse(completionStatus.equals(new CompletionStatus(CompletionStatusEnum.IN_PROGRESS)));
    }

    @Test
    public void hashCodeTest() {
        CompletionStatus completionStatus1 = new CompletionStatus(CompletionStatusEnum.IN_PROGRESS);
        CompletionStatus completionStatus2 = new CompletionStatus(CompletionStatusEnum.COMPLETED);

        assertEquals(completionStatus1.hashCode(), CompletionStatusEnum.IN_PROGRESS.hashCode());
        assertNotEquals(completionStatus1.hashCode(), CompletionStatusEnum.COMPLETED.hashCode());
        assertNotEquals(completionStatus1.hashCode(), completionStatus2.hashCode());
    }

    @Test
    public void toStringTest() {
        CompletionStatus completionStatus1 = new CompletionStatus(CompletionStatusEnum.IN_PROGRESS);
        CompletionStatus completionStatus2 = new CompletionStatus(CompletionStatusEnum.COMPLETED);

        assertEquals(completionStatus1.toString(), "In progress");
        assertEquals(completionStatus2.toString(), "Completed");
    }
}
