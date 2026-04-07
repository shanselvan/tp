package seedu.homechef.model.order;

/**
 * Represents payment information for an order.
 */
public interface PaymentInfo {
    String METHOD_CASH = "CASH";
    String METHOD_PAYNOW = "PAYNOW";
    String METHOD_BANK = "BANK";

    String MESSAGE_INVALID_BANK_REFERENCE =
            "Bank payment requires a non-blank payment reference/details value.";
    String MESSAGE_INVALID_PAYNOW_HANDLE =
            "PayNow payment requires a non-blank phone number or handle.";
    String MESSAGE_INVALID_PAYMENT_METHOD =
            "Unsupported payment method. Expected one of: CASH, PAYNOW, BANK.";

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

    static PaymentInfo fromStorageFields(String method, String details) {
        if (method == null || method.isBlank()) {
            throw new IllegalArgumentException(MESSAGE_INVALID_PAYMENT_METHOD);
        }

        switch (normalizeMethod(method)) {
        case METHOD_CASH:
            return cash();
        case METHOD_PAYNOW:
            if (details == null || details.isBlank()) {
                throw new IllegalArgumentException(MESSAGE_INVALID_PAYNOW_HANDLE);
            }
            return payNow(details.trim());
        case METHOD_BANK:
            return (details == null || details.isBlank()) ? bank() : bank(details.trim());
        default:
            throw new IllegalArgumentException(MESSAGE_INVALID_PAYMENT_METHOD);
        }
    }

    static PaymentInfo cash() {
        return new CashPayment();
    }

    static PaymentInfo payNow(String handle) {
        return new PayNowPayment(handle);
    }

    static PaymentInfo bank() {
        return new BankPayment();
    }

    static PaymentInfo bank(String reference) {
        return new BankPayment(reference);
    }

    private static String normalizeMethod(String method) {
        return method.trim().toUpperCase();
    }
}
