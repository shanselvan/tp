package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents an Order's payment status in HomeChef.
 */
public enum PaymentStatus {
    PAID("Paid"),
    PARTIAL("Partial"),
    UNPAID("Unpaid");

    public static final String MESSAGE_CONSTRAINTS = "Payment status must be 'Paid', 'Partial', or 'Unpaid'";

    public final String displayValue;

    PaymentStatus(String displayValue) {
        assert displayValue != null && !displayValue.isEmpty();
        this.displayValue = displayValue;
    }

    /**
     * Returns the payment status represented by the specified string.
     *
     * @param status String representation of a payment status.
     * @return Matching payment status.
     * @throws NullPointerException     If {@code status} is null.
     * @throws IllegalArgumentException If {@code status} is not a valid payment status.
     */
    public static PaymentStatus fromString(String status) {
        requireNonNull(status);
        checkArgument(isValidPaymentStatus(status), MESSAGE_CONSTRAINTS);
        return Arrays.stream(PaymentStatus.values())
                .filter(s -> s.displayValue.equalsIgnoreCase(status))
                .findFirst()
                .get();
    }

    /**
     * Returns true if the specified string is a valid payment status.
     *
     * @param test String to be tested.
     * @return True if the specified string is a valid payment status.
     */
    public static boolean isValidPaymentStatus(String test) {
        return Arrays.stream(PaymentStatus.values())
                .anyMatch(status -> status.displayValue.equalsIgnoreCase(test));
    }

    /**
     * Returns true if this payment status represents a paid order.
     *
     * @return True if this payment status represents a paid order.
     */
    public boolean isPaid() {
        return this == PAID;
    }

    @Override
    public String toString() {
        return displayValue;
    }
}
