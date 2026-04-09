package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a bank transfer payment.
 */
public final class BankPayment implements PaymentInfo {
    public static final String MESSAGE_INVALID_REFERENCE =
            "Bank payment requires a non-blank payment reference/details value.";

    private final String reference;

    /**
     * Creates a bank transfer payment with the specified reference.
     *
     * @param reference Bank transfer reference.
     */
    public BankPayment(String reference) {
        requireNonNull(reference);
        if (reference.isBlank()) {
            throw new IllegalArgumentException(MESSAGE_INVALID_REFERENCE);
        }
        this.reference = reference;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public String toString() {
        return "Bank: " + reference;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof BankPayment)) {
            return false;
        }
        BankPayment otherPayment = (BankPayment) other;
        return reference.equals(otherPayment.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(BankPayment.class, reference);
    }
}
