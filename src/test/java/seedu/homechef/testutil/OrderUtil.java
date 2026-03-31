package seedu.homechef.testutil;

import static seedu.homechef.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.homechef.logic.commands.AddCommand;
import seedu.homechef.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.tag.DietTag;

/**
 * A utility class for Order.
 */
public class OrderUtil {

    /**
     * Returns an add command string for adding the {@code order}.
     */
    public static String getAddCommand(Order order) {
        return AddCommand.COMMAND_WORD + " " + getOrderDetails(order);
    }

    /**
     * Returns the part of command string for the given {@code order}'s details.
     */
    public static String getOrderDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_FOOD + order.getFood().toString() + " ");
        sb.append(PREFIX_CUSTOMER + order.getCustomer().toString() + " ");
        sb.append(PREFIX_PHONE + order.getPhone().toString() + " ");
        sb.append(PREFIX_EMAIL + order.getEmail().toString() + " ");
        sb.append(PREFIX_ADDRESS + order.getAddress().toString() + " ");
        sb.append(PREFIX_DATE + order.getDate().toString() + " ");
        order.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditOrderDescriptor}'s details.
     */
    public static String getEditOrderDescriptorDetails(EditOrderDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getFood().ifPresent(food -> sb.append(PREFIX_FOOD).append(food.toString()).append(" "));
        descriptor.getCustomer().ifPresent(name -> sb.append(PREFIX_CUSTOMER).append(name.toString()).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.toString()).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.toString()).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.toString()).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<DietTag> dietTags = descriptor.getTags().get();
            if (dietTags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                dietTags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
