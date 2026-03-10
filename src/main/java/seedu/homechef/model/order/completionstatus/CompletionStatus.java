package seedu.homechef.model.order.completionstatus;

import seedu.homechef.model.order.Address;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

/**
 * Represents a Order's completion status in the HomeChef.
 */
public class CompletionStatus {
    private static final int MIN_STATUS = 0;
    private static final int MAX_STATUS = 1;

    public static final String MESSAGE_CONSTRAINTS = String.format(
            "Completion status must be represented as an integer between %d and %d",
            MIN_STATUS,
            MAX_STATUS);

    public final int value;

    /**
     * Constructs a {@code CompletionStatus}.
     *
     * @param value A valid integer to represent status.
     */
    public CompletionStatus(int value) {
        checkArgument(isValidCompletionStatus(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid completion status.
     */
    public static boolean isValidCompletionStatus(int test) {
        return (test >= MIN_STATUS && test <= MAX_STATUS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompletionStatus)) {
            return false;
        }

        CompletionStatus otherCompletionStatus = (CompletionStatus) other;
        return this.value == otherCompletionStatus.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }

}
