package seedu.homechef.model.order;

/**
 * Represents payment information for an order.
 */
public interface PaymentInfo {
    String METHOD_CASH = "CASH";
    String METHOD_PAYNOW = "PAYNOW";
    String METHOD_BANK = "BANK";

    /**
     * Returns the payment method.
     */
    String getMethod();

    /**
     * Returns the PayNow handle for PayNow payments, or null otherwise.
     */
    default String getHandle() {
        return null;
    }

    /**
     * Returns the bank reference for bank payments, or null otherwise.
     */
    default String getReferenceNumber() {
        return null;
    }
}
