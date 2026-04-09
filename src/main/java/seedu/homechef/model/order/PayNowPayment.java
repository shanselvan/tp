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
     * @param reference
     */
    public PayNowPayment(String reference) {
        requireNonNull(reference);
        if (reference.isBlank()) {
            throw new IllegalArgumentException(MESSAGE_INVALID_PAYNOW_HANDLE);
        }
        this.reference = reference;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public String toString() {
        return "PayNow: " + this.reference;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(PayNowPayment.class, reference);
    }
}
