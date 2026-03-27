package seedu.homechef.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

import seedu.homechef.model.order.Order;

/**
 * Utility methods for generating receipt files for orders.
 */
public class ReceiptUtil {

    private static final String RECEIPTS_DIRECTORY_NAME = "receipts";
    private static final String RECEIPT_FILE_SUFFIX = "_receipt.txt";

    /**
     * Returns the output path for an order receipt, located in a {@code receipts} folder
     * beside the HomeChef data file.
     */
    public static Path buildReceiptPath(Path homeChefFilePath, Order order) {
        requireNonNull(homeChefFilePath);
        requireNonNull(order);

        Path baseDirectory = homeChefFilePath.toAbsolutePath().getParent();
        String fileName = buildReceiptFileName(order);
        return baseDirectory.resolve(RECEIPTS_DIRECTORY_NAME).resolve(fileName);
    }

    /**
     * Writes a receipt for the given order to the given output path.
     */
    public static void writeReceipt(Path outputPath, Order order) throws IOException {
        requireNonNull(outputPath);
        requireNonNull(order);

        FileUtil.createParentDirsOfFile(outputPath);
        FileUtil.writeToFile(outputPath, formatReceipt(order));
    }

    private static String buildReceiptFileName(Order order) {
        String customer = sanitizeFileComponent(order.getCustomer().toString());
        String food = sanitizeFileComponent(order.getFood().toString());
        String date = sanitizeFileComponent(order.getDate().toString());
        return food + "_" + customer + "_" + date + RECEIPT_FILE_SUFFIX;
    }

    private static String sanitizeFileComponent(String raw) {
        String sanitized = raw.trim().replaceAll("[^A-Za-z0-9]+", "_").replaceAll("_+", "_");
        sanitized = sanitized.replaceAll("^_|_$", "");
        return sanitized.isEmpty() ? "order" : sanitized;
    }

    private static String formatReceipt(Order order) {
        String tags = order.getTags().stream()
                .map(Object::toString)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(", "));

        String paymentInfo = order.getPaymentInfo()
                .map(Object::toString)
                .orElse("N/A");

        return String.join(System.lineSeparator(),
                "HomeChef Receipt",
                "================",
                "Food: " + order.getFood(),
                "Customer: " + order.getCustomer(),
                "Phone: " + order.getPhone(),
                "Email: " + order.getEmail(),
                "Address: " + order.getAddress(),
                "Fulfillment Date: " + order.getDate(),
                "Price: $" + order.getPrice(),
                "Completion Status: " + order.getCompletionStatus(),
                "Payment Status: " + order.getPaymentStatus(),
                "Diet Tags: " + (tags.isBlank() ? "None" : tags),
                "Payment Info: " + paymentInfo,
                "");
    }
}
