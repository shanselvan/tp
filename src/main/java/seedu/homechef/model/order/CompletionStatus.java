package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents an Order's completion status in HomeChef.
 */
public enum CompletionStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    public static final String MESSAGE_CONSTRAINTS = "Completion status must be 'Pending', 'In progress', 'Completed'";

    private final String displayValue;

    CompletionStatus(String displayValue) {
        assert displayValue != null && !displayValue.isEmpty();
        this.displayValue = displayValue;
    }

    /**
     * Returns the completion status represented by the specified string.
     *
     * @param status String representation of a completion status.
     * @return Matching completion status.
     * @throws NullPointerException     If {@code status} is null.
     * @throws IllegalArgumentException If {@code status} is not a valid completion status.
     */
    public static CompletionStatus fromString(String status) {
        requireNonNull(status);
        checkArgument(isValidCompletionStatus(status), MESSAGE_CONSTRAINTS);
        return Arrays.stream(CompletionStatus.values())
                .filter(cs -> cs.displayValue.equalsIgnoreCase(status))
                .findFirst()
                .get();
    }

    /**
     * Returns true if the specified string is a valid completion status.
     *
     * @param test String to be tested.
     * @return True if the specified string is a valid completion status.
     */
    public static boolean isValidCompletionStatus(String test) {
        return Arrays.stream(CompletionStatus.values())
                .anyMatch(status -> status.displayValue.equalsIgnoreCase(test));
    }

    @Override
    public String toString() {
        return displayValue;
    }
}
