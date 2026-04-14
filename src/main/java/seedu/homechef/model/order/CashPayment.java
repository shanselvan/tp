package seedu.homechef.model.order;

/**
 * Represents a cash payment.
 */
public final class CashPayment implements PaymentInfo {

    /**
     * Returns the payment reference identifier for a cash payment.
     */
    @Override
    public String getReference() {
        return "CashPayment";
    }

    /**
     * Returns a human-readable string representation of this payment.
     */
    @Override
    public String toString() {
        return "Cash";
    }

    /**
     * Returns true if the other object is also a {@code CashPayment}.
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof CashPayment;
    }

    /**
     * Returns the hash code for this cash payment.
     */
    @Override
    public int hashCode() {
        return CashPayment.class.hashCode();
    }
}
