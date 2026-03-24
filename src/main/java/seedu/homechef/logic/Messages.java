package seedu.homechef.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.homechef.logic.parser.Prefix;
import seedu.homechef.model.order.Order;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ORDER_DISPLAYED_INDEX = "The order index provided is invalid";
    public static final String MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX = "The menu item index provided is invalid";
    public static final String MESSAGE_ORDERS_LISTED_OVERVIEW = "%1$d orders listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_MENU_ITEM_NOT_FOUND =
            "No menu item '%s'. Use 'add-menu' to add it to the menu first.";
    public static final String MESSAGE_MENU_ITEM_UNAVAILABLE =
            "'%s' is currently unavailable. Check the menu panel on the right for available items.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code order} for display to the user.
     */
    public static String format(Order order) {
        final StringBuilder builder = new StringBuilder();
        builder.append(order.getFood())
                .append("; Customer: ")
                .append(order.getCustomer())
                .append("; Phone: ")
                .append(order.getPhone())
                .append("; Email: ")
                .append(order.getEmail())
                .append("; Address: ")
                .append(order.getAddress())
                .append("; Completion Status: ")
                .append(order.getCompletionStatus())
                .append("; Payment Status: ")
                .append(order.getPaymentStatus())
                .append("; Tags: ");
        order.getTags().forEach(builder::append);
        return builder.toString();
    }

}
