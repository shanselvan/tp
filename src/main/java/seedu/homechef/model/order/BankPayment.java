package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a bank transfer payment.
 */
public final class BankPayment implements PaymentInfo {
    public static final String MESSAGE_INVALID_BANK_REFERENCE =
            "Bank payment requires a non-blank payment reference/details value.";

    private final String reference;

    public BankPayment() {
        this.reference = null;
    }

    /**
     * Creates a bank transfer payment with the specified reference.
     *
     * @param reference Bank transfer reference.
     */
    public BankPayment(String reference) {
        requireNonNull(reference);
        if (reference.isBlank()) {
            throw new IllegalArgumentException(MESSAGE_INVALID_BANK_REFERENCE);
        }
        this.reference = reference;
    }

    @Override
    public String getReferenceNumber() {
        return reference;
    }

    @Override
    public String getMethod() {
        return METHOD_BANK;
    }

    @Override
    public String toString() {
        return reference == null ? "BANK" : "BANK (ref: " + reference + ")";
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
        return Objects.equals(reference, otherPayment.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(METHOD_BANK, reference);
    }
}
