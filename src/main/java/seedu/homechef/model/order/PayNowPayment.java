package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a PayNow payment.
 */
public final class PayNowPayment implements PaymentInfo {
    public static final String MESSAGE_INVALID_REFERENCE =
            "PayNow payment requires a non-blank phone number or handle.";

    private final String reference;

    /**
     * Creates a PayNow payment with the specified handle.
     *
     * @param reference PayNow phone number or handle.
     */
    public PayNowPayment(String reference) {
        requireNonNull(reference);
        if (reference.isBlank()) {
            throw new IllegalArgumentException(MESSAGE_INVALID_REFERENCE);
        }
        this.reference = reference;
    }

    /**
     * Returns the PayNow phone number or handle.
     */
    @Override
    public String getReference() {
        return reference;
    }

    /**
     * Returns a human-readable string representation of this payment.
     */
    @Override
    public String toString() {
        return "PayNow: " + this.reference;
    }

    /**
     * Returns true if the other object is a {@code PayNowPayment} with the same reference.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PayNowPayment)) {
            return false;
        }
        PayNowPayment otherPayment = (PayNowPayment) other;
        return reference.equals(otherPayment.reference);
    }

    /**
     * Returns the hash code for this PayNow payment.
     */
    @Override
    public int hashCode() {
        return Objects.hash(PayNowPayment.class, reference);
    }
}
