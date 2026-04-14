package seedu.homechef.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
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
        Path receiptsDirectory = baseDirectory.resolve(RECEIPTS_DIRECTORY_NAME);
        String fileName = buildReceiptFileName(order);
        return buildUniqueReceiptPath(receiptsDirectory, fileName);
    }

    private static Path buildUniqueReceiptPath(Path receiptsDirectory, String fileName) {
        Path candidate = receiptsDirectory.resolve(fileName);
        if (!Files.exists(candidate)) {
            return candidate;
        }

        String baseName = fileName.substring(0, fileName.length() - ".txt".length());
        int suffix = 1;
        while (true) {
            Path suffixedCandidate = receiptsDirectory.resolve(baseName + "_" + suffix + ".txt");
            if (!Files.exists(suffixedCandidate)) {
                return suffixedCandidate;
            }
            suffix++;
        }
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
        String sanitized = raw.trim().replaceAll("[\\\\/:*?\"<>|]", "_");
        sanitized = sanitized.replaceAll("_+", "_").replaceAll("^_|_$", "");
        return sanitized.isEmpty() ? "order" : sanitized;
    }

    /**
     * Returns the formatted receipt content for the given order.
     */
    public static String formatReceipt(Order order) {
        String tags = order.getTags().stream()
                .map(Object::toString)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(", "));

        String paymentInfo = order.getPaymentInfo()
                .map(Object::toString)
                .orElse("N/A");

        String nl = System.lineSeparator();

        return String.join(nl,
                "==================================================",
                "                     HOMECHEF",
                "                  ORDER RECEIPT",
                "==================================================",
                "",
                "Order Details",
                "--------------------------------------------------",
                String.format("%-20s : %s", "Food Item", order.getFood()),
                String.format("%-20s : $%s", "Price", order.getPrice()),
                String.format("%-20s : %s", "Dietary Tags", tags.isBlank() ? "None" : tags),
                "",
                "Customer Details",
                "--------------------------------------------------",
                String.format("%-20s : %s", "Name", order.getCustomer()),
                String.format("%-20s : %s", "Phone", order.getPhone()),
                String.format("%-20s : %s", "Email", order.getEmail()),
                String.format("%-20s : %s", "Address", order.getAddress()),
                "",
                "Order Information",
                "--------------------------------------------------",
                String.format("%-20s : %s", "Fulfillment Date", order.getDate()),
                String.format("%-20s : %s", "Order Status", order.getCompletionStatus()),
                String.format("%-20s : %s", "Payment Status", order.getPaymentStatus()),
                "",
                "Payment",
                "--------------------------------------------------",
                String.format("%-20s : $%s", "Amount Due", order.getPrice()),
                String.format("%-20s : %s", "Payment Info", paymentInfo),
                "",
                "==================================================",
                "        Thank you for ordering with HomeChef!",
                "==================================================",
                ""
        );
    }
}
