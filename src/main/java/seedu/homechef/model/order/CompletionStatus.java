package seedu.homechef.model.order;

import static seedu.homechef.commons.util.AppUtil.checkArgument;

/**
 * Represents an Order's completion status in the HomeChef.
 */
public class CompletionStatus {
    public static final String MESSAGE_CONSTRAINTS = String.format(
            "Completion status must be represented as a valid enum");

    public final CompletionStatusEnum value;

    /**
     * Constructs a {@code CompletionStatus}.
     *
     * @param value A valid enum to represent status.
     */
    public CompletionStatus(CompletionStatusEnum value) {
        checkArgument(isValidCompletionStatus(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid completion status.
     */
    public static boolean isValidCompletionStatus(CompletionStatusEnum test) {
        switch (test) {
        case IN_PROGRESS, COMPLETED:
            return true;
        default:
            return false;
        }
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
    public String toString() {
        switch (value) {
        case IN_PROGRESS:
            return "In progress";
        case COMPLETED:
            return "Completed";
        default:
            return "Invalid completion status";
        }
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

}
