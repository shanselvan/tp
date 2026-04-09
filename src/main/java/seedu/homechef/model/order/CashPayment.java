package seedu.homechef.model.order;

/**
 * Represents a cash payment.
 */
public final class CashPayment implements PaymentInfo {

    @Override
    public String getReference() {
        return "CashPayment";
    }

    @Override
    public String toString() {
        return "Cash";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CashPayment;
    }

    @Override
    public int hashCode() {
        return CashPayment.class.hashCode();
    }
}
